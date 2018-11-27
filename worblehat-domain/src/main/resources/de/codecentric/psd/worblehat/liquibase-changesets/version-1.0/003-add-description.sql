-- liquibase formatted sql

-- changeset action:add_description
ALTER TABLE book
ADD description TEXT;
