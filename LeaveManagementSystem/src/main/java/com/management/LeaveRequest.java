package com.management;

import java.sql.Date;

/**
 * LeaveRequest class implements an application that
 * Illustrate to have a attributes of leave request
 * 
 * @author Sandhiya Shanmugam (Expleo)
 * @since 20 FEB 2024
 */

public class LeaveRequest {
	private int leaveId;
    private int employeeId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
    
    /**
     * special method (or) constructor for the initialization of attributes
     * 
     * @param leaveId
     * @param employeeId
     * @param leaveType
     * @param startDate
     * @param endDate
     * @param reason
     * @param status
     */
	public LeaveRequest(int leaveId, int employeeId, String leaveType, Date startDate, Date endDate, String reason, String status) {
		this.leaveId = leaveId;
		this.employeeId = employeeId;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.status=status;
	}
	
	/**
	 * get the status of the leave Request
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * set the status of the leave Request
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * get the leaveId of the leave Request
	 * 
	 * @return leaveId
	 */
	public int getLeaveId() {
		return leaveId;
	}
	
	/**
	 * set the leaveId of the leave Request
	 * 
	 * @param leaveId
	 */
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	
	/**
	 * get the employee ID of the leave Request
	 * 
	 * @return employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	
	/**
	 * set the employee Id of the leave Request
	 * 
	 * @param employeeId
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	/**
	 * get the leave type of the leave Request
	 * 
	 * @return leaveType
	 */
	public String getLeaveType() {
		return leaveType;
	}
	
	/**
	 * set the leave type of the leave Request
	 * 
	 * @param leaveType
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	/**
	 * get the start date of the leave Request
	 * 
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * set the start date of the leave Request
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * get the end date of the leave Request
	 * 
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * set the end date of the leave Request
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * get the reason of the leave Request
	 * 
	 * @return reason
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * set the reason of the leave Request
	 * 
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "LeaveRequest [leaveId=" + leaveId + ", employeeId=" + employeeId + ", leaveType=" + leaveType
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", reason=" + reason + ", status=" + status
				+ "]";
	}
}
