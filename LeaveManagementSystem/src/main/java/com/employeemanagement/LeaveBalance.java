package com.employeemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.exception.LeaveException;
import com.leavemangement.Account;

import DBConnection.DatabaseConnector;

/**
 * LeaveBalance class implements an application that
 * Illustrate to find the leave balance of the employee
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class LeaveBalance {
	
	static DatabaseConnector dbc = new DatabaseConnector();
    static Scanner sc = new Scanner(System.in);
	
	public static void displayLeaveBalance(String username,String password) throws ClassNotFoundException, SQLException, LeaveException {
		
	    
		String userName = username;
        int id = Leave.fetchEmployeeId(username);
		
        
        try {
		   do{
			 System.out.println();
			 System.out.println("VIEW LEAVE BALANCE");
			 System.out.println();
			 System.out.println("1.OVERALL LEAVE BALANCE");
			 System.out.println("2.SICK LEAVE BALANCE");
			 System.out.println("3.CASUAL LEAVE BALANCE");
			 System.out.println("4.MOVE TO PREVIOUS");
			 System.out.println("5.EXIT");
			 System.out.println();
			 System.out.print("ENTER YOUR CHOICE : ");
			 int choice = sc.nextInt();
		
			switch(choice) {
			case 1:
				overallLeaveBalance(id);
				break;
			case 2:
				int sick_Leave_Balance = sickLeaveBalance(id);
				System.out.println("Employee ID: " + id);
                System.out.println("Sick Leave Balance: " + sick_Leave_Balance);
				break;
			case 3:
				int casual_Leave_Balance = casualLeaveBalance(id);
				System.out.println("Employee ID: " + id);
                System.out.println("Casual Leave Balance: " + casual_Leave_Balance);
				break;
			case 4:
				Account acc = new Account(username,password);
				Employee emp = new Employee(acc);
				emp.employeeFunctionsDisplay();
			case 5:
				System.out.println("<---THANK FOR USING THIS APPLICATION--->");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice! Please enter valid choice");
			}
				
		}while(true);
        }catch(InputMismatchException e) {
        	System.out.println(e);
        	//displayLeaveBalance(userName,password);
        }
		
	}
	
	/**
	 * employee can able to view the overall leave balance
	 * 
	 * @param id
	 * @throws ClassNotFoundException
	 */
	public static void overallLeaveBalance(int id) throws ClassNotFoundException {
		try (Connection conn = dbc.getDBConnection()) {
            String sql = "SELECT casual_leave_balance, sick_leave_balance FROM PROJECTDB.leave_balance WHERE emp_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int casualLeaveBalance = resultSet.getInt("casual_leave_balance");
                        int sickLeaveBalance = resultSet.getInt("sick_leave_balance");
                        System.out.println("_____________________________________________________");
                        System.out.println("|                                                    |");
                        System.out.println("|   Employee ID: " + id+"                                |");
                        System.out.println("|   Casual Leave Balance : " + casualLeaveBalance+"                        |");
                        System.out.println("|   Sick Leave Balance : " + sickLeaveBalance+"                          |");
                        System.out.println("|____________________________________________________|");
                    } else {
                        System.out.println("Employee with ID " + id + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
	}
	
	/**
	 * employees can view the sick leave balance
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static int sickLeaveBalance(int id) throws ClassNotFoundException {
		try (Connection conn = dbc.getDBConnection()) {
            
            String sql = "SELECT sick_leave_balance FROM PROJECTDB.leave_balance WHERE emp_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int sickLeaveBalance = resultSet.getInt("sick_leave_balance");
                        return sickLeaveBalance;
                     } else {
                        System.out.println("Employee with ID " + id + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
		return 0;
	}
	
	/**
	 * employees can view the casual leave balance
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static int casualLeaveBalance(int id) throws ClassNotFoundException {
		try (Connection conn = dbc.getDBConnection()) {
            String sql = "SELECT casual_leave_balance FROM PROJECTDB.leave_balance WHERE emp_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int casualLeaveBalance = resultSet.getInt("casual_leave_balance");
                        return casualLeaveBalance;
                    } else {
                        System.out.println("Employee with ID " + id + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
		return 0;
	}

}
