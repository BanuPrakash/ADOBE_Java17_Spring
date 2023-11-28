insert into users(username, password, enabled) values ('Sam', '$2a$12$iGuWzXxHXdqLcP0SUN7EKeugErfK20LxWdJhqvYEJDskXnFQ6lBL2', 1);
insert into users(username, password, enabled) values ('Rita', '$2a$12$iGuWzXxHXdqLcP0SUN7EKeugErfK20LxWdJhqvYEJDskXnFQ6lBL2', 1);

--insert into users(username, password, enabled) values ('Sam', 'secret', 1);
--insert into users(username, password, enabled) values ('Rita', 'secret', 1);

insert into authorities(username, authority) values ('Sam', 'ROLE_READ');
insert into authorities(username, authority) values ('Rita', 'ROLE_READ');
insert into authorities(username, authority) values ('Sam', 'ROLE_ADMIN');