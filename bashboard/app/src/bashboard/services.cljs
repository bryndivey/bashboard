(ns bashboard.services
  (:require [io.pedestal.app.protocols :as p]
            [cljs.reader :as reader]))


(defn receive-ss-event [app e]
  (let [message (reader/read-string (.-data e))]
    (p/put-message (:input app) message)))

(defrecord Services [app]
  p/Activity
  (start [this]
    (let [source (js/EventSource. "/msgs")]
      (.addEventListener source
                         "msg"
                         (fn [e] (receive-ss-event app e))
                         false)))
  (stop [this]))

(defn services-fn [message input-queue]
  (let [body (pr-str message)
        http (js/XMLHttpRequest.)]
    (.open http "POST" "/msgs" true)
    (.setRequestHeader http "Content-Type" "application/edn")
    (.send http body)))



(comment

  ;; The services implementation will need some way to send messages
  ;; back to the application. The queue passed to the services function
  ;; will convey messages to the application.
  (defn echo-services-fn [message queue]
    (put-message queue message))

  )

;; During development, it is helpful to implement services which
;; simulate communication with the real services. This implementation
;; can be placed in the file:
;;
;; app/src/bashboard/simulated/services.cljs
;;
