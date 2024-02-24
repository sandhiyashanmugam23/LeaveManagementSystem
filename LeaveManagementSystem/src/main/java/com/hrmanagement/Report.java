package com.hrmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBConnection.DatabaseConnector;

/**
 * The Report class implements an application that 
 * Illustrate to generate a report by the hrAdmin
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class Report {
	
	static DatabaseConnector dbc = new DatabaseConnector();
	
	/**
	 * this method generate report of the overall application
	 */
	public static void generateReport() {
	    Report r = new Report();
	    System.out.println("_____________________________________________________________");
	    System.out.println("|                                                            |");
	    System.out.println("|                      REPORT GENERATION                     |");
	    System.out.println("|____________________________________________________________|");
	    System.out.println("|                                                            |");
	    System.out.println("|    REGISTERED EMPLOYEE COUNT : " + r.countRegisteredEmployee() + " ".repeat(27)+"|");
	    System.out.println("|                                                            |");
	    System.out.println("|    AVAILABLE DEPARTMENTS : " + r.countDepartments() + " ".repeat(31)+"|");
	    System.out.println("|                                                            |");
	    System.out.println("|    AVAILABLE LEAVE TYPES : 5                               |");
	    System.out.println("|                                                            |");
	    System.out.println("|    PENDING APPLICATION : " + r.pendingApplication() +" ".repeat(33)+"|");
	    System.out.println("|                                                            |");
	    System.out.println("|    DECLINED APPLICATION : " + r.declinedApplication() + " ".repeat(32)+"|");
	    System.out.println("|                                                            |");
	    System.out.println("|    APPROVED APPLICATION : " + r.approvedApplication() +" ".repeat(32)+"|");
	    System.out.println("|                                                            |");
	    System.out.println("|____________________________________________________________|");
	    System.out.println();
	}
	
	/**
	 * count the number of registered employees
	 * 
	 * @return employee_count
	 */
	private int countRegisteredEmployee() {
		int employee_count =0;
		
		try {
      
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = dbc.getDBConnection();
            
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT COUNT(*) AS count FROM PROJECTDB.employee";
            
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                employee_count = rs.getInt("count");
            }

            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
		return employee_count;
		
	}
	
	/**
	 * count the number of pending leave Request
	 * 
	 * @return prequest_count
	 */
	private int pendingApplication() {
		int prequest_count=0;
		try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
        
            Connection conn = dbc.getDBConnection();

            Statement stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS count FROM PROJECTDB.leave_request WHERE status = 'Requested'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                prequest_count = rs.getInt("count");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
		return prequest_count;
		
	}
	
	/**
	 * count of declined application
	 * 
	 * @return drequest_count
	 */
	private int declinedApplication() {
		int drequest_count=0;
		try {
           
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = dbc.getDBConnection();
            
            Statement stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS count FROM PROJECTDB.leave_request WHERE status = 'Rejected'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                drequest_count = rs.getInt("count");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            
            System.out.println(se);
        } catch (Exception e) {
        	
            System.out.println(e);
        }
		return drequest_count;
		
	}
	
	/**
	 * count of approved application
	 * 
	 * @return arequest_count
	 */
	private int approvedApplication() {
		int arequest_count=0;
		try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = dbc.getDBConnection();

            Statement stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS count FROM PROJECTDB.leave_request WHERE status = 'Approved'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                arequest_count = rs.getInt("count");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            
            System.out.println(se);
        } catch (Exception e) {
            
            System.out.println(e);
        }
		return arequest_count;
	}
	
	/**
	 * count the department of the organization
	 * 
	 * @return dept_count
	 */
	private int countDepartments() {
        int dept_count =0;
		
		try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = dbc.getDBConnection();

            Statement stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS count FROM PROJECTDB.department";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                dept_count = rs.getInt("count");
            }

            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
        	System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
		return dept_count;
		
	}
	
}
