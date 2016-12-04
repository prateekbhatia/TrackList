package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Constants.AppearanceConstants;
import client.client;
import utilities.Notification;
import utilities.ToDoList;
import utilities.UserAccount;

public class MenuGUI extends JPanel
{
	private HashMap<String, JPanel> listPanels;
	private HashMap<String, MenuButton> map;
	private JButton addButton;
	private JPanel centerPanel;
	private MenuButton selectedList;
	private client client;
	private UserAccount user;
	private Vector<ToDoList> toDoLists;
	private Vector<Notification> notifications;
	private MenuButton currentToggled;
	
	private ToDoListMainGUI mainGUI;
	
	private final static Color backgroundColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.WHITE;
	
	public MenuGUI(ToDoListMainGUI mainGUI, UserAccount user, client client)
	{
		this.user = user;
		this.client = client;
		this.toDoLists = user.getProjects();
		this.notifications = user.getNotifications();
		this.mainGUI = mainGUI;
		initializeComponents();
		setListPanelMap();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(backgroundColor);
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		this.setLayout(new BorderLayout());
		
		createGUI();
		addActionListener();
		setListsInMenu();
		updateWorklist();
		
		int unread = 0;
		MenuButton note = map.get("Notifications");
		for (int i=0; i<notifications.size(); i++) 
		{
			if (!notifications.get(i).getRead()) {
				unread++;
			}
		}
		
		if (unread != 0) {
			note.setToggle();
		} else {
			note.resetToggle();
		}
	}
	
	protected void updateWorklist() {
		WorklistGUI worklist = (WorklistGUI) listPanels.get("My Worklist");
		worklist.clearList();
		
		
		for(int i=0; i<toDoLists.size(); i++) {
			if (toDoLists.get(i).getTasks().isEmpty()) {
				continue;
			}
			worklist.addTaskList(toDoLists.get(i).getTasksSorted());
		}
		worklist.setTimerMode();
		repaint();
		revalidate();
		
	}
	
	protected void updateNotificationList(Vector<Notification> notifications) {
		this.notifications = notifications;
		MenuButton button  = map.get("Notifications");
		button.setToggle();
		listPanels.put("Notifications", new NotificationListGUI(notifications));
	}
	
	protected void updateLists(Vector<ToDoList> toDoLists) {
		Vector<String> keys = new Vector<>();
		for (String key : listPanels.keySet()) {
			if (key.equals("Notifications") || key.equals("My Worklist")) {
				break;
			} else {
				keys.add(key);
			}
		}
		for (int i=0; i<keys.size(); i++) {
			listPanels.remove(keys.get(i));
		}
		
		this.toDoLists = toDoLists;
		updateWorklist();
		for(int i=0; i<toDoLists.size(); i++) {
			listPanels.put(toDoLists.get(i).getName(), new ListGUI(toDoLists.get(i), client, user));
		}
		
		for (int i=0; i<toDoLists.size(); i++) {
			removeList(toDoLists.get(i));
		}
		
		setListsInMenu();
		if (map.get(currentToggled.getButtonName()) != null) {
			toggleSelect(map.get(currentToggled.getButtonName()));
		} else {
			toggleSelect(map.get("My Worklist"));
		}
	}
	
	private void initializeComponents() {
		listPanels = new HashMap<>();
		map = new HashMap<String, MenuButton>();
		addButton = new JButton("+");
		centerPanel = new JPanel();
	}
	
	private void setListPanelMap() {
		listPanels.clear();
		listPanels.put("Notifications", new NotificationListGUI(notifications));
		listPanels.put("My Worklist", new WorklistGUI(client));
		for (int i=0; i<toDoLists.size(); i++) {
			listPanels.put(toDoLists.get(i).getName(), new ListGUI(toDoLists.get(i), client, user));
		}
	}
	
	private void setListsInMenu() {
		for (int i=0; i<toDoLists.size(); i++) {
			addList(toDoLists.get(i));
		}
	}
	
	private void createGUI()
	{
		addList(new ToDoList("Notifications","username", null));
		addList(new ToDoList("My Worklist", "username", null));
		toggleSelect(map.get("My Worklist"));
		currentToggled = map.get("My Worklist");
		add(centerPanel, BorderLayout.CENTER);
		add(addButton, BorderLayout.SOUTH);
	}
	
