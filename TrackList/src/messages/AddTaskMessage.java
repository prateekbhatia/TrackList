package messages;

import java.util.Vector;

import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;

public class AddTaskMessage extends Message{
	private Task mTask;
	private ToDoList list;
	private Vector<Notification> notifications;
	public AddTaskMessage(Task task,Vector<Notification> notifications){
		super();
		this.notifications=notifications;
		mTask =task;
	}
	
	public void setTask(Task task){
		mTask=task;
	}
	public Task getTask(){
		return mTask;
	}
	public boolean setToDoList(ToDoList toSet){
		if(mTask.getParent().equals(toSet.getName())){
			list =toSet;
			return true;
		}
		return false;
	}
	public ToDoList getToDoList(){
		return list;
	}
	public void setNotifications(Vector<Notification> toSet){
		notifications=toSet;
	}
	public Vector<Notification> getNotifications(){
		return notifications;
	}
}
