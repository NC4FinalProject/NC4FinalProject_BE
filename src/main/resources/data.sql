INSERT IGNORE INTO member (password, role, user_nickname, username, email_verification, created_at, modified_at)
VALUES ('$2a$12$anLXoztRzTwiDhxX9ZMrs.1Y/dusENJGAjHWDdFwfmI6w0omaGa12', 'ADMIN', 'admin', 'admin@mail.com', 'false',
        NOW(), NOW()),
       ('$2a$12$anLXoztRzTwiDhxX9ZMrs.1Y/dusENJGAjHWDdFwfmI6w0omaGa12', 'USER', 'test1', 'test1@mail.com', 'false',
        NOW(), NOW()),
       ('$2a$12$anLXoztRzTwiDhxX9ZMrs.1Y/dusENJGAjHWDdFwfmI6w0omaGa12', 'RESIGNED', 'test2', 'test2@mail.com', 'false',
        NOW(), NOW());
INSERT IGNORE INTO point (member_id, value, reason, created_at)
VALUES (2, 1000, 'DATA.SQL로 들어간 테스트 데이터 1 입니다.', NOW()),
       (2, 2000, 'DATA.SQL로 들어간 테스트 데이터 2 입니다.', '2024-04-01'),
       (2, -1000, 'DATA.SQL로 들어간 마이너스 데이터 3 입니다.', '2024-04-01'),
       (2, 2000, 'DATA.SQL로 들어간 날짜가 뒤쪽으로 지정된 데이터 4 입니다.', '2024-04-03');