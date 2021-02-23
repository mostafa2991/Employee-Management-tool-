/* **************Create User*************** */
CREATE USER M_ElHagez IDENTIFIED BY M_ElHagez;
ALTER USER M_ElHagez QUOTA unlimited ON SYSTEM;
GRANT CREATE SESSION, CONNECT, RESOURCE, DBA TO M_ElHagez;
GRANT ALL PRIVILEGES TO M_ElHagez;
COMMIT WORK; 
/* **************Create Table*************** */
CREATE TABLE M_ElHagez.employee ( 
		  id number(10) PRIMARY KEY ,
		  code varchar2(50) NOT NULL unique ,
		  name varchar2(50) NOT NULL,
		  address varchar2(50) DEFAULT  NULL,
		  email varchar2(50) NOT NULL unique
		);
COMMIT WORK;	
	/* **************Create seq*************** */		
CREATE SEQUENCE M_ElHagez.employee_seq START WITH 1;
COMMIT WORK;
