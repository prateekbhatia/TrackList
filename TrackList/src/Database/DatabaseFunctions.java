package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.spi.DirStateFactory.Result;

import utilities.Notification;
import utilities.Task;
import utilities.ToDoList;
import utilities.UserAccount;

public class DatabaseFunctions {
	private static final boolean DEBUG = true;
	
	private static String username = "root";
	private static String password = "root";
	
	// Login Authentication 
	// Parameters: String, String
	// Return Type: boolean
	// Function: Checks whether username and password exist in database, and returns true/false
	/*public static boolean validCredentials(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("Select count(*) as num from UserAccounts where Username = '" + ua.getUsername() + "' and "
					+ "Password = '" + ua.getPassword() + "'");
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("num") == 1) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}*/
	public static boolean validCredentials(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM UserAccount WHERE Username = ? and Password = ?");
			ps.setString(1, ua.getUsername());
			ps.setString(2, ua.getPassword());
			rs = ps.executeQuery();
			
			return rs.next();
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			if(DEBUG)
				cnfe.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return false;
	}
	
	// Sign Up 
	// Parameters: String, String, String
	// Return Type: boolean
	// Function: Returns false if username is already taken, otherwise creates the account and returns true
	/*public boolean signUp(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("Select count(*) as num from UserAccounts where Username = '" + ua.getUsername() + "'");
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num") == 1) {
					return false;
				} else {
					ps.close();
					ps = conn.prepareStatement("Insert into UserAccounts values('" + ua.getUsername() + "','" +  
							ua.getPassword() + "', '" + ua.getFirstName() + "','" + ua.getLastName() + "')");
					ps.executeUpdate();
					return true;
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}*/
	public boolean signUp(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM UserAccount where Username = ?");
			ps.setString(1, ua.getUsername());
			rs = ps.executeQuery();
			if(rs.next())
				return false;
			else
			{
				ps.close();
				ps = conn.prepareStatement("INSERT INTO UserAccount(Username, Password, FirstName, LastName) VALUES(?, ?, ?, ?)");
				ps.setString(1, ua.getUsername());
				ps.setString(2, ua.getPassword());
				ps.setString(3, ua.getFirstName());
				ps.setString(4, ua.getLastName());
				ps.executeUpdate();
				
				return true;
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			if(DEBUG)
				cnfe.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return false;
	}
	
	
	// Add To-Do List 
	// Parameters: String, String
	// Return Type: boolean
	// Function: Returns false if To-Do List Name is already taken, otherwise adds the To-Do List to
	// the table ToDoListMembers and returns true
	/*public boolean addToDoList(ToDoList td) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("Select count(*) as num from ToDoListMembers where ParentList = '" + td.getName() + 
					"' and username = '" + td.getUsername() + "'");
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num") != 0) {
					return false;
				} else {
					ps.close();
					ps = conn.prepareStatement("Insert into ToDoListMembers values('" + td.getName() + "','" + td.getUsername() + "', '1')");
					ps.executeUpdate();
					return true;
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}*/
	public boolean addToDoList(ToDoList td) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM ToDoList WHERE Name = ? AND Owner = ?");
			ps.setString(1, td.getName());
			ps.setString(2, td.getUsername());
			rs = ps.executeQuery();
			if(rs.next())
				return false;
			else
			{
				ps.close();
				ps = conn.prepareStatement("INSERT INTO ToDoList (Name, Owner) VALUES (?, ?)");
				ps.setString(1, td.getName());
				ps.setString(2, td.getUsername());
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("INSERT INTO ToDoListMember (MemberName, ListID) VALUES(?, (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?))");
				ps.setString(1, td.getUsername());
				ps.setString(2, td.getName());
				ps.setString(3, td.getAssigner());
				ps.executeUpdate();
				return true;
			}
		} catch (SQLException sqle) {
			if (DEBUG) 
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if (DEBUG) 
					sqle.printStackTrace();
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}
	
