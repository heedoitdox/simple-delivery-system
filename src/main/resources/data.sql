INSERT INTO user(id, created_at, updated_at, identifier, name, password)
VALUES(1,CURRENT_TIMESTAMP(6),NULL,'tester1@barogo.com','테스터1', '{bcrypt}$2a$10$bBETTP7kGKw7XUS2tWo6buGbbRStGHjzPdpa3CZIj1zzJmzY5DTdW');
INSERT INTO user(id, created_at, updated_at, identifier, name, password)
VALUES(2,CURRENT_TIMESTAMP(6),NULL,'tester2@barogo.com','테스터2', '{bcrypt}$2a$10$bBETTP7kGKw7XUS2tWo6buGbbRStGHjzPdpa3CZIj1zzJmzY5DTdW');

INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(1,CURRENT_TIMESTAMP(6),NULL,'서울특별시 관악구','도림천빌라', 25000, '2023-03-17 15:30:23', 'COMPLETED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(2,CURRENT_TIMESTAMP(6),NULL,'서울특별시 관악구2','도림천빌라2', 34000, '2023-03-17 20:00:23', 'CANCELED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(3,CURRENT_TIMESTAMP(6),NULL,'서울특별시 관악구2','도림천빌라2', 34000, '2023-03-17 20:00:23', 'COMPLETED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(4,CURRENT_TIMESTAMP(6),NULL,'서울특별시 서초구 23-45','곰돌빌딩 1211호', 55000, '2023-03-18 09:00:23', 'COMPLETED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(5,CURRENT_TIMESTAMP(6),NULL,'서울특별시 서초구 23-45','곰돌빌딩 1211호', 24000, '2023-03-18 10:00:23', 'COMPLETED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(6,CURRENT_TIMESTAMP(6),NULL,'서울특별시 서초구 23-45','곰돌빌딩 1211호', 70000, '2023-03-18 22:00:23', 'COMPLETED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(7,CURRENT_TIMESTAMP(6),NULL,'서울특별시 서초구 23-45','곰돌빌딩 1211호', 22000, '2023-05-05 22:00:23', 'REQUESTED', 1);
INSERT INTO delivery(id, created_at, updated_at, main_address, sub_address, price, requested_at, status, user_id)
VALUES(8,CURRENT_TIMESTAMP(6),NULL,'경기도 성남구 223-98','호빵빌라 202호', 44000, '2023-05-05 22:00:23', 'REQUESTED', 2);