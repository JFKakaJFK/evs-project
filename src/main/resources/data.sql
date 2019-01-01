-- TEST USERS

INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Admin', 'Istrator', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'admin', 'admin', '2016-01-01 00:00:00', 'csav1010', 'admin@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('admin', 'ADMIN')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('admin', 'EMPLOYEE')
INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Susi', 'Kaufgern', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'user1', 'admin', '2016-01-01 00:00:00', 'csav1011', 'employee@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user1', 'STUDENT')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user1', 'EMPLOYEE')
INSERT INTO USER (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, C_NUMBER, EMAIL) VALUES(TRUE, 'Max', 'Mustermann', '$2a$10$UAX93rcb0ux.UfkSUL8wOOKqIxzzV/oWRriGUT/tiwMjyFnRB4y3S', 'user2', 'admin', '2016-01-01 00:00:00', 'csav1012', 'student@mail.com')
INSERT INTO USER_USER_ROLE (USER_USERNAME, ROLES) VALUES ('user2', 'EMPLOYEE')

-- TEST EQUIPMENtS

INSERT INTO EQUIPMENT(CREATE_DATE, LAB_LOCATION, LAB_NAME, LOCKED, MAX_DURATION_MILLISECONDS, NAME, CREATE_USER_USERNAME) VALUES ('2019-01-01 00:00:00', 'Kasten 42', 'Labor Ost', FALSE, 111600000, 'Gerät 1', 'admin')
INSERT INTO EQUIPMENT_COMMENT (CREATE_DATE, MESSAGE, TITLE, CREATE_USER_USERNAME) VALUES ('2019-01-01 00:00:00', 'this is a demo equipment', 'first comment', 'admin')
INSERT INTO EQUIPMENT_COMMENTS (EQUIPMENT_ID, COMMENTS_ID) VALUES (1, 1)
