package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Vector;

import Constants.MessageConstants;
import frames.CreateAccountGUI;
import frames.LoginGUI;
import frames.ToDoListMainGUI;
import messages.AddListMessage;
import messages.AddTaskMessage;
import messages.FinishTaskMessage;
import messages.LoginMessage;
import messages.NotificationMessage;
import messages.ReadNotificationMessage;
import messages.ShareListMessage;
import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;
import utilities.UserAccount;

public class client extends Thread{
	private ObjectInputStream ois;
	private ObjectOutput oos;
	private UserdataLocal userdatalocal;
	private boolean guestMode= false;
	private ToDoListMainGUI mainGUI;
	private LoginGUI loginGUI;
	private Timer timer;
	private CreateAccountGUI createAccountGUI;
	private HashSet<ToDoList> listData;
	
	public client(String hostname, int port){
		listData =new HashSet<ToDoList>();
		Socket socket =null;
		try{
			socket = new Socket(hostname, port);
			oos=new ObjectOutputStream(socket.getOutputStream());	
			ois=new ObjectInputStream(socket.getInputStream());
			this.start();
			
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		loginGUI=new LoginGUI(this);
		loginGUI.setVisible(true);
	}

	public void login(UserAccount toLogin){
		sendMessage(new LoginMessage(toLogin,true));
		
	}
	public void createAccount(UserAccount toCreate){
		sendMessage(new LoginMessage(toCreate,false));
	}
	public void addList(ToDoList toAdd){
		if(guestMode){
			Boolean success =userdatalocal.addList(toAdd);
			if(success){
				mainGUI.populateLists(userdatalocal.getTaskList());
			}
			else{
				loginGUI.showErrorMessage("Error - adding a list");
			}
		}
		else {
			if(!listData.contains(toAdd)){
				sendMessage(new AddListMessage(toAdd));
			}
		}
	}
	
	public void addTask(Task toAdd){
		if(guestMode){
			Boolean success =userdatalocal.addTask(toAdd);
			if(success){
				mainGUI.populateLists(userdatalocal.getTaskList());
			}
			else{
				mainGUI.showErrorMessage("Error - adding a task");
			}
		}
		else{
			Vector<Notification> temp= new Vector<Notification>();
			ToDoList list =userdatalocal.getListofTask(toAdd);
			for(Task task: list.getTasks()){
				if(task.getTask().equalsIgnoreCase(toAdd.getTask())){
					mainGUI.showErrorMessage("Error - you cannot add task with same name in the list");
					return;
				}
			}
			Vector<String> toSend = list.getAssignees();
			for(String user: toSend){
				temp.add(new Notification(MessageConstants.newAddedTask,new UserAccount(user, "", "", "")));
			}
			sendMessage(new AddTaskMessage(toAdd,temp));
		}
	}
	
	public void createAccount(CreateAccountGUI gui){
		createAccountGUI=gui;
	}
	
	public void closeCreateAccountGUI(){
		if(createAccountGUI!=null){
			createAccountGUI.setVisible(false);
		}
	}
	
	public void finishTask(Task task){
		if (guestMode){
			userdatalocal.finishTask(task);
			mainGUI.populateLists(userdatalocal.getTaskList());
			return;
		}
		Vector<Notification> temp= new Vector<Notification>();
		ToDoList list =userdatalocal.getListofTask(task);
		Vector<String> toSend = list.getAssignees();
		for(String user: toSend){
			temp.add(new Notification(MessageConstants.finishTask,new UserAccount(user, "", "", "")));
		}
		sendMessage(new FinishTaskMessage(task,temp));
	}
	
	public void guestlogin(){
		guestMode=true;
		mainGUI= new ToDoListMainGUI(new UserAccount("", "","",""),this);
		mainGUI.setVisible(true);
		userdatalocal=new UserdataLocal(new UserAccount("guest", "", "",""));
	}
	
	public void readNotification(Vector<Notification> reads){
		for (Notification read:reads){
			sendMessage(new ReadNotificationMessage(read));
		}
	}
	
	public void startTimeMode(int time){//thread
		timer =new Timer(time,this);
	}
	
	public void endTimer(){
		mainGUI.endTimer();
	}
	public void updateTimer(){
		mainGUI.updateTimer();
	}
	
	public void stopTimer(){
		if(timer!=null){
			timer.stopTimer();
		}
	}
	
	public void addSharedUser(ToDoList list, String assignee){
		if(guestMode){
			mainGUI.showErrorMessage("Error - you cannot share in the guest mode.Register an account!");
			return;
		}
		if (!assignee.equals(userdatalocal.getUserAccount().getUsername())){
			Notification temp = new Notification(MessageConstants.newAddedPerson, new UserAccount(assignee, "","",""));
			sendMessage(new ShareListMessage(list,temp));
		}
		else {
			mainGUI.showErrorMessage("Error - you cannot share the list with yourself");
		}
		
	}
	
	public void sendMessage(Object o){
		try{
			oos.writeObject(o);
			oos.flush();
		}catch (IOException e) {
			System.out.println("ioe message sending: "+e.getMessage());
		}	
	}
	public void run(){
		try{
			while(true){
				Object o=(Object)ois.readObject();
				if(o instanceof LoginMessage){	
					if(((LoginMessage) o).getSuccess()){
						UserAccount temp=((LoginMessage) o).getUser();
						userdatalocal= new UserdataLocal(temp);
						mainGUI= new ToDoListMainGUI(userdatalocal.getUserAccount(),this);
						mainGUI.setVisible(true);
						loginGUI.setVisible(false);
						closeCreateAccountGUI();
					}
					else{
						LoginMessage message=(LoginMessage) o;
						loginGUI.showErrorMessage(message.getMessage());
					}
				}
				else if(o instanceof AddTaskMessage) {
					AddTaskMessage message =(AddTaskMessage)o;
					if(message.getSuccess()){
						userdatalocal.updateList(message.getToDoList());
						mainGUI.populateLists(userdatalocal.getTaskList());
					}
					else{
						mainGUI.showErrorMessage(message.getMessage());
					}
				}
				else if(o instanceof NotificationMessage){
					NotificationMessage message =(NotificationMessage)o;
					if(message.getSuccess()){
						System.out.println(message.getNotifications().size() +"");
						userdatalocal.setNotification(message.getNotifications());
						mainGUI.populateNotifications(message.getNotifications());
					}
					else{
						mainGUI.showErrorMessage(message.getMessage());
					}
				}
				else if (o instanceof AddListMessage){
					AddListMessage message = (AddListMessage)o;
					if(message.getSuccess()){
						mainGUI.populateLists(message.getUserLists());
						userdatalocal.setToDoList(message.getUserLists());
						System.out.println("size: "+message.getUserLists().size());
					}
					else{
						mainGUI.showErrorMessage(message.getMessage());
					}
					
				}
				else if(o instanceof ShareListMessage){
					ShareListMessage message=(ShareListMessage) o;
					if(message.getSuccess()){
						mainGUI.populateLists(message.getUserLists());
						userdatalocal.setToDoList(message.getUserLists());
					}
					else{
						mainGUI.showErrorMessage(message.getMessage());
					}
				}else if(o instanceof FinishTaskMessage){
					FinishTaskMessage message =(FinishTaskMessage)o;
					if(message.getSuccess()){
						userdatalocal.updateList(message.getToDoList());
						mainGUI.populateLists(userdatalocal.getTaskList());
					}
					else{
						mainGUI.showErrorMessage(message.getMessage());
					}
					
				}

			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
