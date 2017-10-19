package com.brightsconsulting.sgml4j.exceptions;

public class InvalidNodeNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5619483047929949575L;

	public String message;

	public InvalidNodeNameException(String _message) {
		this.message = _message;
	}

}
