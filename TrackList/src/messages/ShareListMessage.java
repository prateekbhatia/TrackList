package messages;

import java.util.Vector;

import utilities.Notification;
import utilities.ToDoList;

public class ShareListMessage extends Message{
	private Vector<ToDoList> userLists;
	private ToDoList list;
	private Notification notification;
	
	public ShareListMessage(ToDoList list, Notification notification){
		super();
		this.list = list;
		this.notification = notification;
	}
	
	public void setUserLists(Vector<ToDoList> lists){
		this.userLists = lists;
	}
	
	public Vector<ToDoList> getUserLists(){
		return this.userLists;
	}
	
	public Notification getNotification(){
		return notification;
	}
	
	public ToDoList getList(){
		return list;
	}
}
