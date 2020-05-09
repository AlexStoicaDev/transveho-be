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
)
