package com.sysadmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import DBConnection.DatabaseConnector;

/**
 * The DepartmentManagement class implements an application that
 * Illustrate to add , read , update and delete departments 
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class DepartmentManagement {
	
    private static Connection conn;
    static Scanner sc = new Scanner(System.in);
    static DatabaseConnector dbc = new DatabaseConnector();
    
    /**
     * show the functionalities in department management
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public void manageDepartment() throws ClassNotFoundException, SQLException {
		
		
		
		try {
		do {
			System.out.println("DEPARTMENT MANAGEMENT");
			System.out.println("1. ADD DEPARTMENT");
			System.out.println("2. READ DEPARTMENT");
			System.out.println("3. UPDATE DEPARTMENT");
			System.out.println("4. REMOVE DEPARTMENT");
			System.out.println("5. EXIT");
			System.out.println();
			System.out.print("ENTER YOUR CHOICE : ");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				addDepartments();
				break;
			case 2:
				readDepartments();
				break;
			case 3:
				updateDepartments();
				break;
			case 4:
				deleteDepartments();
				break;
			case 5:
				System.out.println("<--- (: THANK YOU FOR USING THIS APLLICATION !!! :) --->");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice! Please enter valid choice");
				System.out.println();
			}
		}while(true);
		}catch(InputMismatchException e) {
			System.out.println("Please Enter Integer Values !!!");
		}
	}
	
	/**
	 * add the new department into the application
	 *  
	 * @throws ClassNotFoundException
	 */
	public static void addDepartments() throws ClassNotFoundException {
       
        try {
        	conn = dbc.getDBConnection();
        	System.out.print("Enter department id: ");
        	int deptId = sc.nextInt();
        	sc.nextLine();
            System.out.print("Enter department name: ");
            String deptName = sc.nextLine();

            String query = "INSERT INTO PROJECTDB.department (dept_id,dept_name) VALUES (?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            	pstmt.setInt(1, deptId);
                pstmt.setString(2, deptName);
                pstmt.executeUpdate();
            }

            System.out.println("Department '" + deptName + "' added successfully!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * display the available departments in the application
	 * 
	 * @throws ClassNotFoundException
	 */
	public static void readDepartments() throws ClassNotFoundException {
        try {
        	conn = dbc.getDBConnection();
            String query = "SELECT dept_id, dept_name FROM PROJECTDB.department";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Departments:");
                while (rs.next()) {
                    int deptId = rs.getInt("dept_id");
                    String deptName = rs.getString("dept_name");
                    System.out.println("Dept ID: " + deptId + ", Dept Name: " + deptName);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * update the available departments 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void updateDepartments() throws ClassNotFoundException, SQLException {
		conn = dbc.getDBConnection();
		System.out.print("Enter Department ID : ");
		int deptId = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Department Name : ");
		String newDeptName = sc.nextLine();
        try {
            String query = "UPDATE PROJECTDB.department SET dept_name = ? WHERE dept_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newDeptName);
                pstmt.setInt(2, deptId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Department updated successfully!");
                } else {
                    System.out.println("No department found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * delete the departments
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void deleteDepartments() throws ClassNotFoundException, SQLException {
		System.out.print("Enter Depatment ID : ");
		int deptId = sc.nextInt();
		conn = dbc.getDBConnection();
		
        try {
            String query = "DELETE FROM PROJECTDB.department WHERE dept_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deptId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Department with ID " + deptId + " deleted successfully!");
                } else {
                    System.out.println("No department found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
