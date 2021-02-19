begin
execute immediate 'drop table M_ElHagez.employee';
exception when others then null;
end;

CREATE TABLE M_ElHagez.employee ( 
		  id number(10) NOT NULL ,
		  code varchar2(50) NOT NULL unique ,
		  name varchar2(50) NOT NULL,
		  address varchar2(50) DEFAULT  NULL,
		  email varchar2(50) NOT NULL unique
		);
ALTER TABLE M_ElHagez.employee ADD (
  CONSTRAINT employee_pk PRIMARY KEY (ID));

CREATE SEQUENCE employee_seq START WITH 1;

COMMIT WORK;  
INSERT ALL 
    INTO M_ElHagez.employee (id, code,name,address,email) VALUES (1,'1','Nagy Mohamed','114 Nasr City','mary@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (2,'2','John Nagy','USA','john@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (3,'3','Ajay Sero','KSA','ajay@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (4,'2000','Bill Yusheda','Ain shams','bill@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (5,'2265','Ramy Sayed','33 KSA','max@luv2code.com')
    SELECT 1 FROM DUAL;  
COMMIT WORK; 
