insert into t_user(id, username)
values (1, 'user1');

insert into t_user_password(id_user, password)
values (1, '{noop}password');

insert into t_user_authority(id_user, authority)
values (1, 'ROLE_USER'),
       (1, 'ROLE_DB_USER');