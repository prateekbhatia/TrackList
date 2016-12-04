package messages;

import java.util.Vector;

import utilities.ToDoList;

public class AddListMessage extends Message{
	private ToDoList mList;
	private Vector<ToDoList> mToDoLists;
	public AddListMessage(ToDoList list){
		super();
		mList =list;
	}
	public void setUserLists(Vector<ToDoList> toSet){
		mToDoLists=toSet;
	}
	public ToDoList getToDoList(){
		return mList;
	}
	public void setToDoList(ToDoList list){
		mList =list;
	}
	public Vector<ToDoList> getUserLists(){
		return mToDoLists;
	}
}