	// Remove To-Do List 
	// Parameters: String, String
	// Return Type: boolean
	// Function: Returns false if To-Do List Name is already taken, otherwise adds the To-Do List to
	// the table ToDoListMembers and returns true
	public boolean removeToDoList(ToDoList td) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("Select count(*) as num from ToDoListMembers where ParentList = '" + td.getName() + "'");
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num") == 0) {
					return false;
				} else {
					ps.close();
					ps = conn.prepareStatement("Delete from ToDoListMembers where ParentList = '" + td.getName() + "'");
					ps.executeUpdate();
					ps.close();
					ps = conn.prepareStatement("Delete from ToDoLists where ParentList = '" + td.getName() + "'");
					ps.executeUpdate();
					ps.close();
					/*ps = conn.prepareStatement("Delete from MyWorklists where ParentList = '" + td.getName() + "'");
					ps.executeUpdate();*/
					
					return true;
				}
			}
			
			for(Task t : td.getTasks()) {
				removeTask(t);
			}
			
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return false;
	}

	// Add a Task
	// Parameters: String, String, Int, Int, Boolean, String, String, Int, Int
	// Return Type: boolean
	// Function: Adds the task to the tables MyWorklists, Notifications, ToDoListMembers, ToDoLists
	// (It also adds the ToDoList Specified for the Task if it does not already exist)
	/*public boolean addTask(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			ResultSet temp1 = conn.prepareStatement("Select count(*) as num1 from ToDoLists where ParentList = '" + t.getParent() +
					"' and Task = '" + t.getTask() + "'").executeQuery();
			if(temp1.next()) {
				if(temp1.getInt("num1") == 0) {
					conn.prepareStatement("Insert into ToDoLists values('" + t.getParent() + "','" + t.getTask() + "',"
							+ t.getDeadlineYear() + "," + t.getDeadlineMonth() + "," + t.getDeadlineDay() + "," + t.getDeadlineHour()
							+ "," + t.getDeadlineMinute() + "," + t.isCompleted() + ",'" + t.getAssigner().getUsername() + "')").executeUpdate();
				} else {
					return false;
				}
			}
			
			// This was commented
			ResultSet temp2 = conn.prepareStatement("Select count(*) as num1 from MyWorklists where Username = '" + t.getAssigner().getUsername() +
					"' and Task = '" + t.getTask() + "'").executeQuery();
			if(temp2.next()) {
				if(temp2.getInt("num1") == 0) {
					conn.prepareStatement("Insert into MyWorklists values ('" + t.getAssigner().getUsername() + "','" + t.getTask() + "','" + 
							t.getParent() + "'," + t.getDeadlineYear() + "," + t.getDeadlineMonth() + "," + t.getDeadlineDay() + "," + t.getDeadlineHour()
							+ "," + t.getDeadlineMinute() + "," + t.isCompleted() + ")").executeUpdate();
				} else {
					return false;
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return true;
	}*/
	public boolean addTask(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			ps = conn.prepareStatement("SELECT * FROM Task WHERE CreatedBy = ? AND TaskInfo = ? AND ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?)");
			ps.setString(1, t.getAssigner().getUsername());
			ps.setString(2, t.getTask());
			ps.setString(3, t.getParent());
			ps.setString(4, t.getOwner());
			rs = ps.executeQuery();
			if(rs.next())
				return false;
			else
			{
				ps.close();
				ps = conn.prepareStatement("INSERT INTO Task (TaskInfo, CreatedBy, ListID, TaskDeadlineYear, TaskDeadlineMonth, TaskDeadlineDay, "
						+ "TaskDeadlineHour,TaskDeadlineMinute) VALUES(?, ?, (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?), ?, ?, ?, ?, ?)");
				ps.setString(1, t.getTask());
				ps.setString(2,  t.getAssigner().getUsername());
				ps.setString(3, t.getParent());
				ps.setString(4, t.getOwner());
				ps.setInt(5, t.getDeadlineYear());
				ps.setInt(6, t.getDeadlineMonth());
				ps.setInt(7, t.getDeadlineDay());
				ps.setInt(8, t.getDeadlineHour());
				ps.setInt(9, t.getDeadlineMinute());
				ps.executeUpdate();
				
				return true;
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return false;
	}
	
	// Remove a Task
	// Parameters: Task
	// Return Type: boolean
	// Function: Removes the given Task from the SQL database if it exists, returning true if successful, false otherwise
	public boolean removeTask(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");

			ResultSet temp1 = conn.prepareStatement("Select count(*) as num1 from ToDoLists where ParentList = '" + t.getParent() +
					"' and Task = '" + t.getTask() + "'").executeQuery();
			if(temp1.next()) {
				if(temp1.getInt("num1") != 0) {
					conn.prepareStatement("Delete from ToDoLists where ParentList = '" + t.getParent() + "' and Task = '" + t.getTask() + "' "
							+ "and taskdeadlineyear = " + t.getDeadlineYear() + " and taskdeadlineMonth = " + t.getDeadlineMonth() + 
							" and taskdeadlineday = " + t.getDeadlineDay() + " and taskdeadlineHour = " + t.getDeadlineHour() + 
							" and taskdeadlineminute = " + t.getDeadlineMinute() + " and completed = " + t.isCompleted() + 
							" and assignedby = '" + t.getAssigner().getUsername() + "'").executeUpdate();
				} else {
					return false;
				}
			}

			/*conn.prepareStatement("Delete from MyWorklists where username = '" + t.getAssigner().getUsername() + 
					"' and task = '" + t.getTask() + "' and parentlist = '" + t.getParent() + "' and taskdeadlineyear = " 
					+ t.getDeadlineYear() + " and taskdeadlineMonth = " + t.getDeadlineMonth() + " and taskdeadlineday = " + 
					t.getDeadlineDay() + " and taskdeadlinehour = " + t.getDeadlineHour() + " and taskdeadlineminute = " + 
					t.getDeadlineMinute()).executeUpdate();*/

		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return true;
	}
	
	// Add Notification
	// Parameters: Notification
	// Return Type: boolean
	// Function: Inserts the given Notification into the SQL database, returning true if successful, false otherwise.
	/*public boolean addNotification(Notification n) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			conn.prepareStatement("Insert into Notifications values('" + n.getMessage() +
								"', " + n.getRead() + ",'" + n.getAssignee().getUsername() + "'," + 0 + ")").executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return true;
	}*/
	public boolean addNotification(Notification n) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			ps = conn.prepareStatement("INSERT INTO Notification (Message, Owner) VALUES(?, ?)");
			ps.setString(1, n.getMessage());
			ps.setString(2, n.getAssignee().getUsername());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return true;
	}
	
	// Remove Notifications
	// Parameters: UserAccount
	// Return Type: void
	// Function: Removes all Notifications attached to the given UserAccount
	public void removeNotifications(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			conn.prepareStatement("Delete from Notifications where AssignedTo = '" + ua.getUsername() + "'").executeUpdate();
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
	}
	
	// Set Completed
	// Parameters: Task
	// Return Type: void
	// Function: Sets the given Task's "completed" flag to true/completed.
	/*public void setCompleted(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			conn.prepareStatement("Update ToDoLists set completed = 1 where ParentList = '" + t.getParent() + "'" + 
					"and task = '" + t.getTask() + "'").executeUpdate();
					
			// This was commented
			conn.prepareStatement("Update MyWorklists set completed = 1 where ParentList = '" + t.getParent() + "'" + 
					"and task = '" + t.getTask() + "'").executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}*/
	public void setCompleted(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Task WHERE ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?) AND TaskInfo = ?");
			ps.setString(1, t.getParent());
			ps.setString(2, t.getOwner());
			ps.setString(3, t.getTask());
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				ps.close();
				ps = conn.prepareStatement("UPDATE Task SET Completed = 1 WHERE ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?) AND TaskInfo = ?");
				ps.setString(1, t.getParent());
				ps.setString(2, t.getOwner());
				ps.setString(3, t.getTask());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
	}

	
	// Add User To List
	// Parameters: UserAccount, ToDoList
	// Return Type: boolean
	// Function: Attempts to add the given UserAccount to the given ToDoList, returning true if successful, false if otherwise
	/*public boolean addUserToList(UserAccount ua, ToDoList td) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("Select count(*) as num from ToDoListMembers where ParentList = '" + td.getName() +
					"' and Username = '" + ua.getUsername() + "'");
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num") != 0) {
					return false;
				} else {
					ps.close();
					ps = conn.prepareStatement("Insert into ToDoListMembers values('" + td.getName() + "','" + ua.getUsername() + "', '0')");
					ps.executeUpdate();
					return true;
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}*/
	public boolean addUserToList(UserAccount ua, ToDoList td) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM ToDoListMember WHERE ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?) AND MemberName = ?");
			ps.setString(1, td.getName());
			ps.setString(2, td.getAssigner());
			ps.setString(3, ua.getUsername());
			rs = ps.executeQuery();
			
			if(rs.next())
				return false;
			else
			{
				ps.close();
				ps = conn.prepareStatement("INSERT INTO ToDoListMember(MemberName, ListID) VALUES(?, (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?))");
				ps.setString(1, ua.getUsername());
				ps.setString(2, td.getName());
				ps.setString(3, td.getAssigner());
				ps.executeUpdate();
				return true;
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return false;
	}
	
	// Set Incompleted
	// Parameters: Task
	// Return Type: void
	// Function: Sets the given Task's "completed" flag to false/incomplete.
	public void setIncompleted(Task t) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			conn.prepareStatement("Update ToDoLists set completed = 0 where ParentList = '" + t.getParent() + "'" + 
					"and task = '" + t.getTask() + "'").executeUpdate();
			/*conn.prepareStatement("Update MyWorklists set completed = 0 where ParentList = '" + t.getParent() + "'" + 
					"and task = '" + t.getTask() + "'").executeUpdate();*/
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
	}
	
	// Set Read
	// Parameters: Notification
	// Return Type: void
	// Function: Sets the parameterized Notifcation's "read" flag to true/read.
	/*public void setRead(Notification n) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");

			conn.prepareStatement("Update Notifications set MarkRead = 1 where Notification = '" + n.getMessage() +
					"' and AssignedTo = '" + n.getAssignee().getUsername() + "'").executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}*/
	public void setRead(Notification n) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Notification WHERE Message = ? AND Owner = ?");
			ps.setString(1, n.getMessage());
			ps.setString(2, n.getAssignee().getUsername());
			rs = ps.executeQuery();
			if(rs.next())
			{
				ps.close();
				ps = conn.prepareStatement("UPDATE Notification SET MarkRead = 1 WHERE Message = ? AND Owner = ?");
				ps.setString(1, n.getMessage());
				ps.setString(2, n.getAssignee().getUsername());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
	}
	
	
	// Set Unread
	// Parameters: Notification
	// Return Type: void
	// Function: Sets the parameterized Notifcation's "read" flag to false/unread.
	public void setUnread(Notification n) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");

			conn.prepareStatement("Update Notifications set MarkRead = 0 where Notification = '" + n.getMessage() +
					"' and AssignedTo = '" + n.getAssignee().getUsername() + "'").executeUpdate();
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
	}
	
	// Get User
	// Parameters: String
	// Return Type: UserAccount
	// Function: Returns the UserAccount object whose username = u, if one exists.
	public UserAccount getUser(String u) {
		UserAccount ua = getUserAccount(u);
		if(ua == null)
			return null;
		for(ToDoList td : getToDoLists(ua)) {
			ua.addProject(td);
		}
		
		for(Notification n : getNotifications(u)) {
			ua.addNotification(n);
		}
		
		return ua;
	}
	
	// Get User Account
	// Parameters: String
	// Return Type: UserAccount
	// Function: Returns the UserAccount object whose username = u, if one exists. 
	/*public UserAccount getUserAccount(String u) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAccount ua;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			rs = conn.prepareStatement("Select * from UserAccounts where Username = '" + u + "'").executeQuery();
			
			if(rs.next()) {
				String password = rs.getString("Password");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");

				ua = new UserAccount(u, password, firstName, lastName);
				return ua;
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return null;
	}*/
	public UserAccount getUserAccount(String u) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAccount ua;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM UserAccount WHERE Username = ?");
			ps.setString(1, u);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String password = rs.getString("Password");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");

				ua = new UserAccount(u, password, firstName, lastName);
				return ua;
			}
			else
				return null;
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			if(DEBUG)
				cnfe.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return null;
	}
	
	// Get Notifications
	// Parameters: String
	// Return Type: Vector<Notification>
	// Function: Returns the complete set of notifications belonging to the UserAccount whose username = user (as a vector).
	/*public Vector<Notification> getNotifications(String user){
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Notification> notifications = new Vector<Notification>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			rs = conn.prepareStatement("Select * from Notifications where AssignedTo = '" + user + "'").executeQuery();
			
			while(rs.next()) {
				String message = rs.getString("Notification");
				boolean read = rs.getBoolean("MarkRead");
				int index = rs.getInt("Index");
				
				Notification notification = new Notification(message, read, getUserAccount(user), index);

				notifications.add(notification);
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return notifications;
	}*/
	public Vector<Notification> getNotifications(String user){
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Notification> notifications = new Vector<Notification>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT * FROM Notification WHERE Owner = ?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String message = rs.getString("Message");
				boolean read = rs.getBoolean("MarkRead");
				int index = rs.getInt("NotificationID");
				
				Notification notification = new Notification(message, read, getUserAccount(user), index);

				notifications.add(notification);
			}
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return notifications;
	}
	
	// Get To Do List
	// Parameters: String
	// Return Type: ToDoList
	// Function: Takes in the parameterized String (which ideally is the name of an existing ToDoList) and returns the complete ToDoList object
	// associated with the given list name.
