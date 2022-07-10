drop table if exists momo_category CASCADE;
drop table if exists momo_group CASCADE;
drop table if exists momo_member CASCADE;
drop table if exists momo_schedule CASCADE;

create table momo_category
(
    id   bigint auto_increment,
    name varchar(30) not null,
    primary key (id)
);

create table momo_group
(
    id         bigint auto_increment,
    categoryId bigint       not null,
    deadline   timestamp    not null,
    description clob not null,
    endDate    date         not null,
    startDate  date         not null,
    hostId     bigint       not null,
    location   varchar(255) not null,
    name       varchar(255) not null,
    regular    boolean      not null,
    primary key (id)
);

create table momo_member
(
    id   bigint auto_increment,
    name varchar(30) not null,
    primary key (id)
);

create table momo_schedule
(
    id             bigint auto_increment,
    endTime        time not null,
    reservationDay varchar(255),
    startTime      time not null,
    group_id       bigint,
    primary key (id)
);


INSERT INTO momo_category (name)
VALUES ('운동'),
       ('스터디'),
       ('한 잔'),
       ('영화'),
       ('모각코');

INSERT INTO momo_member (name)
VALUES ('momo');
