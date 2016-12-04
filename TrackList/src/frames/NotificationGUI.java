package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import utilities.Notification;
import utilities.Task;
import utilities.UserAccount;

public class NotificationGUI extends JPanel {
	private Notification notification;
	private JLabel notificationInfo;

	private JPanel eastPanel;
	
	private final static Color taskColor = Color.WHITE;
	private final static Color borderColor = Color.BLACK;
	private final static Color backColor = Color.WHITE;
	
	
	public NotificationGUI(Notification notification) {
		this.notification = notification;
		initializeVariables();
		createGUI();
	}
	
	private void initializeVariables() {
		notificationInfo = new JLabel(notification.getMessage());
	}
	
	private void createGUI() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(200, 60));
		setPreferredSize(new Dimension(700, 60));
		setMaximumSize(new Dimension(1000,60));
		setBackground(taskColor);
		
		Border blackLine = BorderFactory.createLineBorder(borderColor, 3);
		Border topBottomLine = BorderFactory.createMatteBorder(3, 0, 3, 0, backColor);
		setBorder(BorderFactory.createCompoundBorder(topBottomLine, blackLine));
		
		//task info text field settings
		notificationInfo.setBackground(taskColor);
		notificationInfo.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, taskColor));
		notificationInfo.setFont(AppearanceConstants.fontSmallest);
		
		add(notificationInfo);
	}
	
	public void setNotificationUnread() {
		notificationInfo.setForeground(Color.RED);
	}
	
	public void setNotificationRead() {
		notificationInfo.setForeground(Color.BLACK);
	}
	
	public static void main(String [] args) {
		JFrame test = new JFrame();
		test.setLocation(0,0);
		test.setSize(1000,300);
		JPanel temp = new JPanel();
		temp.add(new NotificationGUI(new Notification("test test test test test test test", false, null, 0)));
		test.add(temp, BorderLayout.NORTH);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
