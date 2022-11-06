create table if not exists stadium
(
    id   serial primary key,
    name varchar(255) not null,
    city varchar(255) not null,
    capacity integer not null
) ;