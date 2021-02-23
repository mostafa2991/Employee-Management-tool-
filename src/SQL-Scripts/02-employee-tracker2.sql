CREATE TABLE M_ElHagez.employee ( 
		  id number(10) PRIMARY KEY ,
		  code varchar2(50) NOT NULL unique ,
		  name varchar2(50) NOT NULL,
		  address varchar2(50) DEFAULT  NULL,
		  email varchar2(50) NOT NULL unique
		);

COMMIT WORK;  
INSERT ALL 
    INTO M_ElHagez.employee (id, code,name,address,email) VALUES (1,'1','Nagy Mohamed','114 Nasr City','mary@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (2,'2','John Nagy','USA','john@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (3,'3','Ajay Sero','KSA','ajay@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (4,'2000','Bill Yusheda','Ain shams','bill@luv2code.com')
	INTO M_ElHagez.employee (id, code,name,address,email) VALUES (5,'2265','Ramy Sayed','33 KSA','max@luv2code.com')
    SELECT 1 FROM DUAL;  
COMMIT WORK; 


CREATE OR REPLACE TRIGGER M_ElHagez.employee_on_insert
  BEFORE INSERT ON M_ElHagez.employee
  FOR EACH ROW
BEGIN
  SELECT M_ElHagez.employee_seq.nextval
  INTO :new.id
  FROM dual
END;
  COMMIT WORK;
 



