package messages;

import java.util.Vector;

import utilities.Notification;


public class NotificationMessage extends Message{
	Vector<Notification> notifications;
	
	public NotificationMessage(Vector<Notification> notifications){
		super();
		this.notifications = notifications;
	}
	
	public Vector<Notification> getNotifications(){
		return notifications;
	}
}
