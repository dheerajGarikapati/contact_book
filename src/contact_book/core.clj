(ns contact-book.core
  (:require [ring.adapter.jetty :as jetty]
            [contact-book.routes :as cbroute]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn -main
  [& args]
  (-> cbroute/myroutes
      (wrap-defaults  (assoc-in site-defaults [:security :anti-forgery] false))
      (jetty/run-jetty {:port  8080
                        :join? false}))
  (println "Server Started"))