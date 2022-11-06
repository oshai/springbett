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

