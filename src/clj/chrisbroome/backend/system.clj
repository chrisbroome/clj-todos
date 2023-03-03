(ns chrisbroome.backend.system
  (:require [chrisbroome.backend.api-server :refer [new-api-server]]
            [chrisbroome.backend.handlers :refer [create-app new-api-app]]
            [com.stuartsierra.component :as component]))


(defn system [config-options]
  (let [{api-port :api-server/port
         api-join? :api-server/join?} config-options]
    (component/system-map
     :api-server (component/using
                  (new-api-server api-port api-join?)
                  {:app :app})
     :app (component/using
           (new-api-app (create-app))
           {}))))
