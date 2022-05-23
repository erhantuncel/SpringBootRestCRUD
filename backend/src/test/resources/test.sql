DROP TABLE IF EXISTS staffs;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS staffs_id_seq;
DROP SEQUENCE IF EXISTS departments_id_seq;
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
	FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

CREATE TABLE users (
	id bigserial,
	user_name VARCHAR(40) NOT NUll,
	password VARCHAR(200) NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO users (user_name, password) VALUES ('admin', '$2y$12$JJ26Ir75BOI5pZhlWa6wZ.IL/.IxHxUd9n1DPZ7OqbhpNE6PX6ZB6');

INSERT INTO departments (department_name) VALUES ('Üretim');
INSERT INTO departments (department_name) VALUES ('AR-GE');
INSERT INTO departments (department_name) VALUES ('Pazarlama');

INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Simge', 'CİĞERLİOĞLU', '6279548733', 1, '2019-06-28 16:18:15');
INSERT INTO staffs (first_name, last_name, phone, email, department_id, create_date) VALUES ('Arzu', 'BULGUR', '1283663610', 'arzu@abc.com', 2, '2019-06-20 18:35:52');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Emre', 'BİNBAY', '7543118133', 2, '2019-06-15 12:31:03');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Emre', 'ONBAY', '7543145133', 2, '2019-06-01 16:13:04');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Emre', 'YILMAZ', '3512145133', 2, '2019-05-01 10:37:34');
INSERT INTO staffs (first_name, last_name, phone, department_id, create_date) VALUES ('Merve', 'CİĞERLİOĞLU', '6279128787', 1, '2019-07-18 11:58:35');

