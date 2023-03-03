(ns chrisbroome.backend.clj-todos
  (:require [chrisbroome.backend.system :as system]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main [& args]
  (component/start (system/system args)))

(comment
  (def system (system/system {:api-server/port 3333}))
  (alter-var-root #'system component/start)
  (alter-var-root #'system component/stop)

  (require '[portal.api :as portal])
  (require '[clojure.datafy :as d])
  (def p (portal/open {}))
  (def submit (comp portal/submit d/datafy))
  (add-tap #'submit)
  (portal/url p)
  *e)
