package com.facebook.automation.utils;

import java.util.regex.Pattern;

public class Utility {

	/**
	 * Compare email field and emailConfirmation. return true if identical
	 * 
	 * @param email
	 * @param emailConfirmation
	 * @return
	 */
	public static boolean compareEmail(String email, String emailConfirmation) {
		return email.equals(emailConfirmation);
	}
	
	/**
	 * Check email format
	 * 
	 * @param emailAddress
	 * @param regexPattern
	 * @return
	 */
	public static boolean emailValidation(String emailAddress) {
		String regexPattern = "^(.+)@(\\S+)$";
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	/**
	 * Return true if year of birthday is eligible to have account
	 *  
	 * @param year
	 * @return
	 */

	public static boolean eligibileAge(int year) {
		return year >= 2010;
	}
	
	/**
	 * Check password format
	 * 
	 * @param password
	 * @param regexPattern
	 * @return
	 */
	public static boolean passwordValidation(String password) {
	
        String passwordRegex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
        
	    return Pattern.compile(passwordRegex)
	      .matcher(password)
	      .matches();
	}
	
	
}
