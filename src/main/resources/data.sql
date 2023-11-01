INSERT INTO member(member_role, created_at, updated_at, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','2023-12-11T23:59:59','2023-12-11T23:59:59','test1@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', '최소영', '01033334444', 100L, 'https://picsum.photos/200', true);
INSERT INTO member(member_role, created_at, updated_at, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','2023-12-11T23:59:59','2023-12-11T23:59:59','test2@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', '이채민', '01011112222', 500L, 'https://picsum.photos/200', true);
INSERT INTO member(member_role, created_at, updated_at, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','2023-12-11T23:59:59','2023-12-11T23:59:59','test3@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', '이상원', '01055556666', 700L, 'https://picsum.photos/200', true);
INSERT INTO member(member_role, created_at, updated_at, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','2023-12-11T23:59:59','2023-12-11T23:59:59','test4@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', '이진우', '01033376666', 1000L, 'https://picsum.photos/200', true);
INSERT INTO member(member_role, created_at, updated_at, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','2023-12-11T23:59:59','2023-12-11T23:59:59','test5@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', '이우엽', '01033344333', 5000L, 'https://picsum.photos/200', true);

INSERT INTO membership_policy(membership_policy_grade, membership_policy_subscription_fee, membership_policy_discount_rate, membership_policy_point_accumulation_rate) VALUES('펀딩프렌즈', 6900L, 3, 1);
INSERT INTO membership_policy(membership_policy_grade, membership_policy_subscription_fee, membership_policy_discount_rate, membership_policy_point_accumulation_rate) VALUES('펀딩파트너', 9900L, 5, 2);

INSERT INTO likes(project_id, member_id, created_at) VALUES (1, 1, '2023-12-11T23:59:59');
INSERT INTO likes(project_id, member_id, created_at) VALUES (2, 1, '2023-12-11T23:59:59');
INSERT INTO likes(project_id, member_id, created_at) VALUES (3, 1, '2023-12-11T23:59:59');

INSERT INTO likes(project_id, member_id, created_at) VALUES (1, 4, '2023-12-11T23:59:59');
INSERT INTO likes(project_id, member_id, created_at) VALUES (2, 4, '2023-12-11T23:59:59');
INSERT INTO likes(project_id, member_id, created_at) VALUES (3, 4, '2023-12-11T23:59:59');

INSERT INTO membership(membership_id, membership_status, membership_subscription_created_at, membership_subscription_expired_at, membership_subscription_id, member_id, membership_policy_id) VALUES(1, true, '2023-12-11T23:59:59', '2024-12-11T23:59:59', 1, 4, 1)