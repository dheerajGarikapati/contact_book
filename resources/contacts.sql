-- :name create-contacts-table
-- :command :execute
-- :result :raw
-- :doc creates table in this case contacts
CREATE TABLE if NOT EXISTS contacts(
    contact_id serial PRIMARY KEY,
    first_name text,
    last_name text,
    email text,
    created_at timestamp NOT NULL DEFAULT current_timestamp);

-- :name get-contacts :? :*
SELECT * FROM contacts ORDER BY created_at DESC;

-- :name get-contact-by-id :? :*
SELECT * FROM contacts WHERE contact_id = :contact-id;

-- :name insert-contacts :insert :*
INSERT INTO contacts (first_name, last_name, email)
VALUES (:first-name, :last-name, :email)
returning contact_id;

-- :name update-contact-by-id :! :1
UPDATE CONTACTS
SET FIRST_NAME = :first-name, LAST_NAME = :last-name, EMAIL = :email
WHERE CONTACT_ID = :contact-id;

-- :name delete-contact-by-id :! :1
DELETE FROM CONTACTS WHERE CONTACT_ID = :contact-id;

-- :name get-empty-contacts :? :*
SELECT * FROM contacts WHERE first_name IS NULL AND last_name IS NULL AND email IS NULL;

-- :name delete-empty-contacts :! :*
DELETE FROM contacts WHERE first_name IS NULL AND last_name IS NULL AND email IS NULL;