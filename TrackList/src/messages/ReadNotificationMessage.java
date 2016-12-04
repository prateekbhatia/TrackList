package messages;

import utilities.Notification;

public class ReadNotificationMessage extends Message{
	private Notification notification;
	
	public ReadNotificationMessage(Notification notification){
		super();
		this.notification = notification;
	}
	
	public Notification getNotification(){
		return this.notification;
	}
}
