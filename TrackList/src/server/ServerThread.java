package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import Constants.MessageConstants;
import Database.DatabaseFunctions;
import messages.AddListMessage;
import messages.AddTaskMessage;
import messages.FinishTaskMessage;
import messages.LoginMessage;
import messages.Message;
import messages.ReadNotificationMessage;
import messages.ShareListMessage;
import utilities.Notification;
import utilities.UserAccount;

public class ServerThread extends Thread{
	private Socket s;
	private Server server;
	private DatabaseFunctions dbf;
	
	private final static boolean DEBUG = false;
	
	private UserAccount user;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ServerThread(Socket s, Server server, DatabaseFunctions dbf){
		this.s = s;
		this.server = server;
		this.dbf = dbf;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException ioe) {ioe.printStackTrace();}
		start();
	}
	
	public void run(){
		try{
			while(true){
				Message msg = (Message)ois.readObject();
				parseMessage(msg);
			}
		} catch (IOException ioe) {
			if (DEBUG)
				ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {cnfe.printStackTrace();}
	}
	
	private void parseMessage(Message message){
		Message msg = message;
		if(msg instanceof LoginMessage){
			LoginMessage lm = (LoginMessage)msg;
			boolean success = lm.isLogin() ? login(lm) : createAccount(lm);
			lm.setSuccess(success);
			sendMessage(lm);
			/*if(success){
				sendAllData();
			}*/
		} else if (msg instanceof AddListMessage){
			AddListMessage alm = (AddListMessage)msg;
			boolean success = addToDoList(alm);
			alm.setSuccess(success);
			sendMessage(alm);
			for(int i=0 ; i< alm.getUserLists().size(); i++) {
				System.out.println(alm.getUserLists().get(i).getName());
			}
		} else if (msg instanceof AddTaskMessage){
			AddTaskMessage atm = (AddTaskMessage)msg;
			addTask(atm);
			sendMessage(atm);
		} else if (msg instanceof ShareListMessage){
			ShareListMessage slm = (ShareListMessage)msg;
			boolean success = shareList(slm);
			user = dbf.getUser(user.getUsername());
			slm.setUserLists(user.getProjects());
			slm.setSuccess(success);
			sendMessage(slm);
			for(String u : slm.getList().getAssignees()){
				UserAccount ua = dbf.getUserAccount(u);
				server.sendToActiveUsers(ua, slm);
			}
			server.sendToActiveUsers(user, slm);
		} else if (msg instanceof ReadNotificationMessage){
			ReadNotificationMessage rnm = (ReadNotificationMessage)msg;
			readNotification(rnm);
			rnm.setSuccess(true);
			sendMessage(rnm);
		} else if (msg instanceof FinishTaskMessage){
			FinishTaskMessage ftm = (FinishTaskMessage)msg;
			boolean success = finishTask(ftm);
			if(user == null)user = dbf.getUser(ftm.getToDoList().getUsername());
			ftm.setSuccess(success);
			sendMessage(ftm);
		}
	}
	
	public void sendMessage(Message msg){
		try{
			oos.writeObject(msg);
		} catch(SocketException se){
			server.removeThread(this);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} /*finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	private boolean login(LoginMessage lm){
		if(DatabaseFunctions.validCredentials(lm.getUser())){
			user = dbf.getUser(lm.getUser().getUsername());
			lm.setUser(user);
			return true;
		} else {
			lm.setErrorMessage(MessageConstants.invalidCredentials);
			return false;
		}
	}
	
	private boolean createAccount(LoginMessage lm){
		if(dbf.signUp(lm.getUser())){
			user = lm.getUser();
			return true;
		} else {
			lm.setErrorMessage(MessageConstants.userAlreadyExists);
			return false;
		}
	}
	
	private boolean addToDoList(AddListMessage alm){
		if(dbf.addToDoList(alm.getToDoList())){
			UserAccount assigner = dbf.getUser(alm.getToDoList().getAssigner());
			alm.setUserLists(assigner.getProjects());
			System.out.println("size sever:"+assigner.getProjects().size());
			return true;
		}
		alm.setErrorMessage("Sorry, a list with this name already exists");
		return false;
	}
	
	private boolean addTask(AddTaskMessage atm){
		if(dbf.addTask(atm.getTask())){
			atm.setToDoList(dbf.getToDoList(atm.getTask().getParent(), atm.getTask().getOwner()));
			atm.setSuccess(true);
			Vector<String> sharedWith = atm.getToDoList().getAssignees();
			Vector<Notification> notifications = atm.getNotifications();
			
			if (sharedWith.size() != 1) {
				for(Notification notification : notifications){
					dbf.addNotification(notification);
					UserAccount assignee = dbf.getUser(notification.getAssignee().getUsername());
					Vector<Notification> n = assignee.getNotifications();
					server.notifyUser(assignee, n);
					server.sendToActiveUsers(assignee, atm);
				}
			}
			atm.setSuccess(true);
			return true;
		} else {
			atm.setSuccess(false);
			atm.setErrorMessage("Sorry, the task could not be added");
			return false;
		}
	}
	
	private boolean finishTask(FinishTaskMessage ftm){
		dbf.setCompleted(ftm.getTask());
		ftm.setToDoList(dbf.getToDoList(ftm.getTask().getParent(), ftm.getTask().getOwner()));
		ftm.setSuccess(true);
		Vector<String> sharedWith = ftm.getToDoList().getAssignees();
		Vector<Notification> notifications = ftm.getNotifications();
		if (sharedWith.size() != 1) {
			for(Notification notification : notifications){
				dbf.addNotification(notification);
				UserAccount assignee = dbf.getUser(notification.getAssignee().getUsername());
				Vector<Notification> n = assignee.getNotifications();
				server.notifyUser(assignee, n);
				server.sendToActiveUsers(assignee, ftm);
			}
		}
		ftm.setSuccess(true);
		return true;
	}
	
	private boolean shareList(ShareListMessage slm){
		UserAccount assignee = dbf.getUser(slm.getNotification().getAssignee().getUsername());
		boolean exists = assignee == null ? false : true;
		if(exists){
			if(dbf.addUserToList(assignee, slm.getList())){
				dbf.addNotification(slm.getNotification());
				assignee = dbf.getUser(slm.getNotification().getAssignee().getUsername());
				slm.setUserLists(assignee.getProjects());
				Vector<Notification> n = assignee.getNotifications();
				server.notifyUser(assignee, n);
				AddListMessage alm = new AddListMessage(null);
				alm.setSuccess(true);
				alm.setUserLists(assignee.getProjects());
				server.sendToActiveUsers(assignee, alm);
				return true;
				
			}
		}
		slm.setErrorMessage(MessageConstants.userDoesNotExist);
		return false;
	}
	
	private void readNotification(ReadNotificationMessage rnm){
		dbf.setRead(rnm.getNotification());
	}
	
	/*private void sendAllData(){
		if(user != null){
			AddListMessage alm = new AddListMessage(null);
			alm.setSuccess(true);
			alm.setUserLists(dbf.getUserLists(user));
			sendMessage(alm);
			NotificationMessage nm = new NotificationMessage(dbf.getUserAccount(user.getUsername()).getNotifications());
			sendMessage(nm);
		}
	}*/
	
	public UserAccount getUser(){
		if(user != null){
			return user;
		} else {
			return null;
		}
	}
}