	public void addActionListener() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newList = JOptionPane.showInputDialog("Enter a new list to add");
				if (newList == null || newList.isEmpty()) {
					return;
				} else if(!newList.isEmpty()) {
					System.out.println("Adding list: " + newList);
					client.addList(new ToDoList(newList, user.getUsername(), user));
				}
			}
			
		});
	}
	
	public void updateTimer() {
		WorklistGUI worklist = (WorklistGUI) listPanels.get("My Worklist");
		worklist.updateTimer();
	}
	
	public void endTimer() {
		WorklistGUI worklist = (WorklistGUI) listPanels.get("My Worklist");
		worklist.endTimer();
	}
	
	public void addList(ToDoList list)
	{
		MenuButton button = new MenuButton(list);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				toggleSelect(button);
			}
		});
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_D) {
					//int chosen = JOptionPane.showConfirmDialog(parentComponent, message)
				}
			}
		});
		centerPanel.add(button);
		
		map.put(list.getName(), button);
		repaint();
		revalidate();
	}
	
	public void removeList(ToDoList list)
	{
		MenuButton menuList = map.get(list.getName());
		if(menuList == null)
			return;
		centerPanel.remove(map.get(list.getName()));
		map.remove(list.getName());
		revalidate();
		repaint();
	}
	
	protected void toggleSelect(MenuButton button)
	{
		if(selectedList != null)
			selectedList.setBackground(true);
		selectedList = button;
		selectedList.setBackground(false);
		
		currentToggled = button;
		if (button.getButtonName().equals("Notifications")) {
			if (notifications != null && !notifications.isEmpty()) {
				for (int i=0; i<notifications.size(); i++) {
					notifications.get(i).setRead();
				}
				client.readNotification(notifications);
				button.resetToggle();
			}
		}
		mainGUI.changePanel(listPanels.get(button.getButtonName()));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Vector<ToDoList> lists = new Vector<>();
		for(int i=0; i<5; i++) {
			lists.add(new ToDoList("List"+i, "username", null));
		}
		
		MenuGUI gui = new MenuGUI( null, null, null);
		frame.add(gui);
		frame.setSize(200, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	private class MenuButton extends JPanel
	{
		private ImageIcon icon;
		private ImageIcon iconFlagged;
		private JButton button;
		private JPanel iconPanel;
		private Boolean toggle;
		private ToDoList list;
		protected MenuButton(ToDoList listName)
		{
			setBackground(backgroundColor);
			setForeground(fontColor);
			list = listName;
			toggle = false;
			button = new JButton(listName.getName());
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					toggleSelect(MenuButton.this);
				}
			});
			icon = new ImageIcon("images/list.png");
			iconFlagged = new ImageIcon("images/toggled.png");
			iconPanel = new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					if(toggle)
						g.drawImage(iconFlagged.getImage(), 0, 0, 23, 23, null);
					else
						g.drawImage(icon.getImage(), 0, 0, 23, 23, null);
				}
			};
		
			this.setLayout(new BorderLayout());
			
			createGUI();
		}
		
		public String getButtonName() {
			return list.getName();
		}
		
		private void createGUI()
		{
			addButton.setBorderPainted(false);
			addButton.setBackground(Color.WHITE);
			addButton.setOpaque(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFont(AppearanceConstants.fontSmallest);
			button.setForeground(fontColor);
			
			Dimension size = new Dimension(25, 40);
			iconPanel.setPreferredSize(size);
			iconPanel.setMinimumSize(size);
			add(iconPanel, BorderLayout.WEST);
			add(button, BorderLayout.CENTER);
			this.setMaximumSize(new Dimension(250, (int)button.getPreferredSize().getHeight()));
		}
		
		public void setToggle()
		{
			toggle = true;
		}
		
		public void resetToggle() {
			toggle = false;
		}
		
		public void setBackground(Boolean backgroundFlag)
		{
			if(backgroundFlag)
				this.setBackground(backgroundColor);
			else
				this.setBackground(Color.GRAY);
		}
		
		public ToDoList getList()
		{
			return list;
		}
	}
	

}
