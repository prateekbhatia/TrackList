package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Constants.AppearanceConstants;
import client.client;
import utilities.Task;
import utilities.UserAccount;

public class UserTaskInputGUI extends JPanel {
	private JTextField inputField;
	private JButton deadlineButton;
	private JButton characterButton;
	private JPanel buttonContainer;
	private JDialog calendarDialog;
	private CalendarPicker calendarPicker;
	
	private int year, month, day, hour, minute;
	private String ampm;
	
	private client client;
	private String listName;
	private UserAccount user;
	private boolean dateChosen;
	private String listOwner;
	
	public UserTaskInputGUI(client client, String listName, UserAccount user, String listOwner) {
		this.listName = listName;
		this.client = client;
		this.user = user;
		this.listOwner = listOwner;
		dateChosen = false;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables() {
		inputField = new JTextField();
		deadlineButton = new JButton(new ImageIcon("images/calendar.png"));
		characterButton = new JButton();
		buttonContainer = new JPanel();
		calendarDialog = new JDialog();
		calendarPicker = new CalendarPicker(this);
		
	}
	
	public void setDeadline(int yyyy, int mm, int dd, int hh, int m, String ampm) {
		dateChosen = true;
		year = yyyy;
		month = mm;
		day = dd;
		hour = hh;
		minute = m;
		this.ampm = ampm;
		if (ampm.equals("PM")) {
			hour += 12;
		}
		System.out.println(year + "-" + month + "-" + day + "  " + hour + ":" + minute + ampm);
	}
	
	private void createGUI() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(700, 60));
		
		inputField.setBackground(Color.WHITE);
		inputField.setForeground(Color.BLACK);
		inputField.setFont(AppearanceConstants.fontSmallest);
		inputField.setOpaque(true);
		inputField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		deadlineButton.setBorderPainted(false);
		deadlineButton.setEnabled(true);
		
		characterButton.setBorderPainted(true);
		characterButton.setFont(AppearanceConstants.fontSmallest);
		characterButton.setBackground(Color.WHITE);
		characterButton.setForeground(Color.BLUE.darker().darker());
		characterButton.setEnabled(false);
		characterButton.setText("0");
		characterButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		buttonContainer.setBackground(Color.WHITE);
		buttonContainer.setLayout(new GridLayout(1,2));
		
		buttonContainer.add(characterButton);
		buttonContainer.add(deadlineButton);
		
		add(inputField, BorderLayout.CENTER);
		add(buttonContainer, BorderLayout.EAST);
	}
	
	private void setCharacterLabel() {
		if (!inputField.getText().equals("Enter a task . . .")) {
			characterButton.setText(""+inputField.getText().length());
		}
	}
	
	private void addActionListeners() {
		inputField.addFocusListener(new TextFieldFocusListener("Enter a task . . .", inputField));
		inputField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setCharacterLabel();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setCharacterLabel();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setCharacterLabel();
			}
			
		});
		
		inputField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!inputField.getText().isEmpty() && dateChosen) {
						client.addTask(new Task(inputField.getText().trim(), listName, listOwner, year, month, day, hour, minute, user));
						inputField.setText("");
					} else {
						if (!dateChosen) {
							JOptionPane.showMessageDialog(UserTaskInputGUI.this, "Please choose a deadline");
						} else {
							JOptionPane.showMessageDialog(UserTaskInputGUI.this, "Please enter a task information");
						}
					}
				}
			}
			
		});
		
		deadlineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calendarPicker.setVisible(true);
				System.out.println(calendarPicker.getHeight() +"  " + calendarPicker.getWidth());
			}
			
		});
	}
	
	public static void main(String [] args) {
		/*JFrame test = new JFrame();
		test.setLocation(0,0);
		test.setSize(1000,300);
		JPanel temp = new JPanel();
		temp.add(new UserTaskInputGUI(new client("localhost", 6789), "list", null));
		test.add(temp, BorderLayout.NORTH);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
	}
}
