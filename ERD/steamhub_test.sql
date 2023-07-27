SHOW tables;

SELECT * FROM t_user;	
SELECT * FROM t_authority;
SELECT * FROM t_user_authorities;
SELECT * FROM t_board;
SELECT * FROM t_score_board;
SELECT * FROM t_comment;
SELECT * FROM t_attachment;
SELECT * FROM t_recommend;
SELECT * FROM t_wishlist;

INSERT INTO t_board (user_id, title, content, game_id, is_file) VALUES
(1, 'title1', 'content1', '1569040', true),
(1, 'title2', 'content2', '1569040', true),
(2, 'title3', 'content3', '1569040', true),
(1, 'title1', 'explain1', '1569040', true),
(2, 'title2', 'explain2', '1569040', true)
;