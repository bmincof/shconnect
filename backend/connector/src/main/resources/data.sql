INSERT INTO ACCOUNT (ACCOUNT_NO, ACCOUNT_HOLDER, ACCOUNT_NUMBER, BANK_CODE, REMAIN_MONEY, TYPE, DATE)
VALUES (100000, '김친구', '456789123456', '088', 1000000, '입출금', '1693904107'),
(100001, '김거래', '891385789133', '088', 50000000, '입출금', '1673772907'),
(100002, '김신한', '110222999999', '088', 35550000, '입출금', '1630572907'),
(100003, '김익명', '118561223129', '088', 50000, '입출금', '1640422507'),
(100004, '김신한', '110282102999', '088', 1500000, '예적금', '1631572907'),
(100005, '김신한', '184988102999', '088', 20000000, '예적금', '1630582907');

INSERT INTO ACCOUNT_HISTORY (ACCOUNT_HISTORY_NO, ACCOUNT_NO, DEPOSITOR_NAME, BANK_CODE, ACCOUNT_NUMBER, MODIFIED_AMOUNT, REMAIN_AMOUNT, DATE, NOTE)
VALUES (100000, 100002, '익명의사람', '088', '118561223129', 50000, 36550000, 1640422507, null),
(100001, 100002, '박친구', '088', '852285228852', -1000000, 35550000, 1646643307, '빌린 돈 갚음');

INSERT INTO MEMBER (member_no, id, password, name, age, gender, contact, account_no)
VALUES (100000, 'test', '$2a$10$utsNAnkpTnxLLhxcjlnfEeUqB/4lK4rPAoqR5Xrljlb.BK74O4t42', '김신한', 20, 'F', '010-1234-1234', 100002);

INSERT INTO FRIEND (FRIEND_NO, ACCOUNT_NUMBER, BANK_CODE, BELONG, CONTACT, IMAGE, NAME, RELATION, MEMBER_NO)
VALUES (100000, '456789123456', '088', '신기한모임', '987654321', '이미지.jpg', '김친구', 'FRIEND', 100000),
(100001, '852285228852', '088', '또다른모임', '987654321', '이미지.jpg', '박친구', 'FRIEND', 100000),
(100002, '891385789133', '088', '이건모임', '987654321', '이미지.jpg', '김거래', 'BUSINESS', 100000);

INSERT INTO SCHEDULE (schedule_no, friend_no, name, category, date, repeat_cycle, content, alarm, favorite, member_no)
VALUES (100000, 100000, '새로운일정', '결혼식', 1646643307, 'NONE', null, 'NONE', 'false', 100000);

INSERT INTO MY_SCHEDULE (my_schedule_no, name, category, date, repeat_cycle, content, alarm, favorite, member_no)
VALUES
(100000, '새로운 내 일정', '집들이', 156000000, 'WEEK', '집들이 화이팅', 'MONTH', 'TRUE', 100000),
(100001, '새로운 두번째 일정', '집들이', 167000000, 'WEEK', '집들이 화이팅', 'MONTH', 'TRUE', 100000);

INSERT INTO TRIBUTE_SEND (tribute_send_no, amount, note, sent, schedule_no)
VALUES
(100000, 50000, '보낼 경조사비', 'false', 100000),
(100001, 60000, '보낼 경조사비1', 'true', 100000),
(100002, 70000, '보낼 경조사비2', 'true', 100000),
(100003, 80000, '보낼 경조사비3', 'false', 100000),
(100004, 90000, '보낼 경조사비4', 'false', 100000);

INSERT INTO TRIBUTE_RECEIVE (tribute_receive_no, amount, note, friend_no, my_schedule_no)
VALUES
(100000, 50000, '받은 경조사비', 100000, 100000),
(100001, 50000, '받은 경조사비', 100000, 100000),
(100002, 50000, '받은 경조사비', 100000, 100000),
(100003, 50000, '받은 경조사비', 100000, 100000),
(100004, 50000, '받은 경조사비', 100000, 100000),
(100005, 50000, '받은 경조사비', 100000, 100000);