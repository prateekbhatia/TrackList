package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import Database.DatabaseFunctions;
import messages.Message;
import messages.NotificationMessage;
import utilities.Notification;
import utilities.UserAccount;
import messages.Message;

public class Server extends Thread{
	private ServerSocket ss;
	private Vector<ServerThread> serverThreads;
	private DatabaseFunctions dbf;
	
	public Server(int port){
		try {
			ss = new ServerSocket(port);
			serverThreads = new Vector<ServerThread>();
			dbf = new DatabaseFunctions();
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			System.out.println("Running");
			while(true){
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s, this, dbf);
				serverThreads.addElement(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyUser(UserAccount ua, Vector<Notification> notifications){
		for(ServerThread st : serverThreads){
			if(st == null){
				System.out.println("st == null");
			} else if(st.getUser() == null){
				System.out.println("st.getUser() == null");
			} else if(st.getUser().getUsername() == null){
				System.out.println("st.getUser().getUsername() == null");
			}
			if(ua == null){
				System.out.println("ua == null");
			} else if(ua.getUsername() == null){
				System.out.println("ua.getUsername() == null");
			}
			if(st.getUser().getUsername().equals(ua.getUsername())){
				NotificationMessage notificationMessage = new NotificationMessage(notifications);
				notificationMessage.setSuccess(true);
				st.sendMessage(notificationMessage);
			}
		}
	}
	
	public void sendToActiveUsers(UserAccount ua, Message message){
		for(ServerThread st : serverThreads){
			/*if(st.getUser() == null){
				System.out.println("st.getUser() == null");
			}
			else if(st.getUser().getUsername() == null){
				System.out.println("st.getUser().getUsername() == null");
			}
			if(ua == null){
				System.out.println("ua == null");
			}
			else if(ua.getUsername() == null){
				System.out.println("ua.getUsername() == null");
			}*/
			if(st.getUser().getUsername().equals(ua.getUsername())){
				st.sendMessage(message);
			}
		}
	}
	
	public void removeThread(ServerThread thread){
		serverThreads.remove(thread);
		
		/*for(int i=0; i<serverThreads.size(); i++){
			if(serverThreads.get(i) == thread){
				serverThreads.remove(i);
				break;
			}
		}*/
	}
}
