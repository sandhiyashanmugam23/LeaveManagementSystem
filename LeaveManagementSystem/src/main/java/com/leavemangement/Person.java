package com.leavemangement;

/**
 * Person class implements an application that 
 * Illustrate to fetch the person details
 * 
 * @author Sandhiya Shanumugam (Expleo)
 * @since 19 FEB 2024
 */

public class Person {
     protected String username;
     protected String password;
     protected Account account;

     /**
      * special method for the initialization of variable
      * 
      * @param account
      */
     public Person(Account account) {
            this.account=account;
     }

     /**
      * get a userName through Account class
      * 
      * @return userName
      */
     public String getuserName() {
           return account.getUsername();
     }

     /**
      * set a userName through Account class
      */
     public void setuserName() {
    	 this.username=account.getUsername();
     }
     
     /**
      * get a password through Account class
      * 
      * @return password
      */
     public String getPassword() {
           return account.getPassword();
     }
     
     /**
      * set a password through Account class
      */
     public void setPassword() {
    	 this.password=account.getPassword();
     }
}
