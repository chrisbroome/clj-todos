(ns chrisbroome.backend.api-server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer [run-jetty]]))

(defrecord ApiServer [port join? app server]
  component/Lifecycle

  (start [component]
    (if server
      (do
        (println "Api Server already started")
        component)
      (do
        (println "Starting Api Server")
        (let [{:keys [handler]} app
              server (run-jetty handler {:join? join?
                                         :port  port})]
          (assoc component :server server)))))

  (stop [component]
    (if (not server)
      (do
        (println "Api Server already stopped")
        component)
      (do
        (println "Stopping Api Server")
        (-> server .stop)
        (assoc component :server nil)))))

(defn new-api-server [port join?]
  (map->ApiServer {:port (or port 8888)
                   :join? (or join? false)}))
