package com.notificationdetail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DatabaseConnector;

/**
 * Notification class implements an application that
 * Illustrate to notify the employees about their leave request status
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 21 FEB 2024
 */

public class Notification {
	
	private String message;
	private int leaveID;
	private int emp_ID;
	private Date start_Date;
	private Date end_Date;
	static DatabaseConnector dbc = new DatabaseConnector();
	
	/**
	 * special method (or) constructor for the initialization of the variables
	 * 
	 * @param message
	 * @param leaveID
	 * @param emp_ID
	 */
	public Notification(String message,int leaveID, int emp_ID, Date start_Date, Date end_Date) {
		super();
		this.message = message;
		this.leaveID = leaveID;
		this.emp_ID = emp_ID;
		this.start_Date = start_Date;
		this.end_Date = end_Date;
	}
	
	/**
	 * get the message for the notification
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * set the message for the notification
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * get the leave Id for the notification
	 * 
	 * @return leaveID
	 */
	public int getLeaveID() {
		return leaveID;
	}
	
	/**
	 * set the leave id for the notification
	 * 
	 * @param leaveID
	 */
	public void setLeaveID(int leaveID) {
		this.leaveID = leaveID;
	}
	/**
	 * get the employee ID for the notification
	 * 
	 * @return emp_ID
	 */
	public int getEmp_ID() {
		return emp_ID;
	}
	
	/**
	 * set the employee Id for the notification
	 * 
	 * @param emp_ID
	 */
	public void setEmp_ID(int emp_ID) {
		this.emp_ID = emp_ID;
	}
	
	/**
	 * get the start date for the notification
	 * 
	 * @return start_Date
	 */
	public Date getStart_Date() {
		return start_Date;
	}

	/**
	 * set the start date for the notification
	 * 
	 * @param start_Date
	 */
	public void setStart_Date(Date start_Date) {
		this.start_Date = start_Date;
	}

	/**
	 * get the end date for the notification
	 * 
	 * @return end_Date
	 */
	public Date getEnd_Date() {
		return end_Date;
	}

	/**
	 * set the end date for the notification
	 * 
	 * @param end_Date
	 */
	public void setEnd_Date(Date end_Date) {
		this.end_Date = end_Date;
	}

	/**
	 * method can send the notification to the employees
	 * 
	 * @throws ClassNotFoundException
	 */
	public void sendNotification() throws ClassNotFoundException {
		String sql = "INSERT INTO PROJECTDB.notification (message, leave_id, emp_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

	    try (
	        // Establishing a connection to the database
	        Connection connection = dbc.getDBConnection() ;
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(sql)
	    ) {
	        
	        preparedStatement.setString(1, message);
	        preparedStatement.setInt(2, leaveID);
	        preparedStatement.setInt(3, emp_ID);
	        preparedStatement.setDate(4, start_Date);
            preparedStatement.setDate(5, end_Date);         
	        
	        preparedStatement.executeUpdate();
	        System.out.println("Notification sended successfully.");
	    } catch (SQLException e) {
	        System.out.println("Error storing notification: " + e.getMessage());
	    }
		
	}
	
	/**
	 * employees can view the notification by this method
	 * 
	 * @param empID
	 * @throws ClassNotFoundException
	 */
	public static void viewNotification(int empID) throws ClassNotFoundException {
	    String sql = "SELECT * FROM PROJECTDB.notification WHERE emp_id = ?";

	    try (Connection connection = dbc.getDBConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	        preparedStatement.setInt(1, empID);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            boolean found = false;

	            while (resultSet.next()) {
	                found = true;
	                int notificationId = resultSet.getInt("notification_id");
	                String message = resultSet.getString("message");
	                int leaveId = resultSet.getInt("leave_id");
	                int employeeId = resultSet.getInt("emp_id");
	                Date start_date = resultSet.getDate("start_Date");
	                Date end_date = resultSet.getDate("end_Date");

	                System.out.println("Notification ID: " + notificationId);
	                System.out.println("Message: " + message);
	                System.out.println("Leave ID: " + leaveId);
	                System.out.println("Employee ID: " + employeeId);
	                System.out.println("Start Date : " + start_date);
	                System.out.println("End Date : " + end_date);
	                System.out.println("---------------------------------------");
	            }

	            if (!found) {
	                System.out.println("No notifications found for employee ID: " + empID);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving notifications: " + e.getMessage());
	        //e.printStackTrace();
	    }
	}
	
	
	@Override
	public String toString() {
		return "Notification [message=" + message + ", leaveID=" + leaveID + ", emp_ID="
				+ emp_ID + "]";
	}
	
	
	
}
