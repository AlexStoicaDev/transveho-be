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
)
