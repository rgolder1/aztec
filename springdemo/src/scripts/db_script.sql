SELECT * FROM DEMO_RESULT

DROP TABLE DEMO_RESULT;

CREATE TABLE DEMO_RESULT (
	ID BIGINT PRIMARY KEY,
	TYPE VARCHAR2(10) NOT NULL,
	RESULT VARCHAR2(40) NOT NULL
	)

-- PROD
INSERT into DEMO_RESULT values (1, 'Basic', 'PROD: Basic Success');
INSERT into DEMO_RESULT values (2, 'Annotated', 'PROD: Annotated Success');
INSERT into DEMO_RESULT values (3, 'JPA', 'PROD: JPA Success');
INSERT into DEMO_RESULT values (4, 'JDBC', 'PROD: JDBC Success');


--DEV
INSERT into DEMO_RESULT values (1, 'Basic', 'DEV: Basic Success');
INSERT into DEMO_RESULT values (2, 'Annotated', 'DEV: Annotated Success');
INSERT into DEMO_RESULT values (3, 'JPA', 'DEV: JPA Success');
INSERT into DEMO_RESULT values (4, 'JDBC', 'DEV: JDBC Success');