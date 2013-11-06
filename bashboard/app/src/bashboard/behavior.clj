(ns ^:shared bashboard.behavior
    (:require [clojure.string :as string]
              [io.pedestal.app :as app]
              [io.pedestal.app.messages :as msg]
              [io.pedestal.app.util.log :as log]))

;; transforms

(defn inc-transform [old-value _]
  ((fnil inc 0) old-value))

(defn swap-transform [_ message]
  (:value message))

(defn append-transform [old-value message]
  ((fnil conj []) old-value (:value message)))

(defn empty-transform [_ _]
  [])

(defn add-booking-transform [old-value message]
  (update-in old-value [:bookings] conj (:value message)))

;; emitters

(defn init-login-1 [_]
  [[:node-create [:login] :map]
   [:node-create [:login :name] :map]
   [:transform-enable [:login :name]
    :login [{msg/type :swap msg/topic [:login :name] (msg/param :value) {}}
            {msg/type :set-focus msg/topic msg/app-model :name :main}]]])

(defn init-login [_]
  [[:node-create [:login] :map]
   [:node-create [:login :name] :map]
   [:transform-enable [:login :name]
    :login [{msg/type :swap msg/topic [:login :name] (msg/param :value) {}}]]])

(defn init-main [_]
  [[:transform-enable [:main :sites] :empty [{msg/topic [:sites "zadev1" :bookings]}]]])

;; derivations

(defn derive-count [_ sites]
  (count sites))

(defn derive-username [_ user]
  (:username user))

(defn derive-free-site [_ sites]
  (let [free (for [[name data] sites
                   :when (= 0 (count (:bookings data)))]
               name)]
    (clojure.string/join ", " free)))

;; effects

(defn login-user [{:keys [name] :as data}]
  (log/debug "LOGIN USER " data)
  [{msg/type :login-user :name name}])


;; app


(def example-app
  {:version 2
;;   :debug true

   :focus {:login [[:login]]
           :main [[:main] [:pedestal]]
           :default :login}
   
   :transform [[:append [:sites :* :bookings] append-transform]
               [:empty [:sites :* :bookings] empty-transform]
               [:swap [:**] swap-transform]
               [:update-site [:sites :*] swap-transform]
               [:add-booking [:sites :* :bookings :*] swap-transform]
               [:debug [:pedestal :**] swap-transform]]

   :effect #{[{[:login :name] :name} login-user :map]}

   :derive #{[#{[:sites :*]} [:sites-count] derive-count :vals]
             [#{[:sites]} [:free-sites] derive-free-site :single-val]
             [#{[:validated-user]} [:username] derive-username :single-val]}
   
   :emit [{:init init-login}
          [#{[:login :*]} (app/default-emitter [])]
          
          {:init init-main}
          [#{[:validated-user]
             [:username]
             [:sites :*]
             [:sites-count]
             [:free-sites]} (app/default-emitter [:main])]
          [#{[:pedestal :debug :*]} (app/default-emitter [])]
 ]})