/*	public ToDoList getToDoList(String listName){
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Vector<Task> tasks = new Vector<Task>();
		Vector<String> assignees = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			rs = conn.prepareStatement("Select * from ToDoLists where ParentList = '" + listName + "'").executeQuery();
			rs1 = conn.prepareStatement("Select count(*) as num from ToDoListMembers where ParentList = '" + listName + "'").executeQuery();
			rs2 = conn.prepareStatement("Select count(*) as cnt from ToDoLists where ParentList = '" + listName + "'").executeQuery();
			rs3 = conn.prepareStatement("Select * from ToDoListMembers where ParentList = '" + listName + "'").executeQuery();
			
			if(rs1.next()){
				if(!rs2.next()){
					while(rs.next()) {
						String task = rs.getString("Task");
						int deadlineYear = rs.getInt("TaskDeadlineYear");
						int deadlineMonth = rs.getInt("TaskDeadlineMonth");
						int deadlineDay = rs.getInt("TaskDeadlineDay");
						int deadlineHour = rs.getInt("TaskDeadlineHour");
						int deadlineMinute = rs.getInt("TaskDeadlineMinute");
						boolean completed = rs.getBoolean("Completed");
						String assignedBy = rs.getString("AssignedBy");
						Task t = new Task(task, listName, deadlineYear, deadlineMonth, deadlineDay, deadlineHour, deadlineMinute, getUserAccount(assignedBy));
						assignees.add(assignedBy);
						System.out.println("H: " + assignedBy);
						if(completed) {
							t.setCompleted();
						}
						tasks.add(t);
						ToDoList td = new ToDoList(listName, assignees.get(0), getUserAccount(assignees.get(0)));
						for(Task t1 : tasks) {
							td.addTask(t1);
						}
					
						for(int i = 1; i < assignees.size(); i++) {
							td.addAssignee(assignees.get(i));
						}
					
						return td;
					}
				}
				else{
					if (rs3.next()) {
						ToDoList td1 = new ToDoList(listName, rs3.getString("Username"), getUserAccount(rs3.getString("Username")));
						return td1;
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return null;
	}*/
	/*public ToDoList getToDoList(String listName){
		Connection conn = null;
		ResultSet getListTasks = null;
		ResultSet getMembers = null;
		ResultSet getOwner = null;
		Vector<Task> tasks = new Vector<Task>();
		Vector<String> assignees = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			getOwner = conn.prepareStatement("Select * from ToDoListMembers where ParentList = '" + listName + "' AND Owner='1'").executeQuery();
			getListTasks = conn.prepareStatement("Select * from ToDoLists where ParentList = '" + listName + "'").executeQuery();
			getMembers = conn.prepareStatement("Select * from ToDoListMembers where ParentList = '" + listName + "' AND Owner ='0'").executeQuery();
			
			// Get owner
			if(getOwner.next())
			{
				assignees.add(getOwner.getString("Username"));
			}
			else
			{
				return null;
			}
			
			// Get members
			while(getMembers.next())
			{
				assignees.add(getMembers.getString("Username"));
			}
			
			// Get tasks
			while(getListTasks.next())
			{
				String taskInfo = getListTasks.getString("Task");
				int deadlineYear = getListTasks.getInt("TaskDeadlineYear");
				int deadlineMonth = getListTasks.getInt("TaskDeadlineMonth");
				int deadlineDay = getListTasks.getInt("TaskDeadlineDay");
				int deadlineHour = getListTasks.getInt("TaskDeadlineHour");
				int deadlineMinute = getListTasks.getInt("TaskDeadlineMinute");
				boolean completed = getListTasks.getBoolean("Completed");
				String assignedBy = getListTasks.getString("AssignedBy");
				Task task = new Task(taskInfo, listName, deadlineYear, deadlineMonth, deadlineDay, deadlineHour, deadlineMinute, getUserAccount(assignedBy));
				
				tasks.add(task);
				if(completed) {
					task.setCompleted();
				}
			}
			
			// ?????????
			ToDoList td = new ToDoList(listName, assignees.get(0), getUserAccount(assignees.get(0)));
			for(Task taskElem : tasks)
			{
				td.addTask(taskElem);
			}
			for(int i = 1; i < assignees.size(); i++) {
				td.addAssignee(assignees.get(i));
			}
			
			return td;
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (getOwner != null) {
					getOwner.close();
				}
				if (getListTasks != null) {
					getListTasks.close();
				}
				if (getMembers != null) {
					getMembers.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return null;
	}*/
	public ToDoList getToDoList(String listName, String owner){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet getListTasks = null;
		ResultSet getMembers = null;
		ResultSet getOwner = null;
		Vector<Task> tasks = new Vector<Task>();
		Vector<String> assignees = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			// Get owner
			assignees.add(owner);
			
			// Get members
			ps = conn.prepareStatement("SELECT MemberName FROM ToDoListMember WHERE ToDoListMember.ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?) AND MemberName != ?");
			ps.setString(1, listName);
			ps.setString(2, owner);
			ps.setString(3, owner);
			// need owner
			
			getMembers = ps.executeQuery();
			while(getMembers.next())
			{
				assignees.add(getMembers.getString("MemberName"));
			}
			ps.close();
			
			
			ps = conn.prepareStatement("SELECT * FROM Task WHERE ListID = (SELECT ListID FROM ToDoList WHERE Name = ? AND Owner = ?)");
			ps.setString(1, listName);
			ps.setString(2, owner);
			
			getListTasks = ps.executeQuery();
			// Get tasks
			while(getListTasks.next())
			{
				String taskInfo = getListTasks.getString("TaskInfo");
				int deadlineYear = getListTasks.getInt("TaskDeadlineYear");
				int deadlineMonth = getListTasks.getInt("TaskDeadlineMonth");
				int deadlineDay = getListTasks.getInt("TaskDeadlineDay");
				int deadlineHour = getListTasks.getInt("TaskDeadlineHour");
				int deadlineMinute = getListTasks.getInt("TaskDeadlineMinute");
				boolean completed = getListTasks.getBoolean("Completed");
				String assignedBy = getListTasks.getString("CreatedBy");
				Task task = new Task(taskInfo, listName, assignees.get(0), deadlineYear, deadlineMonth, deadlineDay, deadlineHour, deadlineMinute, getUserAccount(assignedBy));
				
				tasks.add(task);
				if(completed) {
					task.setCompleted();
				}
			}
			
			// ?????????
			ToDoList td = new ToDoList(listName, assignees.get(0), getUserAccount(assignees.get(0)));
			for(Task taskElem : tasks)
			{
				td.addTask(taskElem);
			}
			for(int i = 1; i < assignees.size(); i++) {
				td.addAssignee(assignees.get(i));
			}
			
			return td; 
			
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (getOwner != null) {
					getOwner.close();
				}
				if (getListTasks != null) {
					getListTasks.close();
				}
				if (getMembers != null) {
					getMembers.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return null;
	}
	
	
	// Get To Do Lists
	// Parameters: UserAccount
	// Return Type: Vector<ToDoList>
	// Function: Takes the parameterized UserAccount and returns the complete set of To Do Lists that
	// the UserAccount is a part of, as a Vector.
	/*public Vector<ToDoList> getToDoLists(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<ToDoList> toDoLists = new Vector<ToDoList>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			
			rs = conn.prepareStatement("Select * from ToDoListMembers where Username = '" + ua.getUsername() + "'").executeQuery();
			
			while(rs.next()) {
				toDoLists.add(getToDoList(rs.getString("ParentList")));
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return toDoLists;
	}*/
	public Vector<ToDoList> getToDoLists(UserAccount ua) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<ToDoList> toDoLists = new Vector<ToDoList>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupProject?user=" + username + "&password=" + password + "&useSSL=false");
			ps = conn.prepareStatement("SELECT ToDoList.Name, ToDoList.Owner FROM ToDoListMember INNER JOIN ToDoList ON ToDoListMember.ListID=ToDoList.ListID WHERE ToDoListMember.MemberName = ?");
			ps.setString(1, ua.getUsername());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String listName = rs.getString("Name");
				String owner = rs.getString("Owner");
				toDoLists.add(getToDoList(listName, owner));
			}
			
		} catch (SQLException sqle) {
			if(DEBUG)
				sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				if(DEBUG)
					sqle.printStackTrace();
			}
		}
		return toDoLists;
	}

	//test code
	public static void main (String[] args) {/*
		DatabaseFunctions g = new DatabaseFunctions();
		UserAccount a, b;
		a = new UserAccount("jeffrey", "password", "jeffrey", "nagel");
		b = new UserAccount("sfahimi", "password", "shawn", "fahimi");
		g.signUp(a);
		g.signUp(b);
		ToDoList t = new ToDoList("Project 201", "sfahimi", b);
		g.addToDoList(t);
		g.addTask(new Task("Complete Code", "Project 201", 2016, 14, 11, 23, 59, b));
		g.addTask(new Task("Complete and Submit", "Project 201", 2016, 14, 11, 23, 59, a));
		g.addUserToList(a, t);
		g.addNotification(new Notification("message2", a));
		g.addNotification(new Notification("message2", b));*/
	}
}
