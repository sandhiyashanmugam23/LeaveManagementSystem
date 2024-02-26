package com.hrmanagement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

import com.management.LeaveRequest;
import DBConnection.DatabaseConnector;

/**
 * The LeaveCalender class implements an application that
 * Illustrate to view the leave calendar managed by hrAdmin and viewed by managed
 * 
 * @author Sandhiya Shanmugam(Expleo)
 * @since 20 FEB 2024
 */

public class LeaveCalendar {
	
	static DatabaseConnector dbc = new DatabaseConnector();
    private static Statement statement;
    private static ResultSet resultSet;
	
    /**
     * method that manages the employee leave calendar 
     *  
     * @throws ClassNotFoundException
     */
	public void manageLeaveCalender() throws ClassNotFoundException {
		try (Connection connection = dbc.getDBConnection()){
			statement = connection.createStatement();
	        
	        resultSet = statement.executeQuery("SELECT * FROM DATABASE.leave_calendar");
            System.out.println();
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	        System.out.println("$                       LEAVE CALENDER                         $");
	        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	        System.out.println();
	        System.out.println("Leave Category\tDate\tOccasion");
	        while (resultSet.next()) {
	            String leaveCategory = resultSet.getString("Leave_Category");
	            Date date = resultSet.getDate("Leave_Date");
	            String occasion = resultSet.getString("Occasion");
	            System.out.println(leaveCategory + "\t" + date + "\t" + occasion);
	        }

	        
	        statement.close();
	        resultSet.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	
	/**
	 * manages the leave request history of all the employees
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public  void viewAllLeaveRequest() throws SQLException, ClassNotFoundException {
		Queue<LeaveRequest> leaveRequestsQueue = new LinkedList<>();
		
		Connection connection = dbc.getDBConnection();
		
        String query = "SELECT leave_id, emp_id, leave_type, start_date, end_date, reason, status FROM PROJECTDB.leave_request";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

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
        
        displayLeaveRequest(leaveRequestsQueue);
		
	}
	
	/**
	 * display that leave request of the employees
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
	        System.out.println("$                    LEAVE REQUEST HISTORY                     $");
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
}
