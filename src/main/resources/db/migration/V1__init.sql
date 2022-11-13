
-- uuid extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists stadium
(
    stadium_id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique,
    city varchar(255) not null,
    capacity integer not null
) ;

create table if not exists team
(
    team_id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique
) ;

create table if not exists player
(
    player_id   serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null unique
) ;

create table if not exists game
(
    game_id   serial primary key,
    stadium_id integer not null,
    home_team_id integer not null,
    away_team_id integer not null,
    date timestamp not null,
    ratio_weight decimal not null,
    home_ratio decimal not null,
    away_ratio decimal not null,
    tie_ratio decimal not null,
    home_score integer,
    away_score integer
) ;

create table if not exists bet
(
    bet_id   serial primary key,
    user_id uuid not null,
    game_id integer not null,
    home_score integer not null,
    away_score integer not null
) ;

create table if not exists general_bet
(
    general_bet_id   serial primary key,
    user_id uuid not null,
    winning_team_id integer not null,
    golden_boot_player_id integer not null
) ;

create table if not exists users
(
    user_id uuid DEFAULT uuid_generate_v4 (),
    username varchar(255) not null unique,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null unique,
    is_admin boolean not null
) ;

create table if not exists cred
(
    uuid uuid not null primary key,
    pw varchar(255) not null
) ;

insert into users(user_id, username, first_name, last_name, email, is_admin)
values
('00112233-4455-6677-c899-aabbccddeeff', 'oshai', 'Ohad', 'Shai', 'dummy@gmail.com', true),
('00112234-4455-6677-c899-aabbccddeeff', 'monkey', 'Monkey', 'Monk', 'monkey@zoo.com', false),
('00112235-4455-6677-c899-aabbccddeeff', 'winner', 'Always', 'Low', 'alwayslow@gmail.com', false),
('00112236-4455-6677-c899-aabbccddeeff', 'loser', 'Always', 'High', 'alwayshigh@gmail.com', false),
('00112237-4455-6677-c899-aabbccddeeff', 'zero', 'Always', 'Zero', 'alwayszero@gmail.com', false)

;

insert into cred (uuid, pw) select user_id,'oshai' from users;

