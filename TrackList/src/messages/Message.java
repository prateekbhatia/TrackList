package messages;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 2L;
	private Boolean success;
	private String errorMessage;
	
	public Message(){
		success =false;
	}
	public void setSuccess(boolean in){
		success=in;
	}
	
	public boolean getSuccess(){
		return success;
	}
	
	//Server can set an error message for the GUI to display
	//when the client receives a notification
	public void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	public String getMessage() {
		return errorMessage;
	}
}