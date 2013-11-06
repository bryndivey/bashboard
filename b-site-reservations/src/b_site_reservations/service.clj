(ns b-site-reservations.service
    (:require [io.pedestal.service.interceptor :refer [definterceptor]]
              [io.pedestal.service.log :as log]
              [io.pedestal.service.http :as bootstrap]
              [io.pedestal.service.http.sse :as sse]
              [io.pedestal.service.http.ring-middlewares :as middlewares]
              [io.pedestal.service.http.route :as route]
              [io.pedestal.service.http.body-params :as body-params]
              [io.pedestal.service.http.route.definition :refer [defroutes]]
              [ring.middleware.session.cookie :as cookie]
              [ring.util.response :as ring-resp]))

(def ^:private streaming-contexts (atom {}))

(def ^:private sites (atom {"zadev1" {:bookings {192 {:id 192
                                                      :owner "bryn"
                                                      :start #inst "2009-12-02"
                                                      :end #inst "2009-12-05"}}}}))

(defn- session-from-context [streaming-context]
  (get-in streaming-context [:request :cookies "client-id" :value]))

(defn- session-from-request [request]
  (get-in request [:cookies "client-id" :value]))

(defn- clean-up
  [streaming-context]
  (swap! streaming-contexts dissoc (session-from-context streaming-context))
  (sse/end-event-stream streaming-context))

(defn- send-message [streaming-context event-name event-data]
  (try
    (sse/send-event streaming-context event-name (pr-str event-data))))

(defn- notify
  [session-id event-name event-data]
  (when-let [streaming-context (get @streaming-contexts session-id)]
    (send-message streaming-context event-name event-data)))

(defn- notify-all
  [event-name event-data]
  (doseq [session-id (keys @streaming-contexts)]
    (notify session-id event-name event-data)))

(defn- notify-all-others
  [sending-session-id event-name event-data]
  (doseq [session-id (keys @streaming-contexts)]
    (when (not= session-id sending-session-id)
      (notify session-id event-name event-data))))

(defn- store-streaming-context [streaming-context]
  (let [session-id (session-from-context streaming-context)]
    (swap! streaming-contexts assoc session-id streaming-context)))

(defn- session-id [] (.toString (java.util.UUID/randomUUID)))




;; application logic

(defn- mk-update-site-msg [site-name site-data]
  {:io.pedestal.app.messages/type :update-site
   :io.pedestal.app.messages/topic [:sites site-name]
   :value site-data})

(defn- publish-site [site]
  (notify-all "msg" (mk-update-site-msg site (get @sites site))))

(defn- update-sites [sc]
  (doseq [[name data] @sites]
    (send-message sc "msg" (mk-update-site-msg name data))))

(defn- initialize-connection [streaming-context]
  (store-streaming-context streaming-context)
  (update-sites streaming-context))

(defn add-booking [{msg-data :edn-params :as request}]
  (log/info :message "received booking"
            :msg-data msg-data)
  (swap! sites update-in [(:site msg-data) :bookings] assoc (:id msg-data) (dissoc msg-data :site))
  (log/info :message "new sites"
            :sites @sites)
  (publish-site (:site msg-data))
  (ring-resp/response ""))



(declare url-for)

(defn subscribe
  [request]
  (let [session-id (or (session-from-request request)
                       (session-id))
        cookie {:client-id {:value session-id :path "/"}}]
    (-> (ring-resp/redirect (url-for ::events))
        (update-in [:cookies] merge cookie))))

(definterceptor session-interceptor
  (middlewares/session {:store (cookie/cookie-store)}))


(defn publish
  "Publish a message to all other connected clients."
  [{msg-data :edn-params :as request}]
  (log/info :message "received message"
            :request request
            :msg-data msg-data)
  (let [session-id (or (session-from-request request)
                       (session-id))]
    (notify-all-others session-id
                       "msg"
                       (pr-str msg-data)))
  (ring-resp/response ""))

(defn validate-name [name]
  (bootstrap/json-response
   {:result (boolean (some #{name} '("bryn" "lloyd")))}))

(defn process
  "Route a message based on type"
  [{msg-data :edn-params :as request}]
  (log/info :message "received message"
            :request request
            :msg-data msg-data)
  
  (let [session-id (or (session-from-request request)
                       (session-id))
        msg-type (:io.pedestal.app.messages/type msg-data)]
    (log/info msg-data)
    (log/info :message-type msg-type)
    (log/info :message-type (= msg-type :validate-name))
    (cond
     (= msg-type :validate-name) (validate-name (:name msg-data))
     :else (bootstrap/json-response {:result "invalid msg"}))))


(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s" (clojure-version))))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(defroutes routes
  [[["/" {:get home-page}
     ;; Set default interceptors for /about and any other paths under /
     ^:interceptors [(body-params/body-params) bootstrap/html-body session-interceptor]
     ["/about" {:get about-page}]
     ["/bookings" {:post add-booking}]
     
     ["/msgs" {:get subscribe :post process}
      ["/events" {:get [::events (sse/start-event-stream initialize-connection)]}]]]]])

;; You can use this fn or a per-request fn via io.pedestal.service.http.route/url-for
(def url-for (route/url-for-routes routes))

;; Consumed by b-site-reservations.server/create-server
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; :bootstrap/interceptors []
              ::bootstrap/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::bootstrap/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty or :tomcat (see comments in project.clj
              ;; to enable Tomcat)
              ;;::bootstrap/host "localhost"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
