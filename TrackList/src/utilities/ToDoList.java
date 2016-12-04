package utilities;


import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;


public class ToDoList implements Serializable {
	//name of the ToDoList project
	private String name;
	//set of Tasks in the ToDoList project
	private Vector<Task> tasks; 
	//UserAccounts (denoted by username only) that are a part of the ToDoList project
	private Vector<String> assignees; 
	
	public ToDoList(String name, String username, UserAccount a){
		this.name = name;
		tasks = new Vector<Task>();
		assignees = new Vector<String>();
		assignees.add(username);
	}
	
	public PriorityQueue<Task> getCompletedQueue() {
		Comparator<Task> comparator = new TaskComp();
		PriorityQueue<Task> priorityQueue = new PriorityQueue<Task>(tasks.size() > 0 ? tasks.size() : 10, comparator);
		for (int i=0; i< tasks.size(); i++) {
			if (tasks.get(i).isCompleted())
				priorityQueue.add(tasks.get(i));
		}
		return priorityQueue;
	}
	
	public PriorityQueue<Task> getIncompletedQueue() {
		Comparator<Task> comparator = new TaskComp();
		PriorityQueue<Task> priorityQueue = new PriorityQueue<Task>(tasks.size() > 0 ? tasks.size() : 10, comparator);
		for (int i=0; i< tasks.size(); i++) {
			if (!tasks.get(i).isCompleted())
				priorityQueue.add(tasks.get(i));
		}
		return priorityQueue;
	}
	
	public Vector<Task> getTasksSorted() {
		PriorityQueue<Task> completed = getCompletedQueue();
		PriorityQueue<Task> incompleted = getIncompletedQueue();
		
		Vector<Task> tasks = new Vector<Task>();
		while (!incompleted.isEmpty()) {
			tasks.add(incompleted.poll());
		}
		while (!completed.isEmpty()) {
			tasks.add(completed.poll());
		}
		
		return tasks;
	}
	
	//getters
	public Vector<Task> getTasks(){
		return tasks;
	}
	
	public Vector<String> getAssignees(){
		return assignees;
	}
	
	public String getAssigner(){
		return assignees.get(0);
	}
	
	public String getUsername(){
		return assignees.get(0);
	}
	
	public String getName(){
		return name;
	}
	
	//mutators
	public void addAssignee(String u){
		assignees.add(u);
	}
	
	public void addTask(Task t){
		tasks.add(t);
	}
	
	public void removeTask(Task t){
		tasks.remove(t);
	}
	
	public void setName(String s){
		name = s;
	}
	
	private class TaskComp implements Comparator<Task> {
		@Override
		public int compare(Task o1, Task o2) {
			if(o1.getUnixTime() < o2.getUnixTime())
				return -1;
			else if(o1.getUnixTime() > o2.getUnixTime())
				return 1;
			else
				return 0;
		}
	}
}


