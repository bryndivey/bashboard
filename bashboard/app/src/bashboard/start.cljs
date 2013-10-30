(ns bashboard.start
  (:require [io.pedestal.app.protocols :as p]
            [io.pedestal.app :as app]
            [io.pedestal.app.render.push :as push-render]
            [io.pedestal.app.render :as render]
            [io.pedestal.app.messages :as msg]
            [bashboard.behavior :as behavior]
            [bashboard.rendering :as rendering]
            [bashboard.services :as services]))

;; In this namespace, the application is built and started.

(defn create-app [render-config]
  (let [app (app/build behavior/example-app)
        render-fn (push-render/renderer "content" render-config render/log-fn)
        app-model (render/consume-app-model app render-fn)]
    (app/begin app)

    (comment
      (doseq [site ["zadev1" "zadev2" "zadev3" "zadev4"]]
        (p/put-message (:input app) {msg/type :swap msg/topic [:sites site]}))

      (p/put-message (:input app) {msg/type :append msg/topic [:sites "zadev1" :bookings] :value {:owner "bryn"}}))



    {:app app :app-model app-model}))

(defn ^:export main []
  ;; config/config.edn refers to this namespace as a main namespace
  ;; for several aspects. A main namespace must have a no argument
  ;; main function. To tie into tooling, this function should return
  ;; the newly created app.
  (let [app (create-app (rendering/render-config))
        services (services/->Services (:app app))]
    (app/consume-effects (:app app) services/services-fn)
    (p/start services)
    app))
