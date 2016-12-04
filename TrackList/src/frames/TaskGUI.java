package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Constants.AppearanceConstants;
import client.client;
import utilities.Task;
import utilities.UserAccount;

public class TaskGUI extends JPanel {
	private Task task;
	private JLabel taskInfo;
	private JButton dateLabel;
	private JButton timeLabel;
	private JCheckBox checkBox;
	
	private boolean timerMode;
	
	private JPanel deadlinePanel;
	private JPanel eastPanel;
	private String time;
	private boolean pm;

	
	private final static Color taskColor = Color.WHITE;
	private final static Color borderColor = Color.BLACK;
	private final static Color backColor = Color.WHITE;
	
	private client client;
	
	public TaskGUI(Task task, client client) {
		timerMode = false;
		this.client = client;
		this.task = task;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables() {
		taskInfo = new JLabel(task.getTask());
		timeLabel = new JButton();
		if (task.isCompleted()) {
			taskInfo.setForeground(Color.RED);
		}
		dateLabel = new JButton(task.getDeadlineMonth() + "/" + task.getDeadlineDay() + "/" + task.getDeadlineYear());
		settingTimeLabel();
		checkBox = new JCheckBox();
		
		deadlinePanel = new JPanel();
		eastPanel = new JPanel();
	}
	
	private void settingTimeLabel() {
		pm = false;
		time = "";
		if (task.getDeadlineHour() <= 12) {
			time += task.getDeadlineHour();
		} else {
			time += (task.getDeadlineHour() -12);
			pm = true;
		}
		
		time +=":";
		
		if (task.getDeadlineMinute()<10) {
			time += "0" + task.getDeadlineMinute();
		} else {
			time += task.getDeadlineMinute();
		}
		
		if (pm) {
			time += "PM";
		} else {
			time += "AM";
		}
		timeLabel.setText(time);
	}
	
	public void setTimerMode() {
		if (task.isCompleted()) {
			return;
		}
		
		Calendar timeNow = Calendar.getInstance();
		Calendar deadline = Calendar.getInstance();
		deadline.clear();
		deadline.set(task.getDeadlineYear(), task.getDeadlineMonth()-1, task.getDeadlineDay(), 
				task.getDeadlineHour(), task.getDeadlineMinute());
		
		System.out.println(deadline.getTimeInMillis());
		System.out.println(timeNow.getTimeInMillis());
		long unixTime = deadline.getTimeInMillis() - timeNow.getTimeInMillis();
		
		long days = TimeUnit.MILLISECONDS.toDays(unixTime);
		long hours = TimeUnit.MILLISECONDS.toHours(unixTime) - days*24;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(unixTime) - hours*60 - days*24*60;
		//long days = (long) (unixTime/60.0/60.0/24.0);
		//long hours = (long) (unixTime/60.0/60.0);
		//long minutes = (long) (unixTime/60.0);
		

		dateLabel.setText("");
		timeLabel.setText(days + " days "+hours + " hours " + minutes + " minutes left");
		repaint();
		revalidate();
	}
	
	public void turnOffTimerMode() {
		dateLabel.setText(task.getDeadlineMonth() + "/" + task.getDeadlineDay() + "/" + task.getDeadlineYear());
		settingTimeLabel();
		repaint();
		revalidate();
	}
	
	
	private void createGUI() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(200, 60));
		setPreferredSize(new Dimension(400, 60));
		setMaximumSize(new Dimension(1000, 60));
		setBackground(taskColor);
		//setBorder(BorderFactory.createLineBorder(borderColor, 3));
		
		Border blackLine = BorderFactory.createLineBorder(borderColor, 3);
		Border topBottomLine = BorderFactory.createMatteBorder(3, 0, 3, 0, backColor);
		setBorder(BorderFactory.createCompoundBorder(topBottomLine, blackLine));
		
		//task info text field settings
		taskInfo.setBackground(taskColor);
		//taskInfo.setEditable(false);
		//taskInfo.setWrapStyleWord(true);
		//taskInfo.setLineWrap(true);
		taskInfo.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, taskColor));
		taskInfo.setFont(AppearanceConstants.fontSmallest);
		
		
		
		//setting deadline info
		dateLabel.setBackground(taskColor);
		dateLabel.setBorderPainted(false);
		dateLabel.setFont(AppearanceConstants.fontSmallest);
		//dateLabel.setPreferredSize(new Dimension(50,10));
		timeLabel.setBackground(taskColor);
		timeLabel.setBorderPainted(false);
		timeLabel.setFont(AppearanceConstants.fontSmallest);
		//timeLabel.setPreferredSize(new Dimension(60,10));
		
		//setting deadline panel
		deadlinePanel.setBackground(taskColor);
		deadlinePanel.setLayout(new BoxLayout(deadlinePanel, BoxLayout.X_AXIS));
		deadlinePanel.add(dateLabel);
		deadlinePanel.add(timeLabel);
		
		//setting east panel
		eastPanel.setBackground(taskColor);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
		eastPanel.add(deadlinePanel);
		eastPanel.add(checkBox);
		//eastPanel.setPreferredSize(new Dimension(250,60));
		
		//setting the taskGUI panel
		add(taskInfo, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
		taskCompleted();
	}
	
	public void taskCompleted() {
		if(task.isCompleted()) {
			checkBox.setSelected(true);
			checkBox.setEnabled(false);
			taskInfo.setForeground(Color.RED);
		}
	}
	
	private void addActionListeners() {
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Finished task " + task.getTask());
				client.finishTask(task);
			}
			
		});
	}
	
	public static void main(String [] args) {
		/*
		JFrame test = new JFrame();
		test.setLocation(0,0);
		test.setSize(1000,300);
		JPanel temp = new JPanel();
		temp.add(new TaskGUI(new Task("test test test test test test test", "list", 2016, 11, 15, 14, 0, new UserAccount("u", "p", "f", "l")), null));
		test.add(temp, BorderLayout.NORTH);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		
		
	}
}
