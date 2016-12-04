package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Constants.AppearanceConstants;
import client.client;
import utilities.Task;
import utilities.ToDoList;
import utilities.UserAccount;

public class ListGUI extends JPanel {

	
	private final static Color topColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.YELLOW;
	private final static Color backgroundColor = Color.WHITE;
	
	private ToDoList list;
	private JLabel listLabel;
	private UserTaskInputGUI inputGUI;
	private JButton shareButton;
	
	private JPanel listLabelContainer;
	private JPanel taskGUIContainer;
	private client client;
	private UserAccount user;
	
	private JScrollPane scrollPane;
	private JPanel gap;
	private JPanel centerPanel;
	private SharedListGUI sharedGUI;
	
	private Vector<TaskGUI> tasks;
	
	//private SharedListGUI sharedList;
	
	public ListGUI(ToDoList list, client client, UserAccount user) {
		this.list = list;
		this.client = client;
		this.user = user;
		setBackground(backgroundColor);
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	

	
	private void initializeTaskGUI() {
		tasks = new Vector<TaskGUI>();
		PriorityQueue<Task> incompletedTasks = list.getIncompletedQueue();
		PriorityQueue<Task> completedTasks = list.getCompletedQueue();
		while(!incompletedTasks.isEmpty()) {
			TaskGUI taskGUI = new TaskGUI(incompletedTasks.poll(), client);
			tasks.addElement(taskGUI);
		}
		while (!completedTasks.isEmpty()) {
			TaskGUI taskGUI = new TaskGUI(completedTasks.poll(), client);
			tasks.addElement(taskGUI);
		}
		
	}
	
	private void initializeVariables() {
		sharedGUI = new SharedListGUI(list.getAssignees(), user.getUsername());
		centerPanel = new JPanel();
		listLabel = new JLabel(list.getName());
		inputGUI = new UserTaskInputGUI(client, list.getName(), user, list.getAssigner());
		shareButton = new JButton(new ImageIcon("images/share.png"));
		
		listLabelContainer = new JPanel();
		taskGUIContainer = new JPanel();
		scrollPane = new JScrollPane(taskGUIContainer);
		gap = new JPanel();
		initializeTaskGUI();
	}
	
	private void createGUI() {
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		
		gap.setBackground(topColor);
		
		listLabel.setFont(AppearanceConstants.fontLargest);
		listLabel.setBackground(topColor);
		listLabel.setForeground(fontColor);
		listLabel.setOpaque(true);
		
		shareButton.setFont(AppearanceConstants.fontSmall);
		shareButton.setBackground(topColor);
		shareButton.setOpaque(true);
		shareButton.setBorderPainted(false);
		
		listLabelContainer.setBackground(topColor);
		listLabelContainer.setOpaque(true);
		listLabelContainer.setLayout(new BorderLayout());
		listLabelContainer.add(listLabel, BorderLayout.CENTER);
		listLabelContainer.add(shareButton, BorderLayout.EAST);
		
		
		taskGUIContainer.setBackground(backgroundColor);
		taskGUIContainer.setOpaque(false);
		taskGUIContainer.setLayout(new BoxLayout(taskGUIContainer, BoxLayout.PAGE_AXIS));
		for (int i=0; i<tasks.size(); i++) {
			taskGUIContainer.add(tasks.get(i));
		}
		
		scrollPane.setBorder(BorderFactory.createLineBorder(backgroundColor));
		scrollPane.getViewport().setBackground(backgroundColor);
		scrollPane.setBackground(backgroundColor);
		scrollPane.setOpaque(true);
		scrollPane.setPreferredSize(new Dimension(750,640));

		
		centerPanel.add(listLabelContainer);
		centerPanel.add(inputGUI);
		centerPanel.add(gap);
		centerPanel.add(scrollPane);
		
		setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);
		add(sharedGUI, BorderLayout.EAST);
	}
	
	private void addActionListeners() {
		shareButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = JOptionPane.showInputDialog("Enter a name to share this list with");
				if (username != null && !username.isEmpty()) {
					System.out.println("Adding user to list: " + username);
					client.addSharedUser(list, username);
				}
			}
			
		});
	}
	
	public static void main(String [] args) {
	}
}
