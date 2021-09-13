package com.covid.main;


/**
 * This is a Exception class created specifically to this Covid application
 */
public class ApplicationValidationException extends Exception {

	/**
	 * Constructor
	 * Overrides the super class with the exception message passed
	 */
	public ApplicationValidationException(String exception) {
		super(exception);
	}
}
