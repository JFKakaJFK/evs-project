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

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 86400000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 86400000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 86400000, 'Gerät 1')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 86400000, 'Gerät 1')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:14.158', 'Kasten 3', 'Labor West', FALSE, 864000000, 'Gerät 2')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor West', FALSE, 172800000, 'Gerät 3')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor West', FALSE, 172800000, 'Gerät 3')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor West', FALSE, 172800000, 'Gerät 3')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor West', FALSE, 172800000, 'Gerät 3')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 4')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 4')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 4')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 2', 'Labor Ost', FALSE, 432000000, 'Gerät 4')

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 20', 'Labor Ost', FALSE, 864000000, 'Gerät 5')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 20', 'Labor Ost', FALSE, 864000000, 'Gerät 5')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 20', 'Labor Ost', FALSE, 864000000, 'Gerät 5')
INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME) VALUES ('2019-01-08 10:22:42.399', 'Kasten 20', 'Labor Ost', FALSE, 864000000, 'Gerät 5')


INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 1)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 1)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 1)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 4', 1)

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 2)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 2)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 2)

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 3)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 3)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 3)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 4', 3)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 5', 3)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 6', 3)

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 4)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 4)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 4)

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 5)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 5)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 5)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 4', 5)

INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 1', 6)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 2', 6)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 3', 6)
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, EQUIPMENT_ID) VALUES ('2019-01-01 00:00:00', 'Dieses Gerät ist nur ein Testgerät', 'Bemerkung 4', 6)

-- TEST EQUIPMENT GROUPS

INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 1', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 2', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 3', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 4', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 5', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 6', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 7', 'user1')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('User 1 Group 8', 'user1')

INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 1', 'admin')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 2', 'admin')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 3', 'admin')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 4', 'admin')
INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 5', 'admin')
-- INSERT INTO EQUIPMENT_GROUP (NAME, USER_USERNAME) VALUES ('Admin Group 6', 'admin')

INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 3)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (1, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (2, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (3, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (3, 3)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (3, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (4, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (4, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (4, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (5, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (5, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (5, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (5, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (6, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (6, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (6, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (6, 3)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (7, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (7, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (7, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (8, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (8, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (8, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (8, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (8, 12)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (9, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (9, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (9, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (10, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (10, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (10, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (11, 12)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (11, 3)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (11, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (11, 4)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (12, 7)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (12, 10)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (12, 2)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (13, 6)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (13, 9)
INSERT INTO EQUIPMENT_EQUIPMENTGROUP (EQUIPMENT_GROUP_ID, EQUIPMENT_ID) VALUES (13, 12)

-- TEST MANUALS

INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-1.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 1)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-2.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 1)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-3.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 1)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-4.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 2)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-5.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 2)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-6.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 3)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-7.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 3)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-8.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 4)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-9.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 4)
INSERT INTO EQUIPMENT_MANUAL (CREATE_DATE, DESC, FILENAME, NAME, ORIGINAL_FILE_NAME, TYPE, EQUIPMENT_ID) VALUES ('2019-01-16 14:16:51.002', 'Diese Bedienungsanleitung ist nure eine Testdatei', 'TestManual 1-10.txt', 'Test Bedienungsanleitung', 'TestManual.txt', 'text/plain', 5)

-- TEST RESERVATIONS // TODO change dates before submitting

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-15 10:00:00', 7, 'user2', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-15 10:00:00', 8, 'user2', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-15 10:00:00', 9, 'user2', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-16 10:00:00', 10, 'user2', FALSE, TRUE, TRUE)

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-15 10:00:00', 11, 'user1', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-17 10:00:00', 12, 'user1', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-17 10:00:00', 13, 'user1', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-10 10:00:00', '2019-01-16 10:00:00', 14, 'user1', FALSE, TRUE, TRUE)

INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-14 10:00:00', '2019-01-16 16:00:00', 1, 'admin', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-14 10:00:00', '2019-01-16 16:00:00', 2, 'admin', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-14 10:00:00', '2019-01-16 16:00:00', 3, 'admin', FALSE, TRUE, TRUE)
INSERT INTO EQUIPMENT_RESERVATION (CREATE_DATE, START_DATE, END_DATE, EQUIPMENT_ID, USER_USERNAME, COMPLETED, REMINDER_MAIL_SENT, OVERDUE_MAIL_SENT) VALUES ('2016-01-01 00:00:00', '2019-01-14 10:00:00', '2019-01-16 16:00:00', 4, 'admin', FALSE, TRUE, TRUE)


-- TEST OPENING_HOURS

INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Montag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Dienstag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Mittwoch', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, START_PAUSE, END_PAUSE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Donnerstag', '08:00:00', '17:00:00', '12:00:00', '14:00:00', '2016-01-01 00:00:00', 'admin')
INSERT INTO OPENING_HOURS (DAY, START_TIME , END_TIME, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Freitag', '08:00:00', '12:00:00', '2016-01-01 00:00:00', 'admin')

-- TEST HOLIDAYS

INSERT INTO HOLIDAYS (NAME, START_DATE, END_DATE, CREATE_DATE, CREATE_USER_USERNAME) VALUES ('Test', '2016-01-01 00:00:00', '2016-01-01 00:00:00', '2016-01-01 00:00:00', 'admin')
