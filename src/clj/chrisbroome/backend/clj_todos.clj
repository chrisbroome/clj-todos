(ns chrisbroome.backend.clj-todos
  (:require [compojure.core :refer [GET defroutes]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [site-defaults
                                              wrap-defaults]]
            [ring.util.response :as resp]
            [selmer.parser :as html])
  (:gen-class))

(defn handler [req]
  (-> (resp/resource-response "public/index.html")
      (doto tap>)
      (resp/content-type "text/html")))

(defn favicon [req]
  (resp/resource-response "public/images/favicon.ico"))

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

(comment

  (greet {:name "blah"})
  #_(require '[clojure.tools.deps.alpha.repl :refer [add-libs]])
  (require '[portal.api :as portal])
  (require '[clojure.datafy :as d])
  (def p (portal/open {}))
  (def submit (comp portal/submit d/datafy))
  (add-tap #'submit)

  (def server (run-jetty #'app {:join? false :port 8888}))
  (-> server .stop)

  (portal/url p)
  server
  *e)
