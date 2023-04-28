create table if not exists products
(
    ean      varchar(15)  NOT NULL,
    sku      varchar(255) NOT NULL,
    name     varchar(255) NOT NULL,
    brand    varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    price    float8 DEFAULT 0.0 CHECK (price > 0.0),

    PRIMARY KEY (ean)
);

create table if not exists profiles
(
    email varchar(255) NOT NULL,
    name  varchar(255) NOT NULL,
    role  varchar(255) DEFAULT 'customer' CHECK (role IN ('customer', 'admin', 'manager', 'technician')),
    phone varchar(255) NOT NULL,

    PRIMARY KEY (email)
);

---

insert into products (ean, sku, name, brand, category, price)
values ('0000001', 'spa-01', 'Sword', 'Blacksmith&Co', 'Weapons', 10.45);
insert into products (ean, sku, name, brand, category, price)
values ('0000002', 'spa-02', 'LightSaber', 'Blacksmith&Co', 'Weapons', 10.45);
insert into products (ean, sku, name, brand, category, price)
values ('0000003', 'spa-03', 'MachineGun', 'Blacksmith&Co', 'Weapons', 10.45);

insert into profiles (email, name, role, phone)
values ('user01@polito.it', 'Rosa Olinda', 'customer', '+00 190283947');
insert into profiles (email, name, role, phone)
values ('user02@polito.it', 'Michele Misteri', 'manager', '+00 287465392');
insert into profiles (email, name, role, phone)
values ('user03@polito.it', 'Pietro Piccioni', 'technician', '+00 192740387');
insert into profiles (email, name, role, phone)
values ('user04@polito.it', 'Giuseppe Boschetti', 'customer', '+00 985129374');