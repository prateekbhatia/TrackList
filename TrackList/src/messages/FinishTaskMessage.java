package messages;

import java.util.Vector;

import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;

public class FinishTaskMessage extends Message{
	private Task task;
	private Vector<Notification> notifications;
	private ToDoList list;
	
	public FinishTaskMessage(Task task, Vector<Notification> notifications){
		super();
		this.task=task;
		this.notifications = notifications;
	}
	public void setTask(Task toUpdate){
		task=toUpdate;
	}
	public Task getTask(){
		return task;
	}
	
	public void setToDoList(ToDoList list){
		this.list = list;
	}
	
	public ToDoList getToDoList(){
		return list;
	}
	
	public Vector<Notification> getNotifications(){
		return notifications;
	}
	
	public void setNotifications(Vector<Notification> notifications){
		this.notifications = notifications;
	}
}
