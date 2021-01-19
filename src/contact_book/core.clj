(ns contact-book.core
  (:require [org.httpkit.server :as httpkit]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-request-middleware
                                                     format-response-middleware]]
            [muuntaja.core :as m]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/api" {:get (fn [request]
                     {:status 200
                      :body [:result "Success"]})}]]
    {:data {:muuntaja   m/instance
            :middleware [format-negotiate-middleware
                         format-response-middleware
                         exception-middleware
                         format-request-middleware]}})
   (ring/routes
   (ring/redirect-trailing-slash-handler 
     {:status 301 :body "Redireting to /api"})
   (ring/create-default-handler
     {:not-found (constantly {:status 404
                          :body {:hello "Route not Found"}})}))))



(defn -main
"I don't do a whole lot ... yet."
[& args]
(println "Server Started")
(reset! server (httpkit/run-server app {:port 8080})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn restart-server []
  (stop-server)
  #_(-main))

(comment
  (restart-server)
  @server
  (app {:request-method :get
        :uri "/api/"}))