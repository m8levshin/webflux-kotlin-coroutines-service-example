create table breed
(
    id         integer generated by default as identity
        constraint id_pk primary key,
    name       varchar not null,
    sub_breeds varchar,
    img        varchar
);