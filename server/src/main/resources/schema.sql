create table if not exists products (
    ean varchar(15) primary key,
    name varchar(255),
    brand varchar(255)
);

create table if not exists profiles (
    email varchar(255) primary key,
    name varchar(255)
);

insert into products (ean, name, brand)
values ('ABC1','NAME 1','BRAND 1');
insert into products (ean, name, brand)
values ('ABC2','NAME 2','BRAND 5');
insert into products (ean, name, brand)
values ('ABC3','NAME 3','BRAND 1');

insert into profiles (email, name)
values ('sample@email.com','USER 1');
insert into profiles (email, name)
values ('prova@email.it','USER 2');
insert into profiles (email, name)
values ('sample2@email.com','USER 3');