CREATE TABLE image_path (
	image_path_id INT PRIMARY KEY AUTO_INCREMENT,
	iamge_path VARCHAR(100) NOT NULL
);

CREATE TABLE user (
	user_id VARCHAR(20) PRIMARY KEY,
	image_path_id INT,
	user_pwd VARCHAR(20) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	comment VARCHAR(100),
	withdrawal BOOLEAN NOT NULL,
	FOREIGN KEY(image_path_id) REFERENCES image_path(image_path_id) ON UPDATE CASCADE
);

CREATE TABLE user_session (
	session_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(20) NOT NULL,
	last_timestamp DATE NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE system_alert (
	alert_id INT PRIMARY KEY AUTO_INCREMENT,
	timestamp DATE NOT NULL,
	message VARCHAR(255)
);

CREATE TABLE APNs_token (
	APNs_token VARCHAR(100) PRIMARY KEY,
	user_id VARCHAR(20) NOT NULL,
	timestamp DATE NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE user_log (
	log_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(20) NOT NULL,
	log_status_code INT NOT NULL,
	timestamp DATE NOT NULL,
	ip VARCHAR(100) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE tags (
	tag_id INT PRIMARY KEY AUTO_INCREMENT,
	tag VARCHAR(100) NOT NULL
);

CREATE TABLE space (
	space_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(20) NOT NULL,
	title VARCHAR(100) NOT NULL,
	latitude DOUBLE NOT NULL,
	longitude DOUBLE NOT NULL,
	description VARCHAR(1000),
	status INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE favorite (
	favorite_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id VARCHAR(20) NOT NULL,
	space_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE,
	FOREIGN KEY (space_id) REFERENCES space(space_id) ON UPDATE CASCADE
);

CREATE TABLE chat_rooms (
	chat_room_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id_1 VARCHAR(20) NOT NULL,
	user_id_2 VARCHAR(20) NOT NULL,
	user_1_status BOOLEAN NOT NULL,
	user_2_status BOOLEAN NOT NULL,
	FOREIGN KEY (user_id_1) REFERENCES user(user_id) ON UPDATE CASCADE,
	FOREIGN KEY (user_id_2) REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE space_image (
	space_image_id INT PRIMARY KEY AUTO_INCREMENT,
	space_id INT NOT NULL,
	image_path_id INT NOT NULL,
	FOREIGN KEY (space_id) REFERENCES space(space_id) ON UPDATE CASCADE,
	FOREIGN KEY (image_path_id) REFERENCES image_path(image_path_id) ON UPDATE CASCADE
);

CREATE TABLE space_log (
	log_id INT PRIMARY KEY AUTO_INCREMENT,
	space_id INT NOT NULL,
	log_status_code INT NOT NULL,
	timestamp DATE NOT NULL,
	FOREIGN KEY (space_id) REFERENCES space(space_id) ON UPDATE CASCADE
);

CREATE TABLE space_tag (
	space_tag_id INT PRIMARY KEY AUTO_INCREMENT,
	space_id INT NOT NULL,
	tag_id INT NOT NULL,
	FOREIGN KEY (space_id) REFERENCES space(space_id) ON UPDATE CASCADE,
	FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON UPDATE CASCADE
);