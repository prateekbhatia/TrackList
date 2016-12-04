package utilities;


import java.io.Serializable;


public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String message; //contains the message of the Notification
	private boolean read; //indicates whether the Notification has been read by the receiving user
	private UserAccount assignee; //UserAccount that the Notification is directed towards
	private int index; //determines priority/placement in UserAccount's list of Notifications
	
	public Notification(String m, boolean r, UserAccount b, int i){
		message = m;
		read = r;
		assignee = b;
		index = i;
	}
	
	public Notification(String m, boolean r, UserAccount b) {
		message = m;
		read = r;
		assignee = b;
		index = 0;
	}
	
	public Notification(String m, UserAccount b){
		message = m;
		read = false;
		assignee = b;
		index = 0;
	}
	
	//getters
	public int getIndex() {
		return index;
	}
	
	public boolean getRead() {
		return read;
	}
	
	public String getMessage(){
		return message;
	}
	
	public UserAccount getAssignee(){
		return assignee;
	}
	
	//mutator
	public void setRead(){
		read = true;
	}
	
}
