package com.hegazy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.hegazy.dao.Employee;

public class EmployeeDbUtil {

	private static EmployeeDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/employee";

	private Connection myConn = null;
	private Statement myStmt = null;
	private PreparedStatement myPreStmt = null;
	private ResultSet myRs = null;

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

		try {
			myConn = getConnection();

			String sql = "select * from M_ElHagez.employee order by code";

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
				Employee tempEmployee = new Employee(id, code, name, address, email);

				// add it to the list of students
				employees.add(tempEmployee);
			}

			return employees;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addEmployee(Employee theEmployee) throws Exception {
//		Boolean emailExisted = isEmailExisted(theEmployee.getEmail());
//		if (emailExisted) {
//			System.out.println("email exist");
//			throw new Exception("Email is already Existing : " + theEmployee.getEmail());
//		} else {
			try {
				myConn = getConnection();

				String sql = "insert into M_ElHagez.employee (code, name, address,email) values (?, ?, ?, ?)";

				myPreStmt = myConn.prepareStatement(sql);

				// set params
				myPreStmt.setString(1, theEmployee.getCode());
				myPreStmt.setString(2, theEmployee.getName());
				myPreStmt.setString(3, theEmployee.getAddress());
				myPreStmt.setString(4, theEmployee.getEmail());
				myPreStmt.execute();
			} finally {
				close(myConn, myStmt);
			}
		}

//	}

	public Employee getEmployee(int employeeId) throws Exception {

		try {
			myConn = getConnection();

			String sql = "select * from M_ElHagez.employee where id=?";

			myPreStmt = myConn.prepareStatement(sql);

			// set params
			myPreStmt.setInt(1, employeeId);

			myRs = myPreStmt.executeQuery();

			Employee theEmployee = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String code = myRs.getString("code");
				String name = myRs.getString("name");
				String address = myRs.getString("address");
				String email = myRs.getString("email");

				theEmployee = new Employee(id, code, name, address, email);
			} else {
				throw new Exception("Could not find employee id: " + employeeId);
			}

			return theEmployee;
		} finally {
			close(myConn, myPreStmt, myRs);
		}
	}

	public void updateEmployee(Employee theEmployee) throws Exception {

		try {
			myConn = getConnection();

			String sql = "update M_ElHagez.employee " + " set code=?, name=?, address=?, email=?" + " where id=?";

			myPreStmt = myConn.prepareStatement(sql);

			// set params
			myPreStmt.setString(1, theEmployee.getCode());
			myPreStmt.setString(2, theEmployee.getName());
			myPreStmt.setString(3, theEmployee.getAddress());
			myPreStmt.setString(4, theEmployee.getEmail());
			myPreStmt.setInt(5, theEmployee.getId());

			myPreStmt.execute();
		} finally {
			close(myConn, myPreStmt);
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
		} finally {
			close(myConn, myStmt);
		}
	}

	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	public Boolean isEmailExisted(String email) {

		try {
			final String SQL_SELECT = "Select * from M_ElHagez.employee where email = ?;";
			myPreStmt = myConn.prepareStatement(SQL_SELECT);
			myPreStmt.setString(1, email);
			myRs = myPreStmt.executeQuery();
			while (myRs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
