package com.hsbc.meets.exception;

import java.io.IOException;

/**
 * An Exception class to deal with reading
 * properties from external file.
 * 
 * @author alan
 *
 */
public class InvalidPropFileException extends Exception {

	private boolean fileRead;
	private String brokenProperty;
	/**
	 * Creates instance of {@link InvalidPropFileException}
	 * caused due to missing file
	 * 
	 * @param ioException {@link IOException} object thrown when file not found
	 */
	public InvalidPropFileException(IOException ioException) {
		super(ioException);
		this.fileRead = false;
		this.brokenProperty = "";
	}

	/**
	 * Creates instance of {@link InvalidPropFileException}
	 * caused due to missing property
	 * 
	 * @param brokenProperty
	 */
	public InvalidPropFileException( String brokenProperty) {
		super();
		this.fileRead = true;
		this.brokenProperty = brokenProperty;
	}
	@Override
	public String toString() {
		if (fileRead) {
			return brokenProperty + "not found in prop file";
		} else {
			return super.toString();
		}
	}

	/**
	 * @return the error message
	 */
	public String getMessage() {
		return "Prop File not processed";
	}
}
