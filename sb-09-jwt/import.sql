CREATE DATABASE IF NOT EXISTS `jwt`;

USE `jwt`;

CREATE TABLE jwt.authority
(
    name varchar(20) null
)
    charset = utf8;
CREATE TABLE jwt.user
(
    id        int auto_increment
        primary key,
    username  varchar(20)  null,
    password  char(255)    null,
    firstname varchar(20)  null,
    lastname  varchar(20)  null,
    activated int          null,
    email     varchar(100) null
)
    charset = utf8;
CREATE TABLE jwt.user_authority
(
    user_id        int         null,
    authority_name varchar(20) null
)
    charset = utf8;

# Admin - admin:admin
# User - user:password
# Disabled - disabled:password (this user is deactivated)
INSERT INTO jwt.user (id, username, password, firstname, lastname, email, activated) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1);
INSERT INTO jwt.user (id, username, password, firstname, lastname, email, activated) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1);
INSERT INTO jwt.user (id, username, password, firstname, lastname, email, activated) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0);

INSERT INTO jwt.authority (name) VALUES ('ROLE_USER');
INSERT INTO jwt.authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO jwt.user_authority (user_id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO jwt.user_authority (user_id, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO jwt.user_authority (user_id, authority_name) VALUES (2, 'ROLE_USER');