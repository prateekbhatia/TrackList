package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Constants.AppearanceConstants;

public class SharedListGUI extends JPanel {
	private Vector<JLabel> userLabels;
	private JList<String> userList;
	private JPanel userListPanel;
	private String currentUser;
	
	private final static Color topColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.YELLOW;
	private final static Color backgroundColor = Color.WHITE;
	
	public SharedListGUI(Vector<String> userList, String currentUser)
	{
		this.userLabels = new Vector<JLabel>();
		this.userListPanel = new JPanel();
		this.userList = new JList<String>();
		
		this.currentUser = currentUser;
		Vector<String> userList2 = new Vector<>();
		
		for (int i=0; i<userList.size(); i++) {
			if (!currentUser.equals(userList.get(i))) {
				userList2.add(userList.get(i));
			}
		}
		
		createGUI();
		setUsers(userList2);
	}
	
	private void createGUI()
	{
		setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
		userList.setFont(AppearanceConstants.fontSmall);
		this.setBackground(topColor);
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("Shared with...");
		title.setForeground(Color.WHITE);
		title.setFont (AppearanceConstants.fontLarge);
		this.add(title, BorderLayout.NORTH);
		this.add(this.userList, BorderLayout.CENTER);
	}
	
	public void setUsers(Vector<String> userList)
	{
		this.userList.removeAll();
	 	this.userList.setListData(userList);
	 	this.userList.setBackground(topColor);
	 	this.userList.setForeground(Color.WHITE);
		
		this.repaint();
		this.revalidate();
	}

	public static void main(String[] args)
	{
		Vector<String> users = new Vector<String>();
		users.add("Me");
		users.add("MySelf");
		users.add("I");
		JFrame frame = new JFrame();
		frame.add(new SharedListGUI(users, "Me"));
		frame.setSize(200, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
