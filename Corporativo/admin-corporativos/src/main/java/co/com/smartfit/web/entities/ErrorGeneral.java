package co.com.smartfit.web.entities;

public class ErrorGeneral extends  Error{
	private String message;
	private int statusCode;

	public ErrorGeneral(int statusCode, String message) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public String getMessage() {
	return message;
	}

	public void setMessage(String message) {
	this.message = message;
	}

	public int getStatusCode() {
	return statusCode;
	}

	public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
	}
}
