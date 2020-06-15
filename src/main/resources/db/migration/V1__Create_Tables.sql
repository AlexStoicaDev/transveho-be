create table users (
    id bigint not null auto_increment,
    email varchar(50) not null unique,
    password varchar(120),
    role varchar(255),
    username varchar(20) not null unique,
    spoken_language varchar(255),
    first_name varchar(12),
    last_name varchar(120),
    user_status varchar(255),
    phone_number varchar(12),
    driving_license_category varchar(255),

    primary key (id)
);

create table cars (
    id bigint not null auto_increment,
    chassis_number varchar(255) not null unique,
    engine_type varchar(255),
    huvignette_expiration_date date,
    is_in_transit bit not null default false,
    is_rented bit not null,
    itp_expiration_date date,
    model varchar(255),
    number_of_seats integer,
    others varchar(255),
    plate_number varchar(10) not null unique,
    rca_expiration_date date,
    rovignette_expiration_date date,
    status varchar(255),

    primary key (id)
);


create table routes (
   id bigint not null auto_increment,
   distance_in_km integer,
   from_location varchar(120),
   notes varchar(255),
   price_in_eur integer,
   price_in_ron integer,
   to_location varchar(120),
   is_transit_route boolean not null default true,
   return_route_id bigint,

   primary key (id)
);

 create table passengers (
   id bigint not null auto_increment,
   number_of_adults integer default 0,
   number_of_children integer default 0,
   number_of_infants integer default 0,
   destination_address varchar(255),
   email varchar(40),
   first_name varchar(50),
   flight_date_time timestamp,
   flight_details varchar(255),
   last_name varchar(50),
   paid_for_transfer bit not null,
   payment_method varchar(255),
   phone_number varchar(12),
   pick_up_address varchar(255),
   pick_up_date_time timestamp,
   return_destination_address varchar(255),
   return_flight_details varchar(255),
   return_pick_up_address varchar(255),
   return_pick_up_date_time timestamp,
   return_transfer bit not null,
   transport_type varchar(255),
   route_id bigint,
   transfer_id bigint,
   status varchar(255),

   primary key (id)
);

create table transfers (
   id bigint not null auto_increment,
   car_id bigint,
   driver_id bigint,
   route_id bigint,

   primary key (id)
);
