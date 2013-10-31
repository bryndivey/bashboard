(ns bashboard.rendering
  (:require [domina :as dom]
            [io.pedestal.app.render.push :as render]
            [io.pedestal.app.render.push.handlers :as h]
            [io.pedestal.app.render.push.templates :as templates]
            [io.pedestal.app.render.push.handlers.automatic :as d])
  (:require-macros [bashboard.html-templates :as html-templates]))

;; Load templates.

(def templates (html-templates/bashboard-templates))


(defn render-template [template-name initial-value-fn]
  (fn [renderer [_ path :as delta] input-queue]
    (let [parent (render/get-parent-id renderer path)
          id (render/new-id! renderer path)
          html (templates/add-template renderer path (template-name templates))]
      (dom/append! (dom/by-id parent) (html (assoc (initial-value-fn delta) :id id))))))

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
  (.log js/console (str new-value))
  (templates/update-t renderer path {:bookings []})
  (doseq [[id booking] (:bookings new-value)]
    (let [id (render/new-id! renderer path)
          html (templates/add-template renderer (conj path :bookings) (:booking templates))]
      (templates/append-t renderer path {:bookings (html booking)}))))

(defn render-config []
  [[:node-create [:main]
    (render-template :bashboard-page (constantly {}))]
   [:node-destroy [:main] h/default-destroy]
   
   [:node-create [:main :sites] render-sites-element]

   [:node-create [:main :sites :*] create-site]
   [:value [:main :sites :*] render-site]
   [:node-destroy [:main :sites :*] h/default-destroy]

   
   [:value [:main :*] render-value]
   
   [:transform-enable [:main :sites] (h/add-send-on-click "clear-sites-button")]
   [:transform-disable [:main :sites] (h/remove-send-on-click "clear-sites-button")]])

