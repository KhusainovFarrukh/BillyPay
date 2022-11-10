SELECT nextval('app_user_id_seq');

INSERT INTO app_user (id, email, phone_number, password, name, role)
VALUES (currval('app_user_id_seq'),
        'farrukh@mail.com',
        '+998987654321',
        '$2a$10$CRGrEXkQfzzR86qUYelPy.B6GznYVQBg93vi56eF4wk8NUgUtiEB.',
        'Farrukh Khusainov',
        'SUPER_ADMIN');
INSERT INTO app_user (id, email, phone_number, password, name, role)
VALUES (currval('app_user_id_seq') + 1,
        'admin@mail.com',
        '+998998887766',
        '$2a$10$CRGrEXkQfzzR86qUYelPy.B6GznYVQBg93vi56eF4wk8NUgUtiEB.',
        'Admin (Moderator)',
        'ADMIN');
INSERT INTO app_user (id, email, phone_number, password, name, role)
VALUES (currval('app_user_id_seq') + 2,
        'user@mail.com',
        '+998997776655',
        '$2a$10$CRGrEXkQfzzR86qUYelPy.B6GznYVQBg93vi56eF4wk8NUgUtiEB.',
        'User (Tester)',
        'USER');

SELECT setval('app_user_id_seq', currval('app_user_id_seq') + 2);
