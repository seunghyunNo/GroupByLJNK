SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_attachment;
DROP TABLE IF EXISTS t_user_authorities;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_comment;
DROP TABLE IF EXISTS t_recommend;
DROP TABLE IF EXISTS t_board;
DROP TABLE IF EXISTS t_score_board;
DROP TABLE IF EXISTS t_wishlist;
DROP TABLE IF EXISTS t_user;

/* Create Tables */

CREATE TABLE t_attachment
(
	id int NOT NULL AUTO_INCREMENT,
	sourcename varchar(100) NOT NULL,
	filename varchar(100) NOT NULL,
	board_id int NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE t_authority
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(30) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE t_board
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	regdate datetime DEFAULT now(),
	count int DEFAULT 0,
	title varchar(100) NOT NULL,
	content text NOT NULL,
	game_id varchar(100) NOT NULL,
	is_file boolean NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE t_comment
(
	id int NOT NULL AUTO_INCREMENT,
	content text NOT NULL,
	regdate datetime DEFAULT now(),
	user_id int NOT NULL,
	board_id int NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE t_recommend
(
	user_id int NOT NULL,
	board_id int NOT NULL,
	PRIMARY KEY (user_id, board_id)
);


CREATE TABLE t_score_board
(
	game_id varchar(100) NOT NULL,
	user_id int NOT NULL,
	score int NOT NULL CHECK(score>=1 AND score<=5),
	content varchar(100),
	PRIMARY KEY (game_id, user_id)
);


CREATE TABLE t_user
(
	id int NOT NULL AUTO_INCREMENT,
	username varchar(50) NOT NULL,
	password varchar(500) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (username),
	UNIQUE (email)
);


CREATE TABLE t_user_authorities
(
	user_id int NOT NULL,
	authority_id int NOT NULL,
	PRIMARY KEY (user_id, authority_id)
);


CREATE TABLE t_wishlist
(
	user_id int NOT NULL,
	game_id varchar(100) NOT NULL,
	PRIMARY KEY (user_id, game_id)
);



/* Create Foreign Keys */

ALTER TABLE t_user_authorities
	ADD FOREIGN KEY (authority_id)
	REFERENCES t_authority (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_attachment
	ADD FOREIGN KEY (board_id)
	REFERENCES t_board (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_comment
	ADD FOREIGN KEY (board_id)
	REFERENCES t_board (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_recommend
	ADD FOREIGN KEY (board_id)
	REFERENCES t_board (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_board
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_comment
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_recommend
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_score_board
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_user_authorities
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE t_wishlist
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

