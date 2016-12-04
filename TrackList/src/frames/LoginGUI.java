package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import listeners.TextFieldFocusListener;
import Constants.AppearanceConstants;
import Constants.AppearanceSettings;
import client.client;
import utilities.UserAccount;

public class LoginGUI extends JFrame{
	
	private static final long serialVersionUID = 3061669155424987616L;
	private final static Color backgroundColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.WHITE;
	
	private JButton loginButton;
	private JButton createAccount;
	private JButton guestButton;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton welcomeLabel;

	private JPanel buttonPane;
	private JPanel inputPane;
	private client c;
	
	public LoginGUI(client c) {
		super("Login");
		this.c = c;
		setSize(500,500);
		setLocation(0,0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	private boolean isAllFieldsFilled() {
		if (usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	private void initializeComponents(){
		welcomeLabel = new JButton("Welcome");
		loginButton = new JButton("Login");
		createAccount = new JButton("Don't have an account? Sign up here!");
		guestButton = new JButton("Login As Guest");
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		
		buttonPane = new JPanel();
		inputPane = new JPanel();
	}
	
	private void createGUI(){
		
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, loginButton, 
				guestButton, usernameField, passwordField, usernameLabel, passwordLabel);
		createAccount.setFont(AppearanceConstants.fontSmallest);
		welcomeLabel.setFont(AppearanceConstants.fontLargest);
		welcomeLabel.setBorderPainted(false);
		welcomeLabel.setForeground(Color.YELLOW);
		createAccount.setForeground(fontColor);
		createAccount.setBackground(backgroundColor);
		createAccount.setOpaque(true);
		usernameLabel.setForeground(fontColor);
		passwordLabel.setForeground(fontColor);
		
		
		createButtonPanel();
		createCenterPanel();
	}
	
	private void createButtonPanel() {
		AppearanceSettings.setOpaque(guestButton, loginButton, createAccount);
		AppearanceSettings.setSize(160, 40, loginButton, guestButton);
		createAccount.setBorderPainted(false);
		
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		center.setBackground(backgroundColor);
		south.setBackground(backgroundColor);
		
		center.add(loginButton);
		center.add(guestButton);
		south.add(createAccount);
		
		guestButton.setBackground(backgroundColor);
		loginButton.setBackground(backgroundColor);
		
		buttonPane.setLayout(new BorderLayout());
		buttonPane.add(center, BorderLayout.CENTER);
		buttonPane.add(south, BorderLayout.SOUTH);
		buttonPane.setBackground(backgroundColor);
		
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	private void createCenterPanel() {
		JPanel usernameFieldPane = new JPanel();
		JPanel passwordFieldPane = new JPanel();
		
		AppearanceSettings.setSize(250, 40, usernameField, passwordField);
		
		usernameFieldPane.add(usernameLabel);
		usernameFieldPane.add(usernameField);
		usernameFieldPane.setBackground(backgroundColor);
		
		passwordFieldPane.add(passwordLabel);
		passwordFieldPane.add(passwordField);
		passwordFieldPane.setBackground(backgroundColor);
		
		inputPane.setLayout(new GridLayout(3,1));
		
		inputPane.add(welcomeLabel);
		inputPane.add(usernameFieldPane);
		inputPane.add(passwordFieldPane);
		inputPane.setBackground(backgroundColor);
		
		add(inputPane, BorderLayout.CENTER);
	}
	
	private void addListeners(){
		
		loginButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAllFieldsFilled()) {
					JOptionPane.showMessageDialog(LoginGUI.this, "Please fill in all fields");
					return;
				}
				c.login(new UserAccount(usernameField.getText().trim(), new String(passwordField.getPassword()), "", ""));
			}
		});
		
		guestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c.guestlogin();
			}
			
		});
		
		createAccount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateAccountGUI(c).setVisible(true);
				dispose();
			}

			
		});
	}
}