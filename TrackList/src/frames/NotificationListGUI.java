package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Constants.AppearanceConstants;
import client.client;
import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;

public class NotificationListGUI extends JPanel {
	private Vector<Notification> notifications;
	private Vector<NotificationGUI> notificationGUI;
	private JPanel listLabelContainer;
	private JPanel notificationsContainer;
	private client client;
	
	private final static Color topColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.YELLOW;
	private final static Color backgroundColor = Color.WHITE;
	
	private JLabel listLabel;
	private JScrollPane scrollPane;
	
	public NotificationListGUI(Vector<Notification> notifications) {
		this.notifications = notifications;
 		initializeVariables();
 		initializeNotificationGUI();
		createGUI();
	}
	

	private void initializeNotificationGUI() {
		for (int i=0; i<notifications.size(); i++) {
			NotificationGUI notification = new NotificationGUI(notifications.get(i));
			notificationGUI.add(notification);
		}
	}
	
	private void initializeVariables() {
		notificationGUI = new Vector<NotificationGUI>();
		listLabelContainer = new JPanel();
		notificationsContainer = new JPanel();
		scrollPane = new JScrollPane(notificationsContainer);
		listLabel = new JLabel("Notification");
	}
	
	private void createGUI() {
		
		listLabel.setFont(AppearanceConstants.fontLargest);
		listLabel.setForeground(fontColor);
		listLabel.setBackground(topColor);
		listLabel.setOpaque(true);
		
		listLabelContainer.setBackground(topColor);
		listLabelContainer.setOpaque(true);
		listLabelContainer.setLayout(new BorderLayout());
		listLabelContainer.add(listLabel, BorderLayout.CENTER);
		
		notificationsContainer.setBackground(backgroundColor);
		notificationsContainer.setOpaque(false);
		notificationsContainer.setLayout(new BoxLayout(notificationsContainer, BoxLayout.PAGE_AXIS));
		System.out.println("notificationGUI size: " + notificationGUI.size());
		for (int i=0; i<notifications.size(); i++) {
			if (!notifications.get(i).getRead()) {
				notificationsContainer.add(notificationGUI.get(i));
			}
		}
		
		scrollPane.setBorder(BorderFactory.createLineBorder(backgroundColor));
		scrollPane.setBackground(backgroundColor);
		scrollPane.setOpaque(true);
		scrollPane.setPreferredSize(new Dimension(750,640));
		scrollPane.getViewport().setBackground(backgroundColor);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(listLabelContainer);
		add(scrollPane);
	}
	
	public static void main(String [] args) {
		JFrame test = new JFrame();
		test.setLocation(0,0);
		test.setSize(1000,800);
		JPanel temp = new JPanel();
		Vector<Notification> notifications = new Vector<Notification>();
		for (int i=0; i<5; i++) {
			notifications.add(new Notification("read", null));
		}
		temp.add(new NotificationListGUI(notifications));
		test.add(new JPanel(), BorderLayout.WEST);
		test.add(temp, BorderLayout.NORTH);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
