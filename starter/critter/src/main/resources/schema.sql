CREATE TABLE IF NOT EXISTS pet (
    id bigint NOT NULL AUTO_INCREMENT,
    type nvarchar(255) NULL,
    name nvarchar(255) NULL,
    birth_date DATE,
    notes nvarchar(700) NULL,
    owner_id bigint NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS customer (
    id bigint NOT NULL AUTO_INCREMENT,
    name nvarchar(255) NOT NULL,
    phone_number nvarchar(255) NULL,
    notes nvarchar(700) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employee (
    id bigint NOT NULL AUTO_INCREMENT,
    name nvarchar(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS schedule (
    id bigint NOT NULL AUTO_INCREMENT,
    date DATE NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS skill (
    id bigint NOT NULL AUTO_INCREMENT,
    skill_name nvarchar(255) NOT NULL,
    employee_id bigint NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS day_of_week (
    id bigint NOT NULL AUTO_INCREMENT,
    day_name nvarchar(255) NOT NULL,
    employee_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS join_schedule (
    schedule_id bigint NOT NULL,
    pet_id bigint NULL,
    employee_id bigint NULL,
    skill_name nvarchar(255) NULL
);

