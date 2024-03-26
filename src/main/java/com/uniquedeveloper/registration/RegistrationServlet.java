package com.uniquedeveloper.registration;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_name = request.getParameter("name");
		String email = request.getParameter("email");
		
		// encrypt password 
		String pass = request.getParameter("pass");
		byte[] salt = {42, -10, 127, -50, 0, 64, -128, 33, -73, 18, 55, -91, 101, -29, -16, 83}; // random

                // Hash the password with bcrypt
                String hashedPassword = OpenBSDBCrypt.generate(pass.toCharArray(), salt, 12);
		
		String mobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN?useSSL=false", "root", "0989552898bB@");
			PreparedStatement pst = con.prepareStatement("insert into users(user_name, hashed_pass, email, mobile) values(?,?,?,?) ");
			pst.setString(1, user_name);
			pst.setString(2, hashedPassword);
			pst.setString(3, email);
			pst.setString(4, mobile);
			
			dispatcher = request.getRequestDispatcher("registration.jsp");
			try {
				int rowCount = pst.executeUpdate();
				if (rowCount > 0) {
					request.setAttribute("status", "success");
				} else {
					request.setAttribute("status", "failed");
				}
			} catch (Exception e) {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
