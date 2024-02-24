package com.employeemanagement;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.driverclass.Main;
import com.exception.LeaveException;
import com.leavemangement.Account;
import com.leavemangement.Person;
import com.notificationdetail.Notification;

/**
 * The Employee class implements an application that
 * Illustrate to show the functionalities of the employee
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 19 FEB 2024
 */

public class Employee extends Person{

	
	public Employee(Account account) {
		super(account);
	}
    
	/**
	 * display the functionalities of the employee
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws LeaveException 
	 */
	public void employeeFunctionsDisplay() throws SQLException, ClassNotFoundException, LeaveException {
		
		
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$        Welcome "+super.getuserName()+" AS EMPLOYEE!          $");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		try (Scanner sc = new Scanner(System.in)) {
			
			do{
				System.out.println();
				System.out.println("1.REQUEST FOR LEAVE");
				System.out.println("2.DISPLAY LEAVE BALANCE");
				System.out.println("3.CANCEL THE LEAVE REQUEST");
				System.out.println("4.MODIFY THE LEAVE REQUEST");
				System.out.println("5.VIEW NOTIFICATION");
				System.out.println("6.PREVIOUS PAGE");
				System.out.println("7.EXIT");
				System.out.println();
				System.out.print("ENTER YOUR CHOICE : ");
				int choice = sc.nextInt();
				
				switch(choice) {
				case 1:
					Leave.applyLeave(super.getuserName(),super.getPassword());
					break;
				case 2:
					Leave.displayLeaveBalance(super.getuserName(),super.getPassword());
					break;
				case 3:
					Leave.cancelLeaveRequest(super.getuserName());
					break;
				case 4:
					Leave.modifyLeaveRequest(super.getuserName(),super.getPassword());
					break;
				case 5:
					int empID = Leave.fetchEmployeeId(super.getuserName());
					Notification.viewNotification(empID);
					break;
				case 6:
					Main.userRole();
					break;
				case 7:
					System.out.println("<---THANK YOU FOR USING THIS APPLICATION--->");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice! Please enter valid choice");
				}
			}while(true);
		}catch(InputMismatchException e) {
			System.out.println("Please Enter Integer Values !!!");
        	employeeFunctionsDisplay();
        }
	}
	

}
