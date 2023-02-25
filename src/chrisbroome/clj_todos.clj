(ns chrisbroome.clj-todos
  (:require [compojure.core :refer [GET defroutes]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [site-defaults
                                              wrap-defaults]]
            [ring.util.response :as resp]
            [selmer.parser :as html])
  (:gen-class))

(defn handler [req]
  (-> (resp/response (html/render-file "index.html" {:name "the repl"}))
      (doto tap>)
      (resp/content-type "text/html")))

(defn favicon [req]
  (resp/resource-response "images/favicon.ico"))

(defroutes c-router
  (GET "/favicon.ico" [] #'favicon)
  (GET "/" [] #'handler))

(def app (wrap-defaults #'c-router site-defaults))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
