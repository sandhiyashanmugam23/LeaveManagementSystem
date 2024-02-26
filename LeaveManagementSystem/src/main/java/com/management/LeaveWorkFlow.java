package com.management;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.employeemanagement.LeaveBalance;
import com.notificationdetail.Notification;

import DBConnection.DatabaseConnector;

/**
 * The LeaveWorkFlow class implements an application that
 * Illustrate to make decision on the leave request given by the employee
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class LeaveWorkFlow {
	
	static DatabaseConnector dbc = new DatabaseConnector();
    
    /**
     * take decision on leave request based on some criteria such as leave balance
     * 
     * @param userName
     * @throws ClassNotFoundException
     */
	public void makeDecision(String userName) throws ClassNotFoundException {
		Queue<LeaveRequest> leaveRequestsQueue = new LinkedList<>();

        try {
            // Connect to the database
            Connection connection = dbc.getDBConnection();

            String query = "SELECT leave_id, emp_id, leave_type, start_date, end_date, reason, status FROM PROJECTDB.leave_request WHERE status = 'Requested'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
           
            System.out.println("Leave Request Details:");
            while (resultSet.next()) {
                int leaveId = resultSet.getInt("leave_id");
                System.out.println("Leave ID: " + leaveId);
                int employeeId = resultSet.getInt("emp_id");
                System.out.println("Employee ID: " +employeeId );
                
                String leaveType = resultSet.getString("leave_type");
                System.out.println("Leave Type: " + leaveType);
                Date startDate = resultSet.getDate("start_date");
                System.out.println("Start Date: " + startDate);
                Date endDate = resultSet.getDate("end_date");
                System.out.println("End Date: " + endDate);
                String reason = resultSet.getString("reason");
                System.out.println("Reason: " + reason);
                String status = resultSet.getString("status");
                System.out.println("Status: " + status);
                System.out.println("-----------------------------------------------");
                viewEmployeeDetailsByUsername(employeeId);
                System.out.println("-----------------------------------------------");
                viewLeaveBalance(employeeId);
                System.out.println("-----------------------------------------------");

                LeaveRequest leaveRequest = new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, reason, status);
                leaveRequestsQueue.offer(leaveRequest);
            }
            
            
            processLeaveRequests(leaveRequestsQueue);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * method can categorize the leave request to take decision for the leave request
	 * 
	 * @param leaveRequestsQueue
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void processLeaveRequests(Queue<LeaveRequest> leaveRequestsQueue) throws SQLException, ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
        	
        while (!leaveRequestsQueue.isEmpty()) {
            LeaveRequest leaveRequest = leaveRequestsQueue.poll();
            String leaveType = leaveRequest.getLeaveType();

            if (leaveType.equalsIgnoreCase("maternity") || leaveType.equalsIgnoreCase("paternity")) {
            	
                System.out.print("ENTER YOUR DECISION (Approved/Rejected) for Leave ID "+leaveRequest.getLeaveId()+" : " );
                String str = sc.next();
                if(str.equalsIgnoreCase("Approved")) {
                	approveLeaveRequest(leaveRequest, "Approved");
                }else {
                	rejectLeaveRequest(leaveRequest, "Rejected");
                }
            } else if (leaveType.equalsIgnoreCase("casual")  ) {
            	Connection connection = dbc.getDBConnection();
            	System.out.print("ENTER YOUR DECISION (Approved/Rejected) for Leave ID "+leaveRequest.getLeaveId()+"  : " );
                String str = sc.next();
            	if(str.equalsIgnoreCase("Approved")) {
            		approveLeaveRequest(leaveRequest, "Approved");
            		String updateBalanceQuery = "UPDATE PROJECTDB.leave_balance SET casual_leave_balance = casual_leave_balance - ? WHERE emp_id = ?";
            		PreparedStatement balanceStatement = connection.prepareStatement(updateBalanceQuery);
            		int days = betweenDays(leaveRequest);
            		System.out.println("Number Of Days Taken : "+days);
            		balanceStatement.setInt(1, days); 
            	    balanceStatement.setInt(2, leaveRequest.getEmployeeId());
            	    int balanceRowsAffected = balanceStatement.executeUpdate();
            	    if (balanceRowsAffected > 0) {
            	        System.out.println("Leave balance with ID " + leaveRequest.getLeaveId() + " has been updated successfully.");
            	    } else {
            	        System.out.println("Failed to update leave balance.");
            	    }
            	}else {
            		rejectLeaveRequest(leaveRequest, "Rejected");
            	}
                
            }else if(leaveType.equalsIgnoreCase("sick")) {
            	System.out.print("ENTER YOUR DECISION (Approved/Rejected) for Leave ID "+leaveRequest.getLeaveId()+" : ");
                String str = sc.next();
            	if(str.equalsIgnoreCase("Approved")) {
            		approveLeaveRequest(leaveRequest, "Approved");
            		String updateBalanceQuery = "UPDATE PROJECTDB.leave_balance SET sick_leave_balance = sick_leave_balance - ? WHERE emp_id = ?";
            		Connection connection = dbc.getDBConnection();
            		PreparedStatement balanceStatement = connection.prepareStatement(updateBalanceQuery);
            		int days = betweenDays(leaveRequest);
            		System.out.println("Number Of Days Taken : "+days);
            		balanceStatement.setInt(1, days); // Assuming you have a method to get the number of days for the leave
            	    balanceStatement.setInt(2, leaveRequest.getEmployeeId());
            	    int balanceRowsAffected = balanceStatement.executeUpdate();
            	    if (balanceRowsAffected > 0) {
            	        System.out.println("Leave balance with ID " + leaveRequest.getLeaveId() + " has been updated successfully.");
            	    } else {
            	        System.out.println("Failed to update leave balance.");
            	    }
            	}else {
            		rejectLeaveRequest(leaveRequest, "Rejected");
            	}
            }
            
            System.out.println("If you want to continue , Please give 1 as input : ");
            int n = sc.nextInt();
            if(n!=1) {
            	break;
            }
          
       }
        
    }
	
	public static LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
	
	public static  int betweenDays(LeaveRequest leaveRequest) {
		LocalDate startDate = convertToLocalDate(leaveRequest.getStartDate());
		LocalDate endDate = convertToLocalDate(leaveRequest.getEndDate());

		// Calculate the number of days between start date and end date, inclusive of both dates
		int numberOfDays = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);
		
		return numberOfDays;

	}

	
	/**
	 * leave request is approved this method can update the leave balance and 
	 * send notification to the employee
	 * 
	 * @param leaveRequest
	 * @param status
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void approveLeaveRequest(LeaveRequest leaveRequest, String status) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE PROJECTDB.leave_request SET status = ? WHERE leave_id = ?";
        Connection connection = dbc.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
     
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, leaveRequest.getLeaveId());
       
        int rowsAffected = preparedStatement.executeUpdate();
        

        if (rowsAffected > 0) {
            System.out.println("Leave request with ID " + leaveRequest.getLeaveId() + " has been approved successfully.");
            Notification notify = new Notification("Leave Request is approved",leaveRequest.getLeaveId(),leaveRequest.getEmployeeId(),leaveRequest.getStartDate(), leaveRequest.getEndDate());
            notify.sendNotification();
        } else {
            System.out.println("Failed to find employee ID");
        }
        preparedStatement.close();
    }
	
	/**
	 * leave request is rejected then this method can send the notification to the employee
	 * 
	 * @param leaveRequest
	 * @param status
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void rejectLeaveRequest(LeaveRequest leaveRequest, String status) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE PROJECTDB.leave_request SET status = ? WHERE leave_id = ?";
        Connection connection = dbc.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, leaveRequest.getLeaveId());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Leave request with ID " + leaveRequest.getLeaveId() + " has been rejected successfully.");
            Notification notify = new Notification("Leave Request is rejected",leaveRequest.getLeaveId(),leaveRequest.getEmployeeId(),leaveRequest.getStartDate(),leaveRequest.getEndDate());
            notify.sendNotification();
        } else {
            System.out.println("Failed to find employee ID");
        }
        preparedStatement.close();
    }
	
	/**
	 * view the pending leave request
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void viewAllLeaveRequest() throws SQLException, ClassNotFoundException {
	    Queue<LeaveRequest> leaveRequestsQueue = new LinkedList<>();

	    // Fetch leave requests with status 'Requested' and add them to the queue
	    String query = "SELECT leave_id, emp_id, leave_type, start_date, end_date, reason, status FROM PROJECTDB.leave_request WHERE status = 'Requested'";
	    Connection connection = dbc.getDBConnection();
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        while (resultSet.next()) {
	            int leaveId = resultSet.getInt("leave_id");
	            int employeeId = resultSet.getInt("emp_id");
	            String leaveType = resultSet.getString("leave_type");
	            Date startDate = resultSet.getDate("start_date");
	            Date endDate = resultSet.getDate("end_date");
	            String reason = resultSet.getString("reason");
	            String status = resultSet.getString("status");

	            LeaveRequest leaveRequest = new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, reason, status);
	            leaveRequestsQueue.offer(leaveRequest);
	        }
	    }

	    displayLeaveRequest(leaveRequestsQueue);
	}


	/**
	 * method can display the leave request
	 * 
	 * @param leaveRequestsQueue
	 */
	private static void displayLeaveRequest(Queue<LeaveRequest> leaveRequestsQueue) {
		 if (leaveRequestsQueue.isEmpty()) {
	            System.out.println("No leave requests to display.");
	            return;
	     }
		 System.out.println();
		 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
         System.out.println("$                  VIEW ALL LEAVE REQUESTS                     $");
         System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
         System.out.println();
            // Assuming fixed column widths for simplicity
	        String format = "| %-10s | %-12s | %-12s | %-12s | %-12s | %-20s |  %-10s | %n";

	        // Printing table headers
	        System.out.printf(format, "Leave ID", "Employee ID", "Leave Type", "Start Date", "End Date", "Reason", "Status");

	        // Printing leave requests
	        System.out.println();
	        for (LeaveRequest leaveRequest : leaveRequestsQueue) {
	            System.out.printf(format,
	                    leaveRequest.getLeaveId(),
	                    leaveRequest.getEmployeeId(),
	                    leaveRequest.getLeaveType(),
	                    leaveRequest.getStartDate(),
	                    leaveRequest.getEndDate(),
	                    leaveRequest.getReason(),
	                    leaveRequest.getStatus()
	            );
	        }
		
	}
	
	/**
	 * employee details can be viewed in this method
	 * 
	 * @param emp_id
	 */
	public void viewEmployeeDetailsByUsername(int emp_id) {
        try {
            Connection conn = dbc.getDBConnection();

            // Query to select employee details by username
            String query = "SELECT * FROM PROJECTDB.employee WHERE emp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, emp_id);

            ResultSet rs = pstmt.executeQuery();
             
            System.out.println("THE EMPLOYEE DETAILS : ");
            // Display employee details
            while (rs.next()) {
                System.out.println("Employee ID: " + rs.getInt("emp_id"));
                System.out.println("First Name: " + rs.getString("fname"));
                System.out.println("Last Name: " + rs.getString("lname"));
                System.out.println("Department ID: " + rs.getInt("dept_id"));
                System.out.println("Designation ID: " + rs.getInt("designation_id"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Contact Number: " + rs.getString("contact_number"));
                System.out.println("Address: " + rs.getString("address"));
                // Display other fields as needed
            }
            System.out.println("-----------------------------------------------");
            
            
            // Close resources
            rs.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * leave balance of the employee can be viewed using this application
	 * 
	 * @param empId
	 * @throws ClassNotFoundException
	 */
	public static void viewLeaveBalance(int empId) throws ClassNotFoundException {
		System.out.println("EMPLOYEE LEAVE BALANCE DETAILS : ");
		System.out.println();
		System.out.println("EMPLOYEE ID : "+empId);
		System.out.println();
		int casual_Leave_Balance = LeaveBalance.casualLeaveBalance(empId);
		//System.out.println();
		System.out.println("Casual Leave Balance: " + casual_Leave_Balance);
		System.out.println();
		int sick_Leave_Balance = LeaveBalance.sickLeaveBalance(empId);
		System.out.println("Sick Leave Balance: " + sick_Leave_Balance);
		System.out.println();
	}

}
