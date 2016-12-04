package utilities;



import java.io.Serializable;
import java.util.Vector;


public class UserAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//self explanatory 
	private String username, password, firstName, lastName;
	//Notifications belonging to the UserAccount
	private Vector<Notification> notifications;
	//ToDoList projects the UserAccount is a part of
	private Vector<ToDoList> projects;
	
	public UserAccount(String u, String p, String fn, String ln){
		username = u;
		password = p;
		firstName = fn;
		lastName = ln;
		notifications = new Vector<Notification>();
		projects = new Vector<ToDoList>();
	}
	
	//getters
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Vector<Notification> getNotifications(){
		return notifications;
	}
	public void setNotifications(Vector<Notification> toSet){
		notifications=toSet;
	}
	
	public void setProjects(Vector<ToDoList> toSet){
		projects=toSet;
	}
	
	public Vector<Task> getTasks(){
		Vector<Task> tasks = new Vector<Task>();
		for(ToDoList td : projects) {
			for(Task t : td.getTasks()) {
				tasks.add(t);
			}
		}
		return tasks;
	}
	
	public Vector<ToDoList> getProjects(){
		return projects;
	}
	
	//mutators
	public void setUsername(String u1){
		username = u1;
	}
	
	public void setPassword(String p1){
		password = p1;
	}
	
	public void setFirstName(String n1){
		firstName = n1;
	}
	
	public void setLastName(String n2){
		lastName = n2;
	}
	
	public void addTask(Task t){
		for(ToDoList td : projects) {
			if(td.getName().equalsIgnoreCase(t.getParent())) {
				td.addTask(t);
			}
		}
	}
	
	public void addNotification(Notification n){
		notifications.add(n);
	}
	
	public ToDoList getListofTask(Task task){
		String toget = task.getParent();
		for(ToDoList list:projects){
			if(list.getName().equals(toget)){
				return list;
			}
		}
		return null;
	}
	
	public void removeTask(Task t){
		for(ToDoList td : projects) {
			if(td.getName().equalsIgnoreCase(t.getParent())) {
				td.removeTask(t);
			}
		}
	}
	
	public void removeNotification(Task t){
		notifications.remove(t);
	}
	
	public void addProject(ToDoList s){
		projects.add(s);
	}
	
	public void removeProject(String s){
		projects.remove(s);
	}
}