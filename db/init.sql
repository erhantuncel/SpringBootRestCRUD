DROP TABLE IF EXISTS staffs;
DROP TABLE IF EXISTS staffs_id_seq;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS departments_id_seq;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_id_seq;
CREATE TABLE departments (
	id bigserial,
	department_name VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE staffs (
	id bigserial,
	first_name VARCHAR(40) NOT NULL,
	last_name VARCHAR(40) NOT NULL,
	phone VARCHAR(10) NOT NULL,
	email VARCHAR(40),
	department_id bigint NOT NULL,
	image bytea,
	create_date timestamp,
	PRIMARY KEY (id),
	FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE users (
	id bigserial,
	user_name VARCHAR(40) NOT NUll,
	password VARCHAR(200) NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO users (user_name, password) VALUES ('admin', '$2a$10$k8tbW3jeuVlhOtwd/rAXkeXcWFu6VUeWbeboZiJc4f.0htQQpI/sK');

INSERT INTO departments (department_name) VALUES ('Üretim');
INSERT INTO departments (department_name) VALUES ('AR-GE');
INSERT INTO departments (department_name) VALUES ('Pazarlama');

INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Berfin', 'BEKAROĞLU', '6669766700', pg_read_binary_file('/profileImages/f1.png'), 1, '2019-04-23 10:39:03');
INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Seher', 'ÇİFTCİ', '6130295962', pg_read_binary_file('/profileImages/f2.png'), 1, '2019-09-17 14:22:24');
INSERT INTO staffs (first_name, last_name, phone, image, email, department_id, create_date) VALUES ('Yasin', 'AYSAN', '4988255071', pg_read_binary_file('/profileImages/m1.png'), 'yasin@abc.com', 1, '2019-08-20 11:09:43');
INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Bengisu', 'BOYA', '7757844101', pg_read_binary_file('/profileImages/f3.png'), 1, '2019-05-31 12:27:23');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Zeynep', 'AYDINLIOĞLU', '6521391339', 1, '2019-03-11 18:15:02');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Semiha', 'AKTUNA', '7402920223', 1, '2019-09-13 17:19:07');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Seyit', 'CERAN', '4142819760', 1, '2019-06-29 12:24:23');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Nedim', 'AKARCALI', '6020293528', 1, '2019-11-14 13:46:25');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Ali', 'ARDIÇ', '9453977993', 'ali@abc.com', 1, '2019-07-13 11:58:22');
INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Ogün', 'BÖLGE', '3028260222', pg_read_binary_file('/profileImages/m2.png'), 2, '2019-10-09 13:03:19');
INSERT INTO staffs (first_name, last_name, phone, image, email, department_id, create_date) VALUES ('Mahire', 'ÇALIM', '9626494776', pg_read_binary_file('/profileImages/f4.png'), 'mahire@abc.com', 2, '2019-10-05 15:09:43');
INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Elvan', 'ÇATAL', '6461085688', pg_read_binary_file('/profileImages/f5.png'), 2, '2019-11-09 16:17:18');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Hulki', 'BENT', '2204180868', 'hulki@abc.com', 2, '2019-09-16 14:18:09');
INSERT INTO staffs (first_name, last_name, phone, image, email, department_id, create_date) VALUES ('Hami', 'AYDOĞDU', '2249319175', pg_read_binary_file('/profileImages/m3.png'), 'hami@abc.com', 3, '2019-05-18 10:23:26');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Simge', 'CİĞERLİOĞLU', '8813626480', 'simge@abc.com', 3, '2019-05-21 10:51:07');
INSERT INTO staffs (first_name, last_name, phone, image, department_id, create_date) VALUES ('Ahmet', 'BURAK', '2489323655', pg_read_binary_file('/profileImages/m4.png'), 3, '2019-04-05 11:10:51');
INSERT INTO staffs (first_name, last_name, phone, image, email, department_id, create_date) VALUES ('Özgür', 'BAŞTUĞ', '1997872206', pg_read_binary_file('/profileImages/m5.png'), 'ozgur@abc.com', 3, '2019-10-08 12:28:25');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Doğuşcan', 'BİRİZ', '2520026405', 'doguscan@abc.com', 3, '2019-03-26 11:34:26');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Nazım', 'BATURALP', '1402783346', 'nazim@abc.com', 3, '2019-05-04 11:31:18');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Ayla', 'BAYTIN', '3212727411', 3, '2019-10-31 12:53:49');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Ahmet', 'AKOĞUZ', '5017532698', 3, '2019-08-25 13:27:48');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Özgün', 'BAYTIYAR', '5958316252', 3, '2019-03-08 13:32:29');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Öznür', 'ÇULHAOĞLU', '2429235061', 3, '2019-04-02 17:10:11');