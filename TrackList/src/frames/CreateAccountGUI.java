package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Constants.AppearanceConstants;
import Constants.AppearanceSettings;
import client.client;
import utilities.UserAccount;

public class CreateAccountGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8658733988265363278L;
	private final static Color backgroundColor = new Color(121, 28, 22).brighter();
	private final static Color fontColor = Color.WHITE;

	private JButton createAccount;
	private JButton createAccountLabel;
	private JButton alreadyHaveAnAccount;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	
	private JPanel[] fieldPanes;
	private JPanel fieldPanel;
	private JPanel buttonPanel;
	private JPanel introPanel;
	private JPanel createAccountPanel;
	private client c;
	
	
	public CreateAccountGUI(client c) {
		super("Create Account");
		this.c = c;
		c.createAccount(this);
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
	
	
	private void initializeComponents() {
		createAccount = new JButton("Create Account");
		alreadyHaveAnAccount = new JButton("Already have an account?");
		createAccountLabel = new JButton("Create Account");	
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		firstNameLabel = new JLabel("First Name:");
		lastNameLabel = new JLabel("Last Name:");
		
		fieldPanel = new JPanel();
		buttonPanel = new JPanel();
		introPanel = new JPanel();
		createAccountPanel = new JPanel();
		
		fieldPanes = new JPanel[4];
		for (int i=0; i<4; i++) {
			fieldPanes[i] = new JPanel();
		}
		
	}
	
	private void createGUI() {
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, createAccount, usernameLabel, 
				passwordLabel, firstNameLabel, lastNameLabel);
		AppearanceSettings.setSize(250, 40, usernameField, passwordField, firstNameField, lastNameField);
		AppearanceSettings.setOpaque(createAccount, alreadyHaveAnAccount);
		AppearanceSettings.setBackground(backgroundColor, createAccount, alreadyHaveAnAccount,
				firstNameLabel, lastNameLabel, usernameLabel, passwordLabel,
				fieldPanes[0], fieldPanes[1], fieldPanes[2], fieldPanes[3]);
		AppearanceSettings.setForeground(fontColor, firstNameLabel, lastNameLabel, usernameLabel,
				passwordLabel, alreadyHaveAnAccount);
		AppearanceSettings.setSize(200, 40, createAccount);
		AppearanceSettings.setSize(400, 40, alreadyHaveAnAccount);
		createAccountLabel.setFont(AppearanceConstants.fontLargest);
		createAccountLabel.setBorderPainted(false);
		createAccountLabel.setForeground(Color.YELLOW);
		alreadyHaveAnAccount.setBorderPainted(false);
		alreadyHaveAnAccount.setFont(AppearanceConstants.fontSmallest);
		
		
		fieldPanes[0].add(firstNameLabel);
		fieldPanes[0].add(firstNameField);
		
		fieldPanes[1].add(lastNameLabel);
		fieldPanes[1].add(lastNameField);
		
		fieldPanes[2].add(usernameLabel);
		fieldPanes[2].add(usernameField);
		
		fieldPanes[3].add(passwordLabel);
		fieldPanes[3].add(passwordField);
		
		fieldPanel.setLayout(new GridLayout(4,1));
		for(int i=0; i<4; i++) {
			fieldPanel.add(fieldPanes[i]);
		}
		
		createAccountPanel.add(createAccount);
		createAccountPanel.setBackground(backgroundColor);
		
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(createAccountPanel, BorderLayout.CENTER);
		buttonPanel.add(alreadyHaveAnAccount, BorderLayout.SOUTH);
		buttonPanel.setBackground(backgroundColor);
		
		introPanel.setBackground(backgroundColor);
		introPanel.add(createAccountLabel);
		
		fieldPanel.setBackground(backgroundColor);
		
		add(introPanel, BorderLayout.NORTH);
		add(fieldPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setBackground(backgroundColor);
	}
	
	private void addListeners() {
		alreadyHaveAnAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginGUI(c).setVisible(true);
				dispose();
			}
			
		});
		
		createAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAllFieldsFilled()) {
					JOptionPane.showMessageDialog(CreateAccountGUI.this, "Please fill in all fields");
					return;
				}
				c.createAccount(new UserAccount(usernameField.getText(), new String(passwordField.getPassword()), firstNameField.getText(), lastNameField.getText()));
			}
			
		});
	}
}
