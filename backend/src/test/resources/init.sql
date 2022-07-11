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

INSERT INTO momo_group (categoryId, deadline, description, endDate, startDate, hostId, location, name, regular)
VALUES (1, '2022-07-11T00:52:01.456770', '', '2022-07-08', '2022-07-08', 1, '', '튼튼이 클럽', false),
       (2, '2022-07-12T10:30:01.456770', '', '2022-07-18', '2022-07-18', 1, '', 'CS 리뷰 스터디', false)
