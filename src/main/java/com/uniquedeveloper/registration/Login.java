package com.uniquedeveloper.registration;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("username");
		String pass = request.getParameter("password");

        // encrypt password with bcrypt
		byte[] salt = {42, -10, 127, -50, 0, 64, -128, 33, -73, 18, 55, -91, 101, -29, -16, 83}; // random, same salt as in registration
        String hashedPassword = OpenBSDBCrypt.generate(pass.toCharArray(), salt, 12);
        
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN?useSSL=false", "root", "0989552898bB@");
			String query = "select * from users where email = ? and hashed_pass = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, hashedPassword);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				session.setAttribute("name", rs.getString("user_name"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			} else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		        pst.close();
		        con.close();
		        } catch (Exception e) {
		        	}
		        }
	}
}
