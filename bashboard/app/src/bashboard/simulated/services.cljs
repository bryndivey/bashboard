(ns bashboard.simulated.services
  (:require [io.pedestal.app.protocols :as p]
            [io.pedestal.app.messages :as msg]
            [io.pedestal.app.util.platform :as platform]
            [io.pedestal.app.util.log :as log]))


(def -random-site-num (atom 0))
(defn new-site-name []
  (swap! -random-site-num inc)
  (str "random-site-" @-random-site-num))

(def sample-sites ["zadev1" "zadev2" "zadev3" "zadev4"])

(defn do-connect [input-queue]
  "Publish the initial set of sites, as would come from the service"
  (doseq [site sample-sites]
    (p/put-message input-queue {msg/type :update-site msg/topic [:sites site]})))

(defn add-random-booking [input-queue]
  (let [site (nth sample-sites (rand-int (count sample-sites)))
        id (rand-int 10000000000)]
    (p/put-message input-queue {msg/type :add-booking
                                msg/topic [:sites site :bookings id]
                                :value {:id id
                                        :owner "bryn"
                                        :purpose "Testing shit"
                                        :start #inst "2013-01-05"
                                        :end #inst "2013-01-06"}}))
  (platform/create-timeout (+ 20000 (rand-int 5000)) #(add-random-booking input-queue)))

(defn receive-messages [input-queue]
  (do-connect input-queue)
  (add-random-booking input-queue)
  )

(defrecord MockServices [app]
  p/Activity
  (start [this]
    (receive-messages (:input app)))
  (stop [this]))








(defn validate-name [name]
  {msg/type :name-validation-result
   msg/topic [:login :valid]
   :value (boolean (some #{name} '("bryn" "lloyd")))})


(defn services-fn [msg-data input-queue]
  (log/info (str "Sending msg to server: " msg-data))
    
  (log/info msg-data)
  (let [msg-type (msg/type msg-data)
        response (cond
                  (= msg-type :validate-name) (validate-name (:name msg-data))
                  :else {msg/type :error msg/topic [:main :error] :value "invalid msg"})]
    (log/info "Response: " response)
    (p/put-message input-queue response)
    ))

