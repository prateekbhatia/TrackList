package client;

import java.util.Stack;
import java.util.Vector;

import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;
import utilities.UserAccount;

public class UserdataLocal {
	private UserAccount userData;
	private Stack<Task> tasks;
	UserdataLocal(UserAccount userdata){
		userData=userdata;
		tasks = new Stack<Task>();
	}
	Vector<ToDoList> getTaskList(){
		return userData.getProjects();
	}
	Vector<Notification> getNotification(){
		return userData.getNotifications();
	}
	void setUserAccount(UserAccount toUpdate){
		userData =toUpdate;
	}
	UserAccount getUserAccount(){
		return userData;
	}
	void refreshAll(){
		
	}
	
	void finishTask(Task task){
		ToDoList list =userData.getListofTask(task);
		for(Task toset:list.getTasks()){
			if(task.getTask().equals(toset.getTask())){
				toset.setCompleted();
			}
		}
	}
	
	boolean updateList(ToDoList toUpdate){
		
		Vector<ToDoList> taskList=userData.getProjects();
		for(int i =0;i<taskList.size();++i){
			if(taskList.get(i).getName().equals(toUpdate.getName())){
				taskList.set(i, toUpdate);
				return true;
			}
		}
		return false;
		
	}
	
	Boolean addTask(Task task){
		Vector<ToDoList> taskList=userData.getProjects();
		Boolean toReturn=false;
		for(ToDoList list:taskList){
			if(task.getParent().equalsIgnoreCase(list.getName())){
				list.addTask(task);
				tasks.push(task);
				toReturn=true;
			}
		}
		return toReturn;
	}
	Boolean addList(ToDoList toAdd){
		Vector<ToDoList> taskList=userData.getProjects();
		Boolean toReturn =true;
		for(ToDoList list:taskList){
			if(list.getName().equals(toAdd)){
				toReturn=false;
			}
		}
		if(toReturn){
			taskList.add(toAdd);
		}
		return toReturn;
	}
	
	ToDoList getListofTask(Task task){
		Vector<ToDoList> taskList=userData.getProjects();
		String parent= task.getParent();
		for(ToDoList list:taskList){
			if(list.getName().equals(parent)){
				return list;
			}
		}
		return null;
	}
	void setNotification(Vector<Notification> toSet){
		if(toSet!=null){
			userData.setNotifications(toSet);
		}
	}
	void setToDoList(Vector<ToDoList> toSet){
		if(toSet!=null){
			userData.setProjects(toSet);
		}
	}

}
