package MyFirstGui;
import java.sql.*;
import javax.swing.*;
public class sqliteConnection {
	//Making a connection to the SQL database in that way that the connection can be used in every class with shorter commands
	//The path to the database needs to be correct to make the connection work
	//The correct path is the file path where you stored the SQlite-file
	//Example: "C:\\Users\\Juuzo\\eclipse-workspace\\ATM.sqlite"
	Connection conn=null;
	public static Connection dbConnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn= DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Juuzo\\eclipse-workspace\\ATM.sqlite");
			
			return conn;
			
		} catch (Exception e)	
		{
	JOptionPane.showMessageDialog(null, e);
	return null;
}
	}
}