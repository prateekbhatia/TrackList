package utilities;



import java.io.Serializable;
import java.util.Vector;


public class ToDoListMembers implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//set of UserAccounts associated with the ToDoList with name projectName
	private Vector<UserAccount> members;
	//name of the ToDoList that 'members' are a part of
	private String projectName;
	
	public ToDoListMembers(String pName){
		projectName = pName;
		members = new Vector<UserAccount>();
	}
	
	//getters
	public Vector<UserAccount> getMembers(){
		return members;
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	//mutators
	public void addMember(UserAccount a){
		members.add(a);
	}
	
	public void removeMember(UserAccount b){
		members.remove(b);
	}
	
	public void setProjectName(String nName){
		projectName = nName;
	}
}




