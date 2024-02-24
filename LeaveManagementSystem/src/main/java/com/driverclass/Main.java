package com.driverclass;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.employeemanagement.Employee;
import com.exception.LeaveException;
import com.hrmanagement.HR;
import com.leavemangement.Account;
import com.management.Manager;
import com.sysadmin.SystemAdmin;
import com.sysadmin.UserAccounts;


public class Main{

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, LeaveException {
		System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$           LEAVE MANAGEMENT SYSTEM APPLICATION                $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println();
        
		userRole();
	}
	
    public static void userRole() throws SQLException, ClassNotFoundException, LeaveException {
		
    	
        
        //System.out.println("$".repeat(60));
        try {
        do {
        	Scanner sc = new Scanner(System.in);
            System.out.println("1. SYSTEM ADMIN");
            System.out.println("2. EMPLOYEE");
            System.out.println("3. MANAGER");
            System.out.println("4. HR");
            System.out.println("5. Exit");

            
            System.out.println();
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$                      LOGIN APPLICATION                       $");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println();
            System.out.println("                                      +------------------------+");
            System.out.println("                                      |   6.FORGOT PASSWORD    |");
            System.out.println("                                      +------------------------+");
            System.out.println();
        	//sc.nextLine();
        
        	 System.out.print("ENTER YOUR CHOICE : ");
        	 String username="";
        	 String password="";
             int choice = sc.nextInt(); 
             if(choice < 5) {
            	 System.out.print("Enter username: ");
                 username = sc.next();
                 System.out.print("Enter password: ");
                 password = sc.next();
             }
         	 
             Account acc = new Account(username,password);
        	 
        switch (choice) {
        
               case 1:
                   if(acc.login("ADMIN")) {
                	   
                	   SystemAdmin admin = new SystemAdmin(acc);
                	   admin.menuDisplay();
                   }else {
                	   
                	   Main.userRole();
                   }
                   break;
             
               case 2:
            	   if(acc.login("EMPLOYEE")) {
            		   Employee emp = new Employee(acc);
            		   emp.employeeFunctionsDisplay();
            	   }else {
            		   Main.userRole();
            	   }
                   break;
               case 3:
            	   if(acc.login("MANAGER")) {
            		   
            		   Manager manager = new Manager(acc);
            		   manager.managerFunctions();
            	   }else {
            		  
            		   Main.userRole();
            	   }
                   
                   break;
               case 4:
            	   if(acc.login("HR")) {
            		   HR hr = new HR(acc);
            		   hr.hrFunction();
            	   }else {
            		   Main.userRole();
            	   }
                   break;
                  
               case 5:
            	   System.out.println("<---THANK YOU FOR USING THIS APPLICATION--->");
            	   System.exit(0);
            	   break;
               case 6:
            	   System.out.println("Enter UserName : ");
            	   String userName = sc.next();
            	   UserAccounts.updatePassword(userName);
            	   break;
               default:
                   System.out.println("Invalid choice! Please enter valid choice");
               
           }
        }while(true);
        }catch(InputMismatchException e) {
        	System.out.println("Please Enter Integer Values !!!");
        	userRole();
        }
	}
}


