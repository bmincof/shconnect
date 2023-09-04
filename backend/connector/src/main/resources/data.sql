INSERT INTO MEMBER (member_no, id, password, name, age, gender, contact, bank_code, account_number)
VALUES (100000, 'test', '$2a$10$utsNAnkpTnxLLhxcjlnfEeUqB/4lK4rPAoqR5Xrljlb.BK74O4t42', '김신한', 20, 'F', '010-1234-1234', '088', '110222999999');

INSERT INTO Friend (FRIEND_NO, ACCOUNT_NUMBER, BANK_CODE, BELONG, CONTACT, IMAGE, NAME, RELATION, MEMBER_NO)
VALUES (100000, '456789123456', '088', '신기한모임', '987654321', '이미지.jpg', '김친구', 'FRIEND', 100000),
(100001, '456789123456', '088', '또다른모임', '987654321', '이미지.jpg', '박친구', 'FRIEND', 100000),
(100002, '456789123456', '088', '이건모임', '987654321', '이미지.jpg', '김거래', 'BUSINESS', 100000);