create table if not exists products (
    ean varchar(15) NOT NULL,
    sku varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    brand varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    price float8 DEFAULT 0.0 CHECK (price > 0.0),

    PRIMARY KEY (ean)
);

create table if not exists profiles (
    email varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    role varchar(255) DEFAULT 'customer' CHECK (role IN ('customer', 'admin', 'manager', 'technician')),
    phone varchar(255) NOT NULL,

    PRIMARY KEY (email)
);

create table if not exists products_customers (
    primary_key int NOT NULL,
    customer varchar(255) NOT NULL,
    product varchar(255) NOT NULL,
    purchase_date date NOT NULL,
    warranty_expiration date NOT NULL CHECK (warranty_expiration >= purchase_date),

    PRIMARY KEY (primary_key),
    FOREIGN KEY (customer) REFERENCES profiles(email),
    FOREIGN KEY (product) REFERENCES products(ean)
);

create table if not exists ticket (
    primary_key int NOT NULL,
    assistance int NOT NULL,
    technician varchar(255),
    status varchar(255) DEFAULT 'pending' CHECK (status IN ('pending', 'assigned', 'completed')),
    note varchar(255),
    image boolean DEFAULT false,

    PRIMARY KEY (primary_key),
    FOREIGN KEY (assistance) REFERENCES products_customers(primary_key),
    FOREIGN KEY (technician) REFERENCES profiles(email)
);

---

insert into products (ean, sku, name, brand, category, price) values ('0000001', 'spa-01', 'Sword', 'Blacksmith&Co', 'Weapons', 10.45);
insert into products (ean, sku, name, brand, category, price) values ('0000002', 'spa-02', 'LightSaber', 'Blacksmith&Co', 'Weapons', 10.45);
insert into products (ean, sku, name, brand, category, price) values ('0000003', 'spa-03', 'MachineGun', 'Blacksmith&Co', 'Weapons', 10.45);

insert into profiles (email, name, role, phone) values ('user01@polito.it', 'Rosa Olinda', 'customer', '+00 190283947');
insert into profiles (email, name, role, phone) values ('user02@polito.it', 'Michele Misteri', 'manager', '+00 287465392');
insert into profiles (email, name, role, phone) values ('user03@polito.it', 'Pietro Piccioni', 'technician', '+00 192740387');
insert into profiles (email, name, role, phone) values ('user04@polito.it', 'Giuseppe Boschetti', 'customer', '+00 985129374');