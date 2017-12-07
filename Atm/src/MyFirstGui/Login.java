package MyFirstGui;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import MyFirstGui.HomePage;
import java.awt.Color;



public class Login {

	private JFrame frmOttotellerSimulation;
	public static JTextField txtUsername;
	public static JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmOttotellerSimulation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	//Connection to the SQL database via sqliteConnection
	
	Connection connection=null;
	public Login() {
		initialize();
		connection=sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOttotellerSimulation = new JFrame();
		frmOttotellerSimulation.setTitle("Otto-Teller simulation");
		frmOttotellerSimulation.setBounds(100, 100, 443, 295);
		frmOttotellerSimulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOttotellerSimulation.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(222, 84, 135, 20);
		frmOttotellerSimulation.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel Usernamelabel = new JLabel("Username/Account:");
		Usernamelabel.setForeground(Color.WHITE);
		Usernamelabel.setFont(new Font("Arial", Font.PLAIN, 15));
		Usernamelabel.setBounds(44, 87, 135, 14);
		frmOttotellerSimulation.getContentPane().add(Usernamelabel);
		
		JLabel Passwordlabel = new JLabel("Password:");
		Passwordlabel.setForeground(Color.WHITE);
		Passwordlabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Passwordlabel.setBounds(44, 148, 102, 14);
		frmOttotellerSimulation.getContentPane().add(Passwordlabel);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(222, 143, 135, 20);
		frmOttotellerSimulation.getContentPane().add(txtPassword);
		
		JLabel Header = new JLabel("Login System");
		Header.setForeground(Color.WHITE);
		Header.setFont(new Font("Tahoma", Font.BOLD, 22));
		Header.setBounds(122, 11, 164, 49);
		frmOttotellerSimulation.getContentPane().add(Header);
		
		Button LoginButton = new Button("Login");
		LoginButton.setFont(new Font("Arial", Font.BOLD, 12));
		LoginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				//Fetching login details from AccountDatabase
				//if login details is not found in the database, error message will appear
				
				try {
					String query="select * from AccountDatabase where Account=? and Password=? ";
					
				    
					PreparedStatement pst3 = connection.prepareStatement(query);
					pst3.setString(1, txtUsername.getText());
					pst3.setString(2, txtPassword.getText());
					ResultSet rs=pst3.executeQuery();
					  int count=0;
			          while (rs.next()) {
			        	  count=count+1;
			 
			          }
			          if (count==1)
			          {
			        	  JOptionPane.showMessageDialog(null, "Login was succesfull!","Login completed!", JOptionPane.INFORMATION_MESSAGE);
			        	  frmOttotellerSimulation.dispose();
						  HomePage.main(null);
			          }
			          else if (count>1)
			          {
			        	  JOptionPane.showMessageDialog(null, "Duplicate username or password!","Login error!", JOptionPane.ERROR_MESSAGE);
			          }
			          else 
			          {
			        	  JOptionPane.showMessageDialog(null, "Invalid login details!","Login error!", JOptionPane.ERROR_MESSAGE);
			          }
			          rs.close();
			          pst3.close();
			          
			           
				} catch (Exception e) {

					e.printStackTrace();
				}
				
					
					
			}	
				
		});
		LoginButton.setBounds(44, 207, 87, 30);
		frmOttotellerSimulation.getContentPane().add(LoginButton);
		
		Button ResetButton = new Button("Reset");
		ResetButton.setFont(new Font("Arial", Font.BOLD, 12));
		ResetButton.addActionListener(new ActionListener() {
			
			//Setting textField values to null when clicking "reset"
			
			public void actionPerformed(ActionEvent arg0) {
				txtUsername.setText(null);
				txtPassword.setText(null);
			}
		});
		ResetButton.setBounds(171, 207, 81, 30);
		frmOttotellerSimulation.getContentPane().add(ResetButton);
		
		Button ExitButton = new Button("Exit");
		ExitButton.setFont(new Font("Arial", Font.BOLD, 12));
		ExitButton.setBackground(Color.PINK);
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		ExitButton.setBounds(285, 207, 87, 30);
		frmOttotellerSimulation.getContentPane().add(ExitButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(31, 58, 355, 2);
		frmOttotellerSimulation.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 187, 355, 2);
		frmOttotellerSimulation.getContentPane().add(separator_1);
		
		JLabel oklabel = new JLabel();
		Image img = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		oklabel.setIcon(new ImageIcon(img));
		oklabel.setBounds(86, 22, 60, 30);
		frmOttotellerSimulation.getContentPane().add(oklabel);
		
		JLabel Background = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/slide.png")).getImage();
		Background.setIcon(new ImageIcon(img4));
		Background.setBounds(0, 0, 434, 261);
		frmOttotellerSimulation.getContentPane().add(Background);
	}
}
