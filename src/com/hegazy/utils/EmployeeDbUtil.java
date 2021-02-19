package com.hegazy.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.hegazy.dao.Employee;

public class EmployeeDbUtil {

	private static EmployeeDbUtil instance ;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/employee";
	
	
	public static EmployeeDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new EmployeeDbUtil();
		}
		
		return instance;
	}
	
	private EmployeeDbUtil() throws Exception {		
		dataSource = getDataSource();
		
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
		
	public List<Employee> getEmployees() throws Exception {

		List<Employee> employees = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from M_ElHagez.employee order by name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String code = myRs.getString("code");
				String name = myRs.getString("name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");

				// create new employee object
		Employee tempEmployee = new Employee(id, code, name,address,email);

				// add it to the list of students
				employees.add(tempEmployee);
			}
			
			return employees;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addEmployee(Employee theEmployee) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into M_ElHagez.employee (code, name, address,email) values (?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theEmployee.getCode());
			myStmt.setString(2, theEmployee.getName());
			myStmt.setString(3, theEmployee.getAddress());
			myStmt.setString(4, theEmployee.getEmail());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Employee getEmployee(int employeeId) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from M_ElHagez.employee where id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, employeeId);
			
			myRs = myStmt.executeQuery();

			Employee theEmployee = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String code = myRs.getString("code");
				String name = myRs.getString("name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");

				theEmployee = new Employee(id, code, name,address,email);
			}
			else {
				throw new Exception("Could not find employee id: " + employeeId);
			}

			return theEmployee;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateEmployee(Employee theEmployee) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update M_ElHagez.employee "
						+ " set code=?, name=?, address=?, email=?"
						+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theEmployee.getCode());
			myStmt.setString(2, theEmployee.getName());
			myStmt.setString(3, theEmployee.getAddress());
			myStmt.setString(4, theEmployee.getEmail());
			myStmt.setInt(5, theEmployee.getId());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteEmployee(int employeeId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from M_ElHagez.employee where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, employeeId);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	
}
