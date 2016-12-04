package messages;

import utilities.UserAccount;

/*This class is used to communicate login/create account
 * information in-between server and client. It will be
 * sent back from server with a success return value of true
 * if the login attempt was validated
 */

public class LoginMessage extends Message{
	private UserAccount user;
	private boolean login;
	
	//SERVER CONSTRUCTOR
	//Only returns information about success or failure
	//to login/create account
	public LoginMessage(){
		super();
	}
	
	//CLIENT CONSTRUCTOR
	//constructor for client to send to server, containing user data
	public LoginMessage(UserAccount user, boolean login){
		super();
		this.user = user;
		this.login = login;
	}
	
	//returns true if login message, false if create account message
	public boolean isLogin(){
		return login;
	}
	
	//returns user account
	public UserAccount getUser(){
		return user;
	}
	
	public void setUser(UserAccount ua){
		this.user = ua;
	}
}
