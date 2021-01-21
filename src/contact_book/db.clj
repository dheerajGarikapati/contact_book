(ns contact-book.db
  (:require [hugsql.core :as hugsql]))

(def config
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname     "//localhost:5432/clojure_contacts"
   :user        "postgres"
   :password    "postgres"})

(hugsql/def-db-fns "contacts.sql")
  