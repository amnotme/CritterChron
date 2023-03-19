create table if not exists pet (
    id bigint NOT NULL AUTO_INCREMENT,
    type nvarchar(255) NULL,
    name nvarchar(255) NULL,
    birth_date DATE,
    notes nvarchar(700) NULL,
    owner_id bigint NULL,
    primary key (id)
);


create table if not exists customer (
    id bigint NOT NULL AUTO_INCREMENT,
    name nvarchar(255) NOT NULL,
    phone_number nvarchar(255) NULL,
    notes nvarchar(700) NULL,
    primary key (id)
);

create table if not exists employee (
    id bigint NOT NULL AUTO_INCREMENT,
    name nvarchar(255) NOT NULL,
    primary key (id)

);


create table if not exists schedule (
    id bigint NOT NULL AUTO_INCREMENT,
    date DATE,
    primary key (id)

);