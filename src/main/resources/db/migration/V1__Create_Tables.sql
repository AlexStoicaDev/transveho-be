create table users (
    id bigint not null auto_increment,
    email varchar(50) not null unique,
    password varchar(120),
    role varchar(255),
    username varchar(20) not null unique,
    primary key (id)
)

-- alter table users
--    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username)
--
-- alter table users
--    add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
