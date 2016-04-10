package net.usrlib.parcel;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
		super(message);
	}

	public static final InvalidInputException errorMissingProperty(String propertyName) {
		return new InvalidInputException("Error: JSON input missing '" + propertyName + "' keyname");
	}
}
