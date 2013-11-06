(ns bashboard.rendering
  (:require [domina :as dom]
            [io.pedestal.app.render.events :as events]
            [io.pedestal.app.render.push :as render]
            [io.pedestal.app.render.push.handlers :as h]
            [io.pedestal.app.render.push.templates :as templates]
            [io.pedestal.app.render.push.handlers.automatic :as d]
            [io.pedestal.app.util.log :as log])
  (:require-macros [bashboard.html-templates :as html-templates]))

;; Load templates.

(def templates (html-templates/bashboard-templates))


(defn render-template [template-name initial-value-fn]
  (fn [renderer [_ path :as delta] input-queue]
    (let [parent (render/get-parent-id renderer path)
          id (render/new-id! renderer path)
          html (templates/add-template renderer path (template-name templates))]
      (dom/append! (dom/by-id parent) (html (assoc (initial-value-fn delta) :id id))))))

(defn render-static-template [template-name]
  (fn [renderer [_ path :as delta] input-queue]
    (let [parent (render/get-parent-id renderer path)
          id (render/new-id! renderer path)
          html (template-name templates)]
      (dom/append! (dom/by-id parent) (html {:id id})))))


(defn render-value [renderer [_ path _ new-value] input-queue]
  (let [key (last path)]
    (templates/update-t renderer [:main] {key (str new-value)})))


(defn render-sites-element [renderer [_ path] _]
  (render/new-id! renderer path "sites"))

(defn create-site [renderer [_ path _] _]
  (let [parent (render/get-parent-id renderer path)
        id (render/new-id! renderer path)
        html (templates/add-template renderer path (:site templates))]
    (dom/append! (dom/by-id parent) (html {:id id :name (last path)}))))

(defn render-site [renderer [_ path _ new-value] input-queue]
  (templates/update-t renderer path {:bookings []})
  (doseq [[id booking] (:bookings new-value)]
    (let [id (render/new-id! renderer path)
          html (templates/add-template renderer (conj path :bookings) (:booking templates))]
      (templates/append-t renderer path {:bookings (html booking)}))))


;; login handlers
(defn add-submit-login-handler [_ [_ path transform-name messages] input-queue]
  (events/collect-and-send :click "login-button" input-queue transform-name messages
                           {"login-name" :value}))

(defn remove-submit-login-handler [_ _ _]
  (events/remove-click-event "login-button"))


(defn render-config []
  [
   ;; login rendering

   [:node-create [:login] (render-static-template :login-page)]
   [:node-destroy [:login] h/default-destroy]

   [:transform-enable [:login :name] add-submit-login-handler]
   [:transform-disable [:login :name] remove-submit-login-handler]
   
   ;; main screen rendering

   [:node-create [:main]
    (render-template :bashboard-page (constantly {}))]
   [:node-destroy [:main] h/default-destroy]
   
   [:node-create [:main :sites] render-sites-element]

   [:node-create [:main :sites :*] create-site]
   [:value [:main :sites :*] render-site]
   [:node-destroy [:main :sites :*] h/default-destroy]

   
   [:value [:main :*] render-value]
   
   [:transform-enable [:main :sites] (h/add-send-on-click "clear-sites-button")]
   [:transform-disable [:main :sites] (h/remove-send-on-click "clear-sites-button")]])

