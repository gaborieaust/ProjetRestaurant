create table tables
(
    "TableID"      serial not null
        constraint table_pk
            primary key,
    "Nom"          varchar,
    "NumberGuests" integer
);

alter table tables
    owner to postgres;

create table "Plat"
(
    "PlatID" serial not null
        constraint plat_pk
            primary key,
    "Name"   varchar,
    pxunit   double precision
);

alter table "Plat"
    owner to postgres;

create table waiter
(
    "WaiterID" serial not null
        constraint waiter_pk
            primary key,
    firstname  varchar,
    lastname   varchar
);

alter table waiter
    owner to postgres;

create table invoice
(
    "invoiceID" serial not null
        constraint invoice_pk
            primary key,
    waiterfk    integer
        constraint invoice_waiter_waiterid_fk
            references waiter,
    tablefk     integer
        constraint invoice_table_tableid_fk
            references tables
);

alter table invoice
    owner to postgres;

create table "table_Jointure"
(
    "Invoicefk" integer
        constraint table_jointure_invoice_invoiceid_fk
            references invoice,
    platfk      integer
        constraint table_jointure_plat_platid_fk
            references "Plat",
    quantity    integer
);

alter table "table_Jointure"
    owner to postgres;

