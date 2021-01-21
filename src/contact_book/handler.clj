(ns contact-book.handler
  (:require [contact-book.db :as db]
            [clojure.walk :as walk]
            [clojure.data.json :as json]))

(defn json->map
  [req]
  (-> req
      (get :body)
      slurp
      json/read-str
      walk/keywordize-keys))

(defn get-contacts
  [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (db/get-contacts db/config)})

(defn get-contact-by-id
  [id]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (db/get-contact-by-id db/config
                                  {:contact-id (Integer/parseInt id)})})

(defn create-contact
  [req]
  (let [data       (json->map req)
        created-id {:contact-id (:contact_id (db/insert-contacts db/config data))}]
    {:status 200
     :body   (db/get-contact-by-id db/config created-id)}))

(defn update-contact
  [req id]
  (let [data (assoc (json->map req) :contact-id (Integer/parseInt id))]
    (db/update-contact-by-id db/config data)
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (db/get-contact-by-id db/config
                                    {:contact-id (Integer/parseInt id)})}))

(defn delete-contact 
  [id]
  (let [contact-id     {:contact-id (Integer/parseInt id)}
        before-deleted (db/get-contact-by-id db/config contact-id)]
    (db/delete-contact-by-id db/config contact-id)
    {:status 200
     :body   before-deleted}))

(defn delete-empty-contacts
  [req]
  (let [empty-contacts (db/get-empty-contacts db/config)]
    (db/delete-empty-contacts db/config)
    {:status 200
     :body empty-contacts}))

