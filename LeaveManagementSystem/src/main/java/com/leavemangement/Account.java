package com.leavemangement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DatabaseConnector;
import java.sql.*;

/**
 * The Account class can implements an application that
 * Illustrate to validate the userName and password of the users
 * 
 *  @author Sandhiya Shanmugam (Expleo)
 *  @since 19 FEB 2024
 */


public class Account {
	static DatabaseConnector dbc = new DatabaseConnector();
    private String userName;
    private String password;
    
    /**
     * special method for the initialization of variables
     * 
     * @param userName
     * @param password
     */
    public Account(String userName, String password) {
		this.userName=userName;
		this.password=password;
	}
    
    /**
     * get the userName of the user
     * 
     * @return userName
     */
	public String getUsername() {
		return userName;
	}

	/**
	 * set the userName of the user
	 * 
	 * @param userName
	 */
	public void setUsername(String userName) {
		this.userName = userName;
	}

	/**
	 * get the password of the user
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set the password of the user
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * validate the userName and password of the user
	 * 
	 * @param role
	 * @return
	 * @throws ClassNotFoundException
	 */
	public boolean login(String role) throws ClassNotFoundException{
    	
        try (Connection conn = dbc.getDBConnection()){
        	 
        	 if(role.equalsIgnoreCase("admin")) {
        		 String query = "SELECT * FROM PROJECTDB.admin WHERE username = ? AND password = ?";
                 PreparedStatement pstmt = conn.prepareStatement(query);
                 pstmt.setString(1, userName);
                 pstmt.setString(2, password);
                 ResultSet rs = pstmt.executeQuery();

                 if (rs.next()) {
                     System.out.println("Login successful!");
                     return true;
                 } else {
                     System.out.println("Invalid username or password!");
                     return false;
                 }
        	 }
        	 else if(role.equalsIgnoreCase("Manager")||role.equalsIgnoreCase("HR")) {
        		 String query = "SELECT designation_id FROM PROJECTDB.employee WHERE username = ? AND password = ?";
                 PreparedStatement pstmt = conn.prepareStatement(query);
                 pstmt.setString(1, userName);
                 pstmt.setString(2, password);
                 ResultSet rs2 = pstmt.executeQuery();

                 if (rs2.next()) {
                     int designationId = rs2.getInt("designation_id");
                     String designationQuery = "SELECT designation_name FROM PROJECTDB.designation WHERE designation_id = ?";
                     PreparedStatement designationStmt = conn.prepareStatement(designationQuery);
                     designationStmt.setInt(1, designationId);
                     ResultSet designationRs = designationStmt.executeQuery();
                     
                     if (designationRs.next()) {
                         String designationName = designationRs.getString("designation_name");
                         if(designationName.equalsIgnoreCase(role)) {
                        	 System.out.println("Login successful! Your designation is: " + designationName);
                        	 return true;
                         }
                         else {
                        	 System.out.println("Invalid designation!");
                        	 return false;
                         }
                         
                     } else {
                    	 System.out.println("Invalid username or password!");
                    	 return false;
                     }
        	 }}
        	 else {
        		 String query1 = "SELECT * FROM PROJECTDB.employee WHERE username = ? AND password = ?";
                 PreparedStatement pstmt1 = conn.prepareStatement(query1);
                 pstmt1.setString(1, userName);
                 pstmt1.setString(2, password);
                 ResultSet rs = pstmt1.executeQuery();

                 if (rs.next()) {
                	 
                	 System.out.println("Login successful!");
                	 return true;
                	 
                 } else {
                	 
                	 System.out.println("Invalid username or password!");
                	 return false;
                	 
                 }
        	 }
             
         } catch (SQLException e) {
            System.out.println(e);
         }
        return false;
    }
}

