package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Constants.AppearanceConstants;
import client.client;
import utilities.Task;

public class WorklistGUI extends JPanel
{
	private HashMap<String, ToDoListGUI> map;
	private JCheckBox timeModeCheck;
	private client client;
	private JPanel centerPanel;
	private JScrollPane scrollPane;
	private JPanel timeLeftPanel;
	private JButton timeLeftLabel;
	private int time;
	private int seconds;
	
	private final static Color topColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.YELLOW;
	private final static Color backgroundColor = Color.WHITE;
	
	public WorklistGUI(client client)
	{
		this.client = client;
		centerPanel = new JPanel();
		scrollPane = new JScrollPane(centerPanel);
		scrollPane.getViewport().setBackground(backgroundColor);
		map = new HashMap<String, ToDoListGUI>();
		timeLeftPanel = new JPanel();
		timeLeftLabel = new JButton();
		timeModeCheck = new JCheckBox("Timer Mode");
		timeModeCheck.setForeground(fontColor);
		timeModeCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Checkbox is : " + timeModeCheck.isSelected());
			}
		});
		createGUI();
		addActionListeners();
	}
	
	public void updateTimer() {
		seconds--;
		int minuteLeft = seconds / 60;
		int secondsLeft = seconds % 60;
		if (secondsLeft < 10) {
			timeLeftLabel.setText(minuteLeft + ":0" + secondsLeft);
		} else {
			timeLeftLabel.setText(minuteLeft + ":" + secondsLeft);
		}
		
	}
	
	public void endTimer() {
		timeLeftLabel.setText("");
		timeModeCheck.setSelected(false);
		JOptionPane.showMessageDialog(this, "Timer mode has been finished!");
		for (ToDoListGUI value : map.values()) {
			HashMap<String, TaskGUI> taskMap = value.getTasks();
			for (TaskGUI task : taskMap.values()) {
				task.turnOffTimerMode();
			}
		}
	}
	
	public void stopTimer() {
		timeLeftLabel.setText("");
		timeModeCheck.setSelected(false);
		client.stopTimer();
		for (ToDoListGUI value : map.values()) {
			HashMap<String, TaskGUI> taskMap = value.getTasks();
			for (TaskGUI task : taskMap.values()) {
				task.turnOffTimerMode();
			}
		}
	}
	
	public void setTimerMode() {
		for (ToDoListGUI value : map.values()) {
			HashMap<String, TaskGUI> taskMap = value.getTasks();
			for (TaskGUI task : taskMap.values()) {
				if (timeModeCheck.isSelected()) {
					task.setTimerMode();
				} else {
					task.turnOffTimerMode();
				}
			}
		}
	}
	
	
	private void addActionListeners() {
		timeModeCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (timeModeCheck.isSelected()) {
					String t = JOptionPane.showInputDialog("Enter a time for the timer mode");
					if (t == null || t.isEmpty()) {
						timeModeCheck.setSelected(false);
						return;
					} else {
						try {
							time = Integer.parseInt(t);
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(WorklistGUI.this, "Please enter a number");
							timeModeCheck.setSelected(false);
							return;
						}
						seconds = time*60;
						int minutesLeft = seconds / 60;
						int secondsLeft = seconds % 60;
						if (secondsLeft < 10) {
							timeLeftLabel.setText(minutesLeft + ":0" + secondsLeft);
						} else {
							timeLeftLabel.setText(minutesLeft + ":" + secondsLeft);
						}
						
						client.startTimeMode(time);
					}
				} else {
					stopTimer();
				}
				for (ToDoListGUI value : map.values()) {
					HashMap<String, TaskGUI> taskMap = value.getTasks();
					for (TaskGUI task : taskMap.values()) {
						if (timeModeCheck.isSelected()) {
							task.setTimerMode();
						} else {
							task.turnOffTimerMode();
						}
					}
				}
			}
			
		});
	}
	
	private void createGUI()
	{
		timeLeftPanel.setBackground(topColor);
		timeLeftLabel.setBorderPainted(false);
		timeLeftLabel.setOpaque(true);
		timeLeftLabel.setForeground(fontColor);
		timeLeftLabel.setFont(AppearanceConstants.fontLarger);
		timeLeftPanel.setLayout(new BorderLayout());
		timeLeftLabel.setBackground(topColor);
		timeLeftPanel.add(timeLeftLabel, BorderLayout.CENTER);
		setLayout(new BorderLayout());
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(backgroundColor);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		JLabel title = new JLabel("My Worklist");
		title.setForeground(fontColor);
		title.setFont(AppearanceConstants.fontLargest);
		northPanel.add(title, BorderLayout.CENTER);
		northPanel.add(timeModeCheck, BorderLayout.EAST);
		northPanel.setBackground(topColor);
		northPanel.add(timeLeftPanel, BorderLayout.SOUTH);
		add(northPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void addTaskList(Task task)
	{
		ToDoListGUI parent = map.get(task.getParent());
		if(parent == null)
		{
			parent = new ToDoListGUI(task.getParent());
			map.put(task.getParent(), parent);
			centerPanel.add(parent);
		}
		parent.addTask(task);
	}
	
	public void addTaskList(Vector<Task> taskList)
	{
		for(Task task : taskList)
		{
			addTaskList(task);
		}
	}
	
	public void clearList()
	{
		centerPanel.removeAll();
		map.clear();
		repaint();
		revalidate();
	}
	
	private class ToDoListGUI extends JPanel
	{
		private String title;
		private JPanel centerPanel;
		private HashMap<String, TaskGUI> map;
		public ToDoListGUI(String title)
		{
			this.title = title;
			centerPanel = new JPanel();
			map = new HashMap<String, TaskGUI>();
			
			createGUI();
		}
		
		public HashMap<String, TaskGUI> getTasks() {
			return map;
		}
		
		
		private void createGUI()
		{
			setLayout(new BorderLayout());
			setBackground(backgroundColor);
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
			JLabel titleLabel = new JLabel(this.title);
			titleLabel.setFont(AppearanceConstants.fontLarge);
			add(titleLabel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
		}
		
		public void addTask(Task task)
		{
			TaskGUI taskGUI = new TaskGUI(task, client);
			map.put(task.getParent()+task.getTask(), taskGUI);
			centerPanel.add(taskGUI);
		}
	
	}
	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		WorklistGUI work = new WorklistGUI(null);
		frame.add(work);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
