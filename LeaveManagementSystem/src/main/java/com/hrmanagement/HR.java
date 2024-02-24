package com.hrmanagement;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.driverclass.Main;
import com.exception.LeaveException;
import com.leavemangement.Account;
import com.leavemangement.Person;

/**
 * The HR class can implements an application that
 * Illustrate to show the functionalities of the hrAdmin
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class HR extends Person{
	
	public HR(Account account) {
		super(account);
	}
	
	public void hrFunction() throws SQLException, ClassNotFoundException, LeaveException {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("WELCOME "+ super.getuserName() +" AS HR !!!!");
		
		
		LeaveCalendar lc = new LeaveCalendar();
		
		
		
		try {
		  do {
			System.out.println();
			System.out.println("1.LEAVE CALENDER");
			System.out.println("2.GENERATE REPORT");
			System.out.println("3.LEAVE REQUEST HISTORY");
			System.out.println("4.MOVE TO PREVIOUS");
			System.out.println("5.EXIT");
			System.out.print("ENTER YOUR CHOICE : ");
			int choice = sc.nextInt();
			
			switch(choice) {
			case 1:
				lc.manageLeaveCalender();
				break;
			case 2:
				Report.generateReport();
				break;
			case 3:
				lc.viewAllLeaveRequest();
				break;
			case 4:
				Main.userRole();
				break;
			case 5:
				System.out.println("<---THANK YOU FOR USING THIS APPLICATION--->");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice , Please enter the correct choice !!!");
			}
		}while(true);
		}catch(InputMismatchException e) {
			System.out.println("Please Enter Integer Values !!!");
        	hrFunction();
        }
		
	}
	
}
