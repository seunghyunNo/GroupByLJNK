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


INSERT INTO t_board (user_id, title, content, game_id, is_file)
VALUES
(1, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(3, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(5, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(6, 'test', 'test', '2114690', 1),
(2, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(3, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1),
(1, 'test', 'test', '2114690', 1)
;