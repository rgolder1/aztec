SELECT * FROM ITEM

DROP TABLE ITEM;

CREATE TABLE ITEM (
	ID BIGINT PRIMARY KEY,
	KEY VARCHAR2(10) NOT NULL,
	VALUE VARCHAR2(40) NOT NULL
	)

-- PROD
INSERT into ITEM values (1, 'Basic', 'PROD: Basic Success');
INSERT into ITEM values (2, 'Annotated', 'PROD: Annotated Success');
INSERT into ITEM values (3, 'JPA', 'PROD: JPA Success');
INSERT into ITEM values (4, 'JDBC', 'PROD: JDBC Success');


--DEV
INSERT into ITEM values (1, 'Basic', 'DEV: Basic Success');
INSERT into ITEM values (2, 'Annotated', 'DEV: Annotated Success');
INSERT into ITEM values (3, 'JPA', 'DEV: JPA Success');
INSERT into ITEM values (4, 'JDBC', 'DEV: JDBC Success');