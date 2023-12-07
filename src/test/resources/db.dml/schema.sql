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

create table user_role
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_role_users foreign key (user_id) references users (id) on delete cascade on update no action
);

CREATE TABLE weather
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    date  TIMESTAMP     NOT NULL,
    lat   NUMERIC(7, 4) NOT NULL,
    lon   NUMERIC(7, 4) NOT NULL,
    city  VARCHAR(255)  NOT NULL,
    state VARCHAR(255)  NOT NULL
);

CREATE TABLE temperature
(
    weather_id BIGINT,
    "value"      NUMERIC(4, 1),
    FOREIGN KEY (weather_id) REFERENCES weather (id) ON DELETE CASCADE ON UPDATE CASCADE
);



