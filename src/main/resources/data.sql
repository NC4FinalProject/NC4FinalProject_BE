insert into member (password, role, user_nickname, username, email_verification, created_at, modified_at) values
    ('$2a$12$anLXoztRzTwiDhxX9ZMrs.1Y/dusENJGAjHWDdFwfmI6w0omaGa12', 'ADMIN','admin', 'admin@mail.com', false, NOW(), NOW()),
    ('$2a$12$anLXoztRzTwiDhxX9ZMrs.1Y/dusENJGAjHWDdFwfmI6w0omaGa12', 'RESIGNED', 'test1', 'test1@mail.com', false, NOW(), NOW());