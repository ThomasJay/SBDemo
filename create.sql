create table users
(
    id         varchar(64) not null,
    email      varchar(200),
    first_name varchar(200),
    last_name  varchar(200),
    password   varchar(64),
    primary key (id)
) engine=InnoDB;
