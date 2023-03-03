(ns chrisbroome.backend.handlers
  (:require [com.stuartsierra.component :as component]
            [compojure.core :refer [defroutes GET]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.util.response :as resp]))

(defn handler [_]
  (-> (resp/resource-response "public/index.html")
      (resp/content-type "text/html")))

(defn favicon [_]
  (resp/resource-response "public/images/favicon.ico"))

(defroutes c-router
  (GET "/favicon.ico" [] #'favicon)
  (GET "/" [] #'handler))

(defn create-app
  "Create an instance of our ring api handlers"
  []
  (wrap-defaults #'c-router site-defaults))

(defrecord ApiApp [handler]
  component/Lifecycle
  (start [component]
    (println "Starting ApiApp")
    (assoc component :handler handler))
  (stop [component]
    (println "Stopping ApiApp")
    (assoc component :handler nil)))

(defn new-api-app [handler]
  (map->ApiApp {:handler handler}))
