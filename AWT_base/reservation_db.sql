CREATE TABLE user(
user_id VARCHAR(8) NOT NULL,
user_name VARCHAR(20),
class_id VARCHAR(5),
group_id VARCHAR(30),
password VARCHAR(20),
PRIMARY KEY (user_id)
);

CREATE TABLE facility(
facility_id   VARCHAR(4) NOT NULL,
facility_name VARCHAR(30) NOT NULL,
open_time     TIME,
close_time    TIME,
explanation   TEXT,
PRIMARY KEY (facility_id)
);

CREATE TABLE reservation(
reservation_id INT NOT NULL AUTO_INCREMENT,
facility_id    VARCHAR(4) NOT NULL,
user_id        VARCHAR(8) NOT NULL,
date           DATETIME,
day            DATE,
start_time     TIME,
end_time       TIME,
PRIMARY KEY (reservation_id),
FOREIGN KEY (user_id) REFERENCES user(user_id),
FOREIGN KEY (facility_id) REFERENCES facility(facility_id)
);
