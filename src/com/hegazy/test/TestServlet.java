package com.hegazy.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	@Resource(name = "jdbc/employee")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			myConn= dataSource.getConnection();
			if (myConn != null) {
				System.out.println("Connected DataBase.....");
			}
			
			String sql = "SELECT * FROM webstudent.student";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
				System.out.println(email);
			}
			myConn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
		
		
	}

}
