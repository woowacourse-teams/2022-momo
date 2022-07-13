drop table if exists momo_category CASCADE;
drop table if exists momo_group CASCADE;
drop table if exists momo_member CASCADE;
drop table if exists momo_schedule CASCADE;

create table momo_category
(
    id   bigint generated by default as identity,
    name varchar(30) not null,
    primary key (id)
);

create table momo_group
(
    id          bigint generated by default as identity,
    categoryId  bigint       not null,
    deadline    timestamp    not null,
    description clob         not null,
    endDate     date         not null,
    startDate   date         not null,
    hostId      bigint       not null,
    location    varchar(255) not null,
    name        varchar(255) not null,
    primary key (id)
);

create table momo_member
(
    id       bigint generated by default as identity,
    email    varchar(255) not null,
    name     varchar(30)  not null,
    password varchar(255) not null,
    primary key (id)
);

create table momo_schedule
(
    id        bigint generated by default as identity,
    date      date not null,
    endTime   time not null,
    startTime time not null,
    group_id  bigint,
    primary key (id)
);

INSERT INTO momo_category (name)
VALUES ('스터디'),
       ('모각코'),
       ('식사'),
       ('카페'),
       ('술'),
       ('운동'),
       ('게임'),
       ('여행'),
       ('기타');

