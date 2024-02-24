package com.management;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.driverclass.Main;
import com.exception.LeaveException;
import com.hrmanagement.LeaveCalendar;
import com.leavemangement.Account;
import com.leavemangement.Person;

/**
 * The Manager class implements an application that
 * Illustrate to show the functionalities of the manager
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 19 FEB 2024
 */

public class Manager extends Person{
	
	/**
	 * special method (or) constructor to initialize a variable
	 * 
	 * @param account
	 */
	public Manager(Account account) {
		super(account);
		
	}
	
	/**
	 * display the functionalities of the manager
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws LeaveException
	 */
	public void managerFunctions() throws SQLException, ClassNotFoundException, LeaveException {
		
		Scanner sc = new Scanner(System.in);
		
		
		LeaveWorkFlow lwf = new LeaveWorkFlow();
		LeaveCalendar lc = new LeaveCalendar();
		try {
		do {
			
			System.out.println();
			System.out.println("WELCOME "+super.getuserName()+" AS MANAGER !!!!");
			System.out.println();
			System.out.println("1.MAKE DECISION ON LEAVE REQUEST");
			System.out.println("2.VIEW ALL LEAVE REQUEST");
			System.out.println("3.VIEW LEAVE CALENDER");
			System.out.println("4.MOVE TO PREVIOUS");
			System.out.println("5.EXIT");
			System.out.println();
			System.out.println("ENTER YOUR CHOICE : ");
			int choice = sc.nextInt();
			System.out.println();
			
			switch(choice) {
			case 1:
				lwf.makeDecision(super.getuserName());
				break;
			case 2:
				lwf.viewAllLeaveRequest();
				break;
			case 3:
				lc.manageLeaveCalender();
				break;
			case 4:
				Main.userRole();
				break;
			case 5:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice! Please enter valid choice");
			}
		}while(true);
		
		}catch(InputMismatchException e) {
        	System.out.println(e);
        	managerFunctions();
        }
	}

}
