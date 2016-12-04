package utilities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//task contains the literal task message/statement
	//parentList holds the name of the ToDoList that the Task is associated with
	public String task, parentList, listOwner; 
	//int variables specify the deadline by which the Task is to be completed
	public int deadlineYear, deadlineMonth, deadlineDay, deadlineHour, deadlineMinute;
	public boolean completed; //flag variable indicating whether the Task is completed or not
	public UserAccount assigner; //UserAccount that creates/assigns the Task in the project
	
	public Task(String t, String p, String owner, int dy, int dm, int dd, int dh, int dm2, UserAccount ua){
		task = t;
		parentList = p;
		listOwner = owner;
		deadlineYear = dy;
		deadlineMonth = dm;
		deadlineDay = dd;
		deadlineHour = dh;
		deadlineMinute = dm2;
		assigner = ua;
		completed = false;
	}
	
	public long getUnixTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(deadlineYear, deadlineMonth-1, deadlineDay, deadlineHour, deadlineMinute);
		
		return calendar.getTimeInMillis();
	}
	
	//getters
	public UserAccount getAssigner() {
		return assigner;
	}
	
	public boolean isCompleted(){
		return completed;
	}
	
	public String getTask(){
		return task;
	}
	
	public String getParent(){
		return parentList;
	}
	
	public int getDeadlineYear(){
		return deadlineYear;
	}
	
	public int getDeadlineMonth(){
		return deadlineMonth;
	}
	
	public int getDeadlineDay(){
		return deadlineDay;
	}
	
	public int getDeadlineHour(){
		return deadlineHour;
	}
	
	public int getDeadlineMinute(){
		return deadlineMinute;
	}
	
	public String getOwner() {
		return listOwner;
	}
	
	//mutators
	public void setCompleted(){
		completed = true;
	}
	
	public void setDeadlineYear(int x){
		deadlineYear = x;
	}
	
	public void setDeadlineMonth(int x){
		deadlineMonth = x;
	}
	
	public void setDeadlineDay(int x){
		deadlineDay = x;
	}
	
	public void setDeadlineHour(int x){
		deadlineHour = x;
	}
	
	public void setDeadlineMinute(int x){
		deadlineMinute = x;
	}
}


