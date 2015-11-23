# Book schema

# --- !Ups

CREATE TABLE book (
    id varchar(255) NOT NULL,
    author varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE User;