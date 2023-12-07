drop table if exists user_role cascade;
drop table if exists city cascade;
drop table if exists country cascade;
drop table if exists users cascade;


create table users
(
    id       BIGINT primary key,
    name     varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table country
(
    id   BIGINT primary key,
    country_name varchar(255) not null,
    logo varchar(255) null
);

create table city
(
    id   BIGINT primary key,
    city_name varchar(255) not null,
    country_id BIGINT not null,
    constraint fk_city_country foreign key (country_id) references country(id) on delete cascade on update no action
);

create table user_role
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_role_users foreign key (user_id) references users(id) on delete cascade on update no action
);


