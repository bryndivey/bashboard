(ns ^:shared bashboard.behavior
    (:require [clojure.string :as string]
              [io.pedestal.app :as app]
              [io.pedestal.app.messages :as msg]))

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

(defn init-main [_]
  [[:transform-enable [:main :counter] :inc [{msg/topic [:counter]}]]
   [:transform-enable [:main :sites] :empty [{msg/topic [:sites "zadev1" :bookings]}]]])

;; effects

(defn publish-sites [sites]
  [{msg/type :swap msg/topic [:other-sites] :value sites}])

;; derivations

(defn derive-count [_ sites]
  (count sites))

(defn derive-free-nodes [_ sites]
  (for [[name data] sites
        :when (= 0 (count (:bookings data)))]
    name))

;; app

(def example-app
  {:version 2
;;   :debug true
   :transform [[:inc [:counter] inc-transform]
               [:append [:sites :* :bookings] append-transform]
               [:empty [:sites :* :bookings] empty-transform]
               [:swap [:**] swap-transform]
               [:update-site [:sites :*] swap-transform]
               [:add-booking [:sites :*] add-booking-transform]
               [:debug [:pedestal :**] swap-transform]]

   :effect #{[#{[:sites]} publish-sites :single-val]}
   
   :derive #{[#{[:sites :*]} [:sites-count] derive-count :vals]
             [#{[:sites]} [:free-sites] derive-free-nodes :single-val]}
   
   :emit [
          {:init init-main}
          [#{[:test :**]
             [:counter]
             [:sites :*]
             [:sites-count]
             [:free-sites]} (app/default-emitter [:main])]
          [#{[:pedestal :debug :*]} (app/default-emitter [])]]})

