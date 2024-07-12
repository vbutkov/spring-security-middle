create table if not exists t_user
(
    id       int primary key,
    username varchar not null unique
);

create table if not exists t_user_password
(
    id       serial primary key,
    id_user  int not null unique references t_user (id),
    password text
);

create table if not exists t_user_authority
(
    id        serial primary key,
    id_user   int     not null references t_user (id),
    authority varchar not null
);

