package com.sysadmin;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.driverclass.Main;
import com.exception.LeaveException;
import com.leavemangement.Account;
import com.leavemangement.Person;

/**
 * The SystemAdmin class implements an application that
 * Illustrate to show the functionalities of the system admin
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 18 FEB 2024
 */

public class SystemAdmin extends Person {
	
	public SystemAdmin(Account account) {
		super(account);
	}
	
	/**
	 * display the functionalities of the SystemAdmin
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws LeaveException
	 */
	public void menuDisplay() throws SQLException, ClassNotFoundException, LeaveException {
		DepartmentManagement dm = new DepartmentManagement();
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$           WELCOME "+super.getuserName()+" AS SYSTEM ADMIN!!! $");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		
		UserAccounts acc = new UserAccounts();
		boolean flag=true;
		
		try {
		while(flag) {
			System.out.println();
			System.out.println("1.CREATE RECORD FOR NEW EMPLOYEE");
			System.out.println("2.READ RECORDS OF ALL THE EMPLOYEES");
			System.out.println("3.UPDATE RECORDS OF EXITING EMPLOYEES");
			System.out.println("4.DELETE RECORDS OF EXITING EMPLOYEES");
			System.out.println("5.DEPARTMENT MANAGEMENT");
			System.out.println("6.GO BACK");
			System.out.println("7.EXIT");
			System.out.println();
			System.out.println("Enter Your Option : ");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				acc.addRecords();
				break;
			case 2:
				acc.readRecords();
				break;
			case 3:
				acc.updateRecords(super.getuserName());
				break;
			case 4:
				acc.deleteRecords();
				break;
			case 5:
				dm.manageDepartment();
				break;
			case 6:
				Main.userRole();
				break;
			case 7:
				System.out.println("<-- (: THANK FOR USING THIS APPLICATION :) -->");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice !! Please enter a valid one.");
				System.out.println();
		   }
		}
		}catch(InputMismatchException e) {
			System.out.println("Please Enter Integer Values !!!");
        	menuDisplay();
        }
	}
}
