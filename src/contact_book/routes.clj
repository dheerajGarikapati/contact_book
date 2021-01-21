(ns contact-book.routes
  (:require [contact-book.handler :as handler]
            [compojure.core :refer [defroutes DELETE GET POST PUT]]))

(def show-all-contacts
  (GET "/" [] handler/get-contacts))

(def show-contact
  (GET "/:id" [id] (handler/get-contact-by-id id)))

(def create-contact
  (POST "/new-contact" req (handler/create-contact req)))

(def alter-contact
  (PUT "/update/:id" [id] (fn [req] (handler/update-contact req id))))

(def delete-contact-id
  (DELETE "/delete-contact/:id" [id] (handler/delete-contact id)))

(def delete-contact-by-query
  (DELETE "/delete-empty-contacts" [] handler/delete-empty-contacts))

(defroutes myroutes
  show-all-contacts
  show-contact
  create-contact
  alter-contact
  delete-contact-id
  delete-contact-by-query)