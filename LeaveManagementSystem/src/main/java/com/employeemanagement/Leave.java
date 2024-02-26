package com.employeemanagement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.exception.LeaveException;
import com.leavemangement.Account;

import DBConnection.DatabaseConnector;

/**
 * The Leave class implements an application that
 * Illustrate to execute the functions like applying for leave,canceling the 
 * leave request, modifying the leave request.
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 19 FEB 2024
 */

public class Leave {
	
    static DatabaseConnector dbc = new DatabaseConnector();
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * display the leave category and execute the functionalities for applying a leave
	 * 
	 * @param username
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws LeaveException 
	 */
	
	public static void applyLeave(String username,String password) throws ClassNotFoundException, SQLException {
		
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$                       REQUEST FOR LEAVE                      $");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		int empId = fetchEmployeeId(username);
		
		try {
		do {
			System.out.println();
			
			System.out.println("LEAVE CATEGORY");
			System.out.println();
			System.out.println("1.CASUAL LEAVE");
			System.out.println("2.SICK LEAVE");
			System.out.println("3.MATERNITY LEAVE");
			System.out.println("4.PATERNITY LEAVE");
			System.out.println("5.MOVE TO PREVIOUS");
			System.out.println("6.EXIT");
			System.out.println();
			System.out.print("Enter Your Choice : ");
			int choice = sc.nextInt();
		
			
			switch(choice) {
			case 1:
				casualLeaveRequest(empId);
				break;
			case 2:
				sickLeaveRequest(empId);
				break;
			case 3:
				maternityLeaveRequest(empId);
				break;
			case 4:
				paternityLeaveRequest(empId);
				break;
			case 5:
				 Account acc = new Account(username,password);
				 Employee emp = new Employee(acc);
				 emp.employeeFunctionsDisplay();
				 break;
			case 6:
				System.out.println("<---THANK YOU FOR USING THIS APPLICATION--->");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice! Please enter valid choice");
			}
		}while(true);
		}catch(InputMismatchException e) {
			System.out.println("Please Enter Integer Values !!!");
        	//applyLeave(userName,password);
        } catch (LeaveException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	/**
	 * employee made a request for paternityLeave
	 * 
	 * @param empId
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 */
	private static void paternityLeaveRequest(int empId) throws ClassNotFoundException {
	    System.out.println("PATERNITY LEAVE REQUEST");
	    System.out.println();

	    System.out.println("EMPLOYEE ID :" + empId);
	    System.out.print("Start Date (YYYY-MM-DD): ");
	    String startDateString = sc.nextLine();
	    Date startDate = Date.valueOf(startDateString); // Convert String to java.sql.Date

	    try {
	        if (!validateStartDate(startDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }

	        System.out.print("End Date (YYYY-MM-DD): ");
	        String endDateString = sc.nextLine();
	        Date endDate = Date.valueOf(endDateString); // Convert String to java.sql.Date

	        if (!validateEndDate(startDate, endDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }

	        System.out.print("Reason: ");
	        String reason = sc.nextLine();

	        try (Connection conn = dbc.getDBConnection()) {
	            String sql = "INSERT INTO PROJECTDB.leave_request (emp_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);

	            statement.setInt(1, empId);
	            statement.setString(2, "PATERNITY");
	            statement.setDate(3, startDate);
	            statement.setDate(4, endDate);
	            statement.setString(5, reason);

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Leave request submitted successfully!");
	            } else {
	                System.out.println("Failed to submit leave request.");
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	    } catch (LeaveException e) {
	        System.out.println(e.getMessage()); // Print the custom exception message
	    }
	}


	/**
	 * employee made a request for maternityLeave
	 * 
	 * @param empId
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 */
	private static void maternityLeaveRequest(int empId) throws ClassNotFoundException {
	    System.out.println("MATERNITY LEAVE REQUEST");
	        
	    System.out.println("EMPLOYEE ID :"+empId);
	    System.out.print("Start Date (YYYY-MM-DD): ");
	    sc.nextLine();
	    String startDateString = sc.next();
	    Date startDate = Date.valueOf(startDateString); // Convert String to java.sql.Date
	    
	    try {
	        if(!validateStartDate(startDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }

	        System.out.print("Reason: ");
	        // Consume the newline character left in the buffer
	        sc.nextLine();
	        String reason = sc.nextLine();

	        try (Connection conn = dbc.getDBConnection()) {
	            String sql = "INSERT INTO PROJECTDB.leave_request (emp_id, leave_type, start_date, reason) VALUES (?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            
	            statement.setInt(1, empId);
	            statement.setString(2, "MATERNITY");
	            statement.setDate(3, startDate);
	            statement.setString(4, reason);

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Maternity Leave request submitted successfully!");
	            } else {
	                System.out.println("Failed to submit leave request.");
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	    } catch (LeaveException e) {
	        System.out.println(e.getMessage()); // Print the custom exception message
	    }
	}


	/**
	 * employee made a request for sick leave
	 * 
	 * @param empId
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 */
	private static void sickLeaveRequest(int empId) throws ClassNotFoundException {
	    System.out.println("SICK LEAVE REQUEST");
	    System.out.println();
	    
	    System.out.println("EMPLOYEE ID :"+empId);
	    int sick_Leave_Balance = LeaveBalance.sickLeaveBalance(empId);
	    System.out.println();
	    System.out.println("Sick Leave Balance: " + sick_Leave_Balance);
	    System.out.println();
	    System.out.println("NOW WE ENTER INTO LEAVE REQUEST APPLICATION");
	    System.out.println();
	    sc.nextLine();
	    System.out.print("Start Date (YYYY-MM-DD): ");
	    String startDateString = sc.next();
	    Date startDate = Date.valueOf(startDateString); 

	    try {
	        if(!validateStartDate(startDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }
	        
	        System.out.print("End Date (YYYY-MM-DD): ");
	        String endDateString = sc.next();
	        Date endDate = Date.valueOf(endDateString); // Convert String to java.sql.Date
	        
	        if(!validateEndDate(startDate,endDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }

	        System.out.print("Reason: ");
	        // Consume the newline character left in the buffer
	        sc.nextLine();
	        String reason = sc.nextLine();

	        try (Connection conn = dbc.getDBConnection()) {
	            String sql = "INSERT INTO PROJECTDB.leave_request (emp_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            
	            statement.setInt(1, empId);
	            statement.setString(2, "SICK");
	            statement.setDate(3, startDate);
	            statement.setDate(4, endDate);
	            statement.setString(5, reason);

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Sick Leave request submitted successfully!");
	            } else {
	                System.out.println("Failed to submit leave request.");
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	    } catch (LeaveException e) {
	        System.out.println(e.getMessage()); // Print the custom exception message
	    }
	}

	
	

	/**
	 * employee made a request for casual leave
	 * 
	 * @param empId
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 */
	private static void casualLeaveRequest(int empId) throws ClassNotFoundException {
	    System.out.println("CASUAL LEAVE REQUEST");
	    System.out.println();
	    
	    System.out.println("EMPLOYEE ID :"+empId);
	    int casual_Leave_Balance = LeaveBalance.casualLeaveBalance(empId);
	    System.out.println();
	    System.out.println("Casual Leave Balance: " + casual_Leave_Balance);
	    System.out.println();
	    System.out.println("NOW WE ENTER INTO LEAVE REQUEST APPLICATION");
	    System.out.println();
	    sc.nextLine();
	    System.out.print("Start Date (YYYY-MM-DD): ");
	    String startDateString = sc.nextLine();
	    Date startDate = Date.valueOf(startDateString); 
	    
	    try {
	        if(!validateStartDate(startDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }
	        
	        System.out.print("End Date (YYYY-MM-DD): ");
	        String endDateString = sc.nextLine();    
	        Date endDate = Date.valueOf(endDateString); // Convert String to java.sql.Date
	        
	        if(!validateEndDate(startDate,endDate)) {
	            throw new LeaveException("Invalid date please enter the right one !!!");
	        }
	        
	        System.out.print("Reason: ");
	        String reason = sc.nextLine();

	        try (Connection conn = dbc.getDBConnection()) {
	            String sql = "INSERT INTO PROJECTDB.leave_request (emp_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            
	            statement.setInt(1, empId);
	            statement.setString(2, "CASUAL");
	            statement.setDate(3, startDate);
	            statement.setDate(4, endDate);
	            statement.setString(5, reason);

	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Leave request submitted successfully!");
	            } else {
	                System.out.println("Failed to submit leave request.");
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	    } catch (LeaveException e) {
	        System.out.println(e.getMessage()); // Print the custom exception message
	    }
	}

    private static boolean validateStartDate(Date startDate) {
    	LocalDate start_Date = convertToLocalDate(startDate);
    	if(start_Date.isAfter(LocalDate.now())) {
    		return true;
    	}
		return false;
		
	}
	
	private static boolean validateEndDate(Date startDate,Date endDate) {
		LocalDate start_Date = convertToLocalDate(startDate);
		LocalDate end_Date = convertToLocalDate(endDate);
		if(end_Date.isAfter(start_Date)) {
			return true;
		}
		return false;
	}
	
	
	public static LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
	/**
	 * employee can view the overall leave balance
	 * 
	 * @param username
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 * @throws SQLException 
	 */
	public static void displayLeaveBalance(String username,String password) throws ClassNotFoundException, SQLException, LeaveException {
		LeaveBalance.displayLeaveBalance(username,password);
	}
	
	public static void cancelLeaveRequest(String username) throws ClassNotFoundException {
		
	    System.out.println();
	    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	    System.out.println("$                   CANCELLING LEAVE REQUEST                   $");
	    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	    System.out.println();
	    int empId = fetchEmployeeId(username);
	    System.out.println("EMPLOYEE ID: " + empId);
	    
	    try (Connection conn = dbc.getDBConnection()) {
	        String sql = "DELETE FROM PROJECTDB.leave_request WHERE emp_id = ?";
	        try (PreparedStatement deleteStatement = conn.prepareStatement(sql)) {
	            deleteStatement.setInt(1, empId);
	            int rowsAffected = deleteStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Leave request with ID " + empId + " canceled successfully.");
	            } else {
	                System.out.println("Leave request with ID " + empId + " not found.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println(e);
	    }
	}
	
//	public static void updateLeaveBalance(int empId,String leave_type) {
//		try (Connection conn = dbc.getDBConnection()) {
//	        String sql = "SELECT start_date, end_date FROM PROJECTDB.leave_request WHERE emp_id = ?";
//	        
//	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
//	            statement.setInt(1, empId);
//	            ResultSet rs = statement.executeQuery();
//	            
//	            int totalDays = 0;
//	            while (rs.next()) {
//	                java.sql.Date startDate = rs.getDate("start_date");
//	                java.sql.Date endDate = rs.getDate("end_date");
//	                
//	                long daysBetween = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
//	                totalDays += daysBetween;
//	            }
//	            
//	            // Update leave balance here with totalDays
//	            casualUpdateLeaveBalanceInDatabase(empId, totalDays,leave_type);
//	        }
//	    } catch (SQLException e) {
//	        System.out.println(e);
//	    } catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			System.out.println(e1);
//		}
//	}
//	
//	private static void casualUpdateLeaveBalanceInDatabase(int empId, int totalDays, String leave_type) {
//	    if(leave_type.equalsIgnoreCase("casual")) {
//	    	try (Connection conn = dbc.getDBConnection()) {
//		        String updateSql = "UPDATE PROJECTDB.leave_balance SET casual_leave_balance = casual_leave_balance - ? WHERE emp_id = ?";
//		        
//		        try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
//		            statement.setInt(1, totalDays);
//		            statement.setInt(2, empId);
//		            
//		            int rowsAffected = statement.executeUpdate();
//		            if (rowsAffected > 0) {
//		                System.out.println("Leave balance updated successfully for employee ID " + empId);
//		            } else {
//		                System.out.println("Failed to update leave balance for employee ID " + empId);
//		            }
//		        }
//		    } catch (SQLException e) {
//		        System.out.println(e);
//		    } catch (ClassNotFoundException e1) {
//				System.out.println(e1);
//			}
//	    }else if(leave_type.equalsIgnoreCase("sick")){
//	    	try (Connection conn = dbc.getDBConnection()) {
//		        String updateSql = "UPDATE PROJECTDB.leave_balance SET sick_leave_balance = sick_leave_balance - ? WHERE emp_id = ?";
//		        
//		        try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
//		            statement.setInt(1, totalDays);
//		            statement.setInt(2, empId);
//		            
//		            int rowsAffected = statement.executeUpdate();
//		            if (rowsAffected > 0) {
//		                System.out.println("Leave balance updated successfully for employee ID " + empId);
//		            } else {
//		                System.out.println("Failed to update leave balance for employee ID " + empId);
//		            }
//		        }
//		    } catch (SQLException e) {
//		        System.out.println(e);
//		    } catch (ClassNotFoundException e1) {
//				System.out.println(e1);
//			}
//	    }
//	}
	
	/**
	 * employee can able to modify the leave request
	 * 
	 * @param username
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 * @throws SQLException 
	 */
	public static void modifyLeaveRequest(String username,String password) throws ClassNotFoundException, SQLException, LeaveException {
		
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$                    MODIFYING LEAVE REQUEST                   $");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println();
		Leave.cancelLeaveRequest(username);
		Leave.applyLeave(username,password);
		
	}
	
	/**
	 * method can be use to fetch the employee ID
	 * 
	 * @param userName
	 * @return empId
	 * @throws ClassNotFoundException
	 */
	public static int fetchEmployeeId(String username) throws ClassNotFoundException {
        int empId = -1; 

        try {
            
            Connection connection = dbc.getDBConnection();

            String query = "SELECT emp_id FROM PROJECTDB.employee WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
   
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                empId = resultSet.getInt("emp_id");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return empId;
    }
}
