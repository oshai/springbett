
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists stadium
(
    id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique,
    city varchar(255) not null,
    capacity integer not null
) ;

create table if not exists team
(
    id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique
) ;

create table if not exists player
(
    id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique
) ;

create table if not exists users
(
    id uuid DEFAULT uuid_generate_v4 (),
    username varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    is_admin boolean not null
) ;

create table if not exists cred
(
    id uuid,
    pw varchar(255) not null
) ;

insert into users(username, first_name, last_name, email, is_admin)
values ('oshai', 'ohad', 'shai', 'dummy@gmail.com', true);

insert into cred (id, pw) select id,'oshai' from users;

