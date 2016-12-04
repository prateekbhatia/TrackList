package Constants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class AppearanceConstants {
	public static final Color darkBlue = new Color(0,0,139);
	public static final Color lightBlue = new Color(135,206,250);
	public static final Color mediumGray = new Color(100, 100, 100);
	
	public static final Font fontSmall = new Font("Avant Garde", Font.BOLD,16);
	public static final Font fontSmallest = new Font("Avant Garde", Font.BOLD,13);
	public static final Font fontMedium = new Font("Avant Garde", Font.BOLD, 20);
	public static final Font fontLarge = new Font("Avant Garde", Font.BOLD, 25);
	public static final Font fontLarger = new Font("Avant Garde", Font.BOLD, 30);
	public static final Font fontLargest = new Font("Avant Garde", Font.BOLD, 35);
	
	//added a blue border variable used in StartWindowGUI
	public static final Border blueLineBorder = BorderFactory.createLineBorder(darkBlue);
}
