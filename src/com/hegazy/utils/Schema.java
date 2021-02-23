package com.hegazy.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Schema {

	
	
	public static void createNewSchema() throws SQLException, IOException {
		
		// Registering the Driver
		DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
		// Getting the connection
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
		System.out.println("Connection established......");
		// Initialize the script runner
		ScriptRunner sr = new ScriptRunner(connection);
	
		// Creating a reader object
		Reader UserReader = new BufferedReader(new FileReader(
				"E:\\PersonalProjects\\Employee-Management-tool-\\src\\SQL-Scripts\\01-create-user.sql"));
		
		System.out.println("New User Created ......");
		Reader scriptReader = new BufferedReader(new FileReader(
				"E:\\PersonalProjects\\Employee-Management-tool-\\src\\SQL-Scripts\\02-employee-tracker.sql"));
		System.out.println("Data Added to the DB ......");
		// Running the script
		sr.runScript(UserReader);
		System.out.println("Create TRIGER .....");
		Statement myStmt = null;
		String sql = "CREATE OR REPLACE TRIGGER M_ElHagez.employee_on_insert\r\n" + 
				"  BEFORE INSERT ON M_ElHagez.employee\r\n" + 
				"  FOR EACH ROW\r\n" + 
				"BEGIN\r\n" + 
				"  SELECT M_ElHagez.employee_seq.nextval\r\n" + 
				"  INTO :new.id\r\n" + 
				"  FROM dual;\r\n" + 
				"END;";
		myStmt = connection.createStatement();
		int myRs = myStmt.executeUpdate(sql);
		System.out.println("Triger Created....."+ myRs);
		sr.runScript(scriptReader);
		UserReader.close();
		scriptReader.close();
		connection.close();
	}

	public static void dropTheSchema() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
		Statement myStmt = null;
		System.out.println("connection created.....");
		String sql = "drop user M_ElHagez";
		myStmt = connection.createStatement();
		int myRs = myStmt.executeUpdate(sql);
		System.out.println("Database deleted....."+ myRs);
		connection.close();
	}

	
}
