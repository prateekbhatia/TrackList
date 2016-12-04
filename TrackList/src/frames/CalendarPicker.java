package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;

public class CalendarPicker extends JDialog
{	
	
	private JXDatePicker datePicker;
	private JSpinner spinner;
	private JButton doneButton;
	private UserTaskInputGUI inputGUI;
	
	public CalendarPicker(UserTaskInputGUI inputGUI)
	{
		this.inputGUI = inputGUI;
		datePicker = new JXDatePicker();
		datePicker.setDate(Calendar.getInstance().getTime());
		datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		
		spinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
		spinner.setEditor(new JSpinner.DateEditor(spinner, "hh:mm a"));
		

		doneButton = new JButton("Done");
		doneButton.setBorderPainted(false);
		doneButton.setOpaque(false);
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputGUI.setDeadline(getYear(), getMonth(), getDate(), getHour(), getMinute(), getAMPM());
				dispose();
			}
		});
		
		setUI();
	}
	
	private void setUI()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));
		buttonPanel.add(spinner);
		buttonPanel.add(doneButton);
		
		add(datePicker.getMonthView(), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(233,245));
		setResizable(false);
		pack();
	}
	
	public int getYear()
	{
		Date date = new Date();
		date.setTime((long)datePicker.getMonthView().getSelectionDate().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		return year;
	}
	
	public int getMonth()
	{
		Date date = new Date();
		date.setTime((long)datePicker.getMonthView().getSelectionDate().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		return month;
	}
	
	public int getDate()
	{
		Date date = new Date();
		date.setTime((long)datePicker.getMonthView().getSelectionDate().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return day;
	}

	public int getHour()
	{
		return Integer.parseInt((new SimpleDateFormat("hh").format(spinner.getValue())));
	}
	
	public int getMinute()
	{
		return Integer.parseInt((new SimpleDateFormat("mm").format(spinner.getValue())));
	}
	
	public String getAMPM() {
		return new SimpleDateFormat("a").format(spinner.getValue());
	}
}
