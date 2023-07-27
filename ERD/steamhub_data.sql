DELETE FROM t_user;
ALTER TABLE t_user AUTO_INCREMENT = 1;
DELETE FROM t_board;
ALTER TABLE t_board AUTO_INCREMENT = 1;
DELETE FROM t_score_board;
ALTER TABLE t_score_board  AUTO_INCREMENT = 1;
DELETE FROM t_comment;
ALTER TABLE t_comment AUTO_INCREMENT = 1;
DELETE FROM t_authority ;
ALTER TABLE t_authority  AUTO_INCREMENT = 1;
DELETE FROM t_attachment;
ALTER TABLE t_attachment AUTO_INCREMENT = 1;

DELETE FROM t_recommend;
DELETE FROM t_user_authorities;
DELETE FROM t_wishlist;

-- 권한 설정
INSERT INTO t_authority (name) VALUES
('ROLE_MEMBER'), ('ROLE_ADMIN')
;

INSERT INTO t_user (username, password, email) VALUES
--  ('user1', '1234', 'user1@naver.com'),
--  ('user2', '1234', 'user2@naver.com'),
--  ('admin1', '1234', 'admin1@naver.com')
('USER1', '$2a$10$6gVaMy7.lbezp8bGRlV2fOArmA3WAk2EHxSKxncnzs28/m3DXPyA2', 'user1@naver.com'),
('USER2', '$2a$10$7LTnvLaczZbEL0gabgqgfezQPr.xOtTab2NAF/Yt4FrvTSi0Y29Xa', 'user2@naver.com'),
('ADMIN1', '$2a$10$53OEi/JukSMPr3z5RQBFH.z0TCYSUDPtxf1/8caRyRVdDNdHA9QHi', 'admin1@naver.com')
;

INSERT INTO t_user_authorities VALUES
(1, 1),
(3, 1),
(3, 2)
;

INSERT INTO t_board (user_id, title, content, game_id, is_file) VALUES
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false),
(1, 'title1', 'content1', '2114690', false);

INSERT INTO t_board (user_id, title, content, game_id, is_file) VALUES
(1, 'title1', 'content1', '1569040', false),
(1, 'title2', 'content2', '1569040', false),
(2, 'title3', 'content3', '1569040', false),
(1, 'title1', 'explain1', '1569040', true),
(2, 'title2', 'explain2', '1569040', true)
;

INSERT INTO t_score_board (user_id, game_id, score, content) VALUES
(1, '1569040', 5, 'good'),
(2, '1569040', 4, 'not bad'),
(3, '1569040', 3, 'normal')
;

INSERT INTO t_attachment (board_id, sourcename, filename) VALUES
(4, 'face01.png', 'face01.png'),
(4, 'face02.png', 'face02.png'),
(5, 'face03.png', 'face03.png'),
(5, 'face02.png', 'face02.png')
;

INSERT INTO t_comment (user_id, board_id, content) VALUES
(1, 1, '댓글입니다'),
(1, 2, '댓글입니다2'),
(2, 2, '댓글 달기'),
(3, 1, '샘플데이터')
;

INSERT INTO t_recommend (user_id, board_id) VALUES
(1, 1),
(2, 1),
(1, 4),
(2, 4)
;

INSERT INTO t_wishlist (user_id, game_id) VALUES
(1, '1569040'),
(1, '1407350'),
(1, '1407300')
;







