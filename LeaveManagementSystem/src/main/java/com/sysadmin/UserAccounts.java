package com.sysadmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.employeemanagement.Leave;
import com.exception.LeaveException;

import DBConnection.DatabaseConnector;

/**
 * The UserAccounts class implements an application that
 * Illustrate to create , read , update , delete the employee account
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class UserAccounts {
	static Scanner scanner= new Scanner(System.in);
	static DatabaseConnector dbc = new DatabaseConnector();
	
	/**
	 * add new account (or) record for the employee
	 * 
	 * @throws ClassNotFoundException
	 */
	
	public void addRecords() throws ClassNotFoundException {
		try  {
			
			Connection conn = dbc.getDBConnection();
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("$                   ADD NEW EMPLOYEE RECORDS                   $");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			
            System.out.println("Enter the Employee details");

            System.out.print("Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("First Name: ");
            String fname = scanner.nextLine();

            System.out.print("Last Name: ");
            String lname = scanner.nextLine();

            System.out.print("Department ID: ");
            int deptId = scanner.nextInt();

            System.out.print("Designation ID: ");
            int designationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if(!isValidEmail(email)) {
            	throw new LeaveException("Please enter valid email address!!!");
            }

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Contact Number: ");
            Long contactNumber = scanner.nextLong();
            if(!isValidContactNumber(contactNumber)) {
            	throw new LeaveException("Please enter valid contact address!!!");
            }
            scanner.nextLine();
            
            System.out.println("Address: ");
            String address = scanner.nextLine();

            // Prepare SQL statement
            String sql = "INSERT INTO PROJECTDB.employee (emp_id, fname, lname, dept_id, designation_id, email, username, password, contact_number, address) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.setString(2, fname);
            statement.setString(3, lname);
            statement.setInt(4, deptId);
            statement.setInt(5, designationId);
            statement.setString(6, email);
            statement.setString(7, username);
            statement.setString(8, password);
            statement.setLong(9, contactNumber);
            statement.setString(10, address);
            
            String tsql = "INSERT INTO PROJECTDB.leave_balance(emp_id) VALUES(?)";
            PreparedStatement statement1 = conn.prepareStatement(tsql);
            statement1.setInt(1, empId);
            
            // Execute the statement
            int rowsInserted = statement.executeUpdate();
            int rowsInserted1 = statement1.executeUpdate();
            if (rowsInserted > 0 && rowsInserted1 > 0) {
                System.out.println("A new record has been inserted successfully.");
            }
            

        } catch (SQLException e) {
            System.out.println(e);
        } catch (LeaveException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * display all the employee details
	 * 
	 * @throws ClassNotFoundException
	 */
	public void readRecords() throws ClassNotFoundException {
		try (Connection conn = dbc.getDBConnection()) {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("$                   READ ALL EMPLOYEE RECORDS                  $");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM PROJECTDB.employee";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("EMPLOYEE DETAILS:");
            System.out.println("--------------------------------------------------------".repeat(2));
            System.out.printf("%-10s %-15s %-15s %-10s %-15s %-20s %-15s %-15s %-15s %-15s\n",
                    "Emp ID", "First Name", "Last Name", "Dept ID", "Designation ID",
                    "Email", "Username", "Password", "Contact Number", "Address");
            System.out.println("--------------------------------------------------------".repeat(2));

            while (resultSet.next()) {
                int empId = resultSet.getInt("emp_id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                int deptId = resultSet.getInt("dept_id");
                int designationId = resultSet.getInt("designation_id");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Long contactNumber = resultSet.getLong("contact_number");
                String address = resultSet.getString("address");

                System.out.printf("%-10s %-15s %-15s %-10s %-15s %-20s %-15s %-15s %-15s %-15s\n",
                        empId, fname, lname, deptId, designationId,
                        email, username, password, contactNumber, address);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
		
	}
	
	/**
	 * update the employee records such as first name, last name, address, department Id
	 * 
	 * @param userName
	 */
	public void updateRecords(String userName) {
		
		 try {
	            
	            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("$                    UPDATE EMPLOYEE RECORDS                   $");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	            
	      

	            System.out.println("Enter employee ID:");
	            int empId = scanner.nextInt();
	            scanner.nextLine(); // Consume newline left-over

	            System.out.println("Select the field to update:");
	            System.out.println("1. First Name");
	            System.out.println("2. Last Name");
	            System.out.println("3. Department ID");
	            System.out.println("4. Designation ID");
	            System.out.println("5. Email");
	            System.out.println("6. Contact Number");
	            System.out.println("7. Address");
	            System.out.println("8.Exit");
	            
	            boolean flag = true;
	            do {
	            System.out.print("Enter Your Choice : ");
	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline left-over

	            
	            switch (choice) {
	                case 1:
	                	updateFirstName(empId);
	                    break;
	                case 2:
	                	updateLastName(empId);
	                    break;
	                case 3:
	                	updateDepartmentId(empId);
	                    break;
	                case 4:
	                	updateDesignationId(empId);
	                    break;
	                case 5:
	                    updateEmail(empId);
	                    break;
	                case 6:
	                    updateContactNumber(empId);
	                    break;
	                case 7:
	                	updateAddress(empId);
	                	break;
	                case 8:
	                	flag=false;
	                	System.out.println("<---THANK YOU FOR USING THIS APPLICATION--->");
	                	break;
	                default:
	                    System.out.println("Invalid choice !! Please enter a valid one");
	                    System.out.println();
	            }
	            }while(flag);
	            
	        } catch (InputMismatchException e) {
	            System.out.println(e);
	            
	        }
		
	}
	
	/**
	 * update (or) change the email address of the employee
	 * 
	 * @param empId
	 */
	public void updateEmail(int empId) {
	    try {
	   
	        System.out.println("Enter new email:");
	        String newEmail = scanner.nextLine();

	        // Validate email format
	        if (!isValidEmail(newEmail)) {
	        	throw new LeaveException("Please enter valid email address!!!");
	        }

	        Connection conn = dbc.getDBConnection();

	        // Query to update the email field
	        String query = "UPDATE PROJECTDB.employee SET email = ? WHERE emp_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, newEmail);
	        pstmt.setInt(2, empId);

	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println(rowsAffected + " row(s) updated successfully.");

	        pstmt.close();
	        conn.close();
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } catch (LeaveException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	// Method to validate email format using regular expression
	public boolean isValidEmail(String email) {
	    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
	// Method to validate contact number
	public boolean isValidContactNumber(long number) {
        if (number <= 0) {
            return false;
        }
        int digitCount = String.valueOf(number).length();
        final int VALID_CONTACT_NUMBER_LENGTH = 10;

        return digitCount == VALID_CONTACT_NUMBER_LENGTH;
    }
	
	/**
	 * update the contact number of the employee
	 * 
	 * @param empId
	 */
	public void updateContactNumber(int empId) {
	    try {
	       
	        Connection conn = dbc.getDBConnection();
	        
	        System.out.println("Enter new contact number:");
	        long newContactNumber = scanner.nextLong();

	        // Query to update the contact number field
	        if(isValidContactNumber(newContactNumber)) {
	        String query = "UPDATE PROJECTDB.employee SET contact_number = ? WHERE emp_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setLong(1, newContactNumber);
	        pstmt.setInt(2, empId);

	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println(rowsAffected + " row(s) updated successfully.");
	        pstmt.close();
	        conn.close();
	        }else {
	        	throw new LeaveException("Please enter valid contact address!!!");
	        }

	        
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println(e);
	    } catch (LeaveException e) {
			System.out.println(e);
	    }
	}
	
	/**
	 * update the first name of the employee
	 * 
	 * @param empId
	 */
	public void updateFirstName(int empId) {
        try {
            
            System.out.println("Enter new first name:");
            String newFirstName = scanner.nextLine();

            Connection conn = dbc.getDBConnection();

            // Query to update the first name field
            String query = "UPDATE PROJECTDB.employee SET fname = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newFirstName);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
	}
	
	/**
	 * update the LastName of the employee
	 * 
	 * @param empId
	 */
	public void updateLastName(int empId) {
        try {
            
            System.out.println("Enter new last name:");
            String newLastName = scanner.nextLine();

            Connection conn = dbc.getDBConnection();

            // Query to update the last name field
            String query = "UPDATE PROJECTDB.employee SET lname = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newLastName);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * update the department details
	 * 
	 * @param empId
	 */
	public void updateDepartmentId(int empId) {
        try {

            System.out.println("Enter new department ID:");
            int newDepartmentId = scanner.nextInt();

            Connection conn = dbc.getDBConnection();

            // Query to update the department ID field
            String query = "UPDATE PROJECTDB.employee SET dept_id = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, newDepartmentId);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * update the designation Id
	 * 
	 * @param empId
	 */
	public void updateDesignationId(int empId) {
        try {
            
            System.out.println("Enter new designation ID:");
            int newDesignationId = scanner.nextInt();

            Connection conn = dbc.getDBConnection();

            // Query to update the designation ID field
            String query = "UPDATE PROJECTDB.employee SET designation_id = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, newDesignationId);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
             System.out.println(e);
        }
    }
	
	/**
	 * update the user name of the employee
	 * 
	 * @param empId
	 */
	public void updateUsername(int empId) {
        try {
            
            System.out.println("Enter new username:");
            String newUsername = scanner.nextLine();

            Connection conn = dbc.getDBConnection();

            // Query to update the username field
            String query = "UPDATE PROJECTDB.employee SET username = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newUsername);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * update the password of the employee
	 * 
	 * @param userName
	 */
	public static void updatePassword(String userName) {
        try {
            
        	int empId = Leave.fetchEmployeeId(userName);
        	
            System.out.println("Enter new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Confirm new password:");
            String newPassword1 = scanner.nextLine();
            
            if(newPassword.equals(newPassword1)) {

            Connection conn = dbc.getDBConnection();

            // Query to update the password field
            String query = "UPDATE PROJECTDB.employee SET password = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
            }else {
            	throw new LeaveException("Confirm password is not matching with new password");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } catch (LeaveException e) {
			System.out.println(e.getMessage());
		}
    }
	
	/**
	 * update the address of the employee
	 * 
	 * @param empId
	 */
	public void updateAddress(int empId) {
        try {

            System.out.println("Enter new address:");
            String newAddress = scanner.nextLine();

            Connection conn = dbc.getDBConnection();

            // Query to update the address field
            String query = "UPDATE PROJECTDB.employee SET address = ? WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newAddress);
            pstmt.setInt(2, empId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * delete the records of the employee if there is no need
	 * 
	 * @throws ClassNotFoundException
	 */
	
	public void deleteRecords() throws ClassNotFoundException {
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$                    DELETE EMPLOYEE RECORDS                   $");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println();
		System.out.println("Delete an employee record");
        System.out.print("Enter the Employee ID: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); 

        try (Connection conn = dbc.getDBConnection()) {
        	//String sql2 = "DELETE FROM PROJECTDB.notification WHERE emp_id = ?";
        	String sql1 = "DELETE FROM PROJECTDB.leave_balance WHERE emp_id = ?";
        	//String sql2 = "DELETE FROM PROJECTDB.notification WHERE emp_id = ?";
            String sql = "DELETE FROM PROJECTDB.employee WHERE emp_id = ?";
            
            //PreparedStatement statement2 = conn.prepareStatement(sql2);
            PreparedStatement statement = conn.prepareStatement(sql1);
            PreparedStatement statement1 = conn.prepareStatement(sql);
            
          //  statement2.setInt(1, empId);
            statement.setInt(1, empId);
            statement1.setInt(1, empId);

           // int rowsDeleted2 = statement2.executeUpdate();
            int rowsDeleted = statement.executeUpdate();
            int rowsDeleted1 = statement1.executeUpdate();
            if (rowsDeleted > 0 && rowsDeleted1 > 0) {
                System.out.println("Employee record with ID " + empId + " deleted successfully.");
            } else {
                System.out.println("No employee record found with ID: " + empId);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } 
	}
}
