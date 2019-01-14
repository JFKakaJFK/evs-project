-- TEST USERS

INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Admin', 'Istrator', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'admin', 'admin', '2016-01-01 00:00:00', 'csav1010', 'admin@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('admin', 'ADMIN')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('admin', 'EMPLOYEE')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('admin', 'STUDENT')
INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Susi', 'Kaufgern', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'user1', 'admin', '2016-01-01 00:00:00', 'csav1011', 'employee@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user1', 'EMPLOYEE')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user1', 'STUDENT')
INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Max', 'Mustermann', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'user2', 'admin', '2016-01-01 00:00:00', 'csav1012', 'student@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user2', 'STUDENT')

-- TEST EQUIPMENTS

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 111600000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 111600000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 111600000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 111600000, 'Gerät 1')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:23:40.726', 'Kasten 1', 'Labor West', FALSE, 259200000, 'SuperTestgerät 4200')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:23:40.726', 'Kasten 1', 'Labor West', FALSE, 259200000, 'SuperTestgerät 4200')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:23:40.726', 'Kasten 1', 'Labor West', FALSE, 259200000, 'SuperTestgerät 4200')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 3')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 3')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 3')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'this is a demo equipment', 'first comment', 1)

-- TEST EQUIPMENT GROUPS

INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 1', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 2', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 1', 'admin')

INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 3)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 8)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (3, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (3, 12)

-- TEST OPENING_HOURS

INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Montag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Dienstag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Mittwoch', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Donnerstag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Freitag', '08:00:00', '12:00:00', '2016-01-01 00:00:00', 'admin')

-- TEST RESERVATIONS

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-12 10:00:00', 7, 'user2', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-12 10:00:00', 8, 'user2', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-12 10:00:00', 9, 'user2', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-12 10:00:00', 10, 'user2', FALSE)

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-14 10:00:00', 11, 'user1', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-14 10:00:00', 12, 'user1', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-14 10:00:00', 13, 'user1', FALSE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-14 10:00:00', 14, 'user1', FALSE)

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED) VALUES ('2016-01-01 00:00:00', '2019-01-14 10:00:00', '2019-01-14 16:00:00', 14, 'admin', FALSE)

