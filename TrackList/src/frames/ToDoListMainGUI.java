package frames;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.client;
import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;
import utilities.UserAccount;

public class ToDoListMainGUI extends JFrame {
	
	private client client;
	private Vector<Notification> notifications;
	private Vector<ToDoList> toDoLists;
	private UserAccount user;
	private JPanel currentPanel;
	
	private MenuGUI menuGUI;
	
	public ToDoListMainGUI(UserAccount user, client client) {
		setLocation(0,0);
		setSize(900,600);
		notifications = user.getNotifications();
		toDoLists = user.getProjects();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuGUI = new MenuGUI(this, user, client);
		createGUI();
	}
	
	public void changePanel(JPanel panel) {
		if(currentPanel != null)
			remove(currentPanel);
		currentPanel = panel;
		add(currentPanel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	private void createGUI() {
		add(currentPanel, BorderLayout.CENTER);
		add(menuGUI, BorderLayout.WEST);
	}
	
	public void populateLists(Vector<ToDoList> lists) {
		toDoLists = lists;
		menuGUI.updateLists(lists);

	}
	
	public void endTimer() {
		menuGUI.endTimer();
	}
	
	public void updateTimer() {
		menuGUI.updateTimer();
	}
	
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void populateNotifications(Vector<Notification> notifications) {
		this.notifications = notifications;
		menuGUI.updateNotificationList(notifications);
	}
	
	public static void main(String [] args) {/*
		UserAccount user = new UserAccount("username", "password", "firstname", "lastname");
		Vector<ToDoList> toDoLists = new Vector<ToDoList>();
		for(int i=0; i< 5; i++) {
			toDoLists.add(new ToDoList("List" + i, "username", user));
			user.addProject(toDoLists.get(i));
		}
		for(int i=0; i<toDoLists.size(); i++) {
			for(int j=0; j<10; j++) {
				Task task = new Task("task" + j, toDoLists.get(i).getName(), 0,0,0,0,0, user);
				if (i == 0 && j == 0) {
					task.setCompleted();
				}
				user.addTask(task);
			}
		}
		for(int i=0; i<5; i++) {
			Notification notification = new Notification("User1 has shared list List1", user);
			if (i == 0) {
				notification.setRead();
			}
			user.addNotification(notification);
		}
		
		
		ToDoListMainGUI mainGUI = new ToDoListMainGUI(user, null);
		
		user.addTask(new Task("task" + 100, "List0", 0,0,0,0,0, user));
		user.addNotification(new Notification("new notification!", user));
		user.addProject(new ToDoList("Temp", "username", user));
		mainGUI.populateLists(user.getProjects());
		mainGUI.populateNotifications(user.getNotifications());
		mainGUI.setVisible(true);*/
	}
}
