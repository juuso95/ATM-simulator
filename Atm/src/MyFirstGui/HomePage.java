package MyFirstGui;

import java.awt.EventQueue;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import MyFirstGui.BalancePage;
import MyFirstGui.Payments;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import MyFirstGui.Login;


public class HomePage {
	
	public static JLabel balance1;
	private JFrame frmOttotellerSimulation;
	private JTextField txtAmount;
	private JLabel date;
	public static JLabel TheKing;
	
	
			
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window = new HomePage();
					window.frmOttotellerSimulation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Adding a Date time to HomePage and making it change the date by itself
	public void Date()
	{
		Thread Date=new Thread()
		{
			public void run ()
			{
				try {
					while (true) {
						
					Calendar cal=new GregorianCalendar();
					int day=cal.get(Calendar.DAY_OF_MONTH);
					int month=cal.get(Calendar.MONTH)+1;
					int year=cal.get(Calendar.YEAR);
					 
					
					date.setText(day+"/"+month+"/"+year);
					
					sleep(1000);
					}
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		};
		
		Date.start();
	}
	
	//Connection to the SQL database  via sqliteConnection
	Connection connection=null;
	/**
	 * Create the application.
	 */
	public HomePage() {
		initialize();
		Date();
	//Connection to the SQL database  via sqliteConnection	
		connection=sqliteConnection.dbConnector();
		
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOttotellerSimulation = new JFrame();
		frmOttotellerSimulation.setTitle("Otto-Teller simulation");
		frmOttotellerSimulation.getContentPane().setBackground(Color.WHITE);
		frmOttotellerSimulation.getContentPane().setFont(new Font("Arial", Font.PLAIN, 11));
		frmOttotellerSimulation.setBounds(100, 100, 450, 292);
		frmOttotellerSimulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOttotellerSimulation.getContentPane().setLayout(null);
		
		TheKing = new JLabel("");
		TheKing.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				//Fetching account name from database (AccountDatabase) via SQL connection which i did earlier
				try {
					{
					String query2="select Account from AccountDatabase where Account='"+Login.txtUsername.getText()+"' ";
					
					PreparedStatement pst3;
					pst3 = connection.prepareStatement(query2);
					ResultSet rs=pst3.executeQuery();
					
					//Setting the account name to the JLabel in HomePage
			          while (rs.next()) {   	  

			        TheKing.setText(rs.getString("Account"));
			          }

					pst3.close();
					
					}	
				} catch (Exception e) {

					e.printStackTrace();
				}
				}
			
			public void ancestorMoved(AncestorEvent arg0) {
			}
			public void ancestorRemoved(AncestorEvent arg0) {
			}
		});
		
		
		Button deposit = new Button("Deposit");
		deposit.setFont(new Font("Dialog", Font.BOLD, 12));
		deposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int num1,num2,ans;
				try {
					Class.forName("org.sqlite.JDBC");
				//Inserting transaction details to Transaction History table via SQL connection
					String query="insert into Transactions (Date,Amount,Type,Account) values (?,?,?,?)";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, date.getText());
					pst.setString(2, txtAmount.getText());
					pst.setString(3, deposit.getLabel());
					pst.setString(4, TheKing.getText());
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Transaction was succesfull!");
					pst.close();
					
				//Selecting column "Balance" from database with the	value of JLabel which i set before
					String query2="select Balance from AccountDatabase where Account='"+TheKing.getText()+"'";
	                    PreparedStatement pst3=connection.prepareStatement(query2);
	                    ResultSet rs=pst3.executeQuery();
	                    while(rs.next())
	                    {
	            //Setting "Balance" to label to make amount of current balance           
	                        balance1.setText(rs.getString("Balance"));
	                    }
	                    pst3.close();

				//Calculation which define the final balance	
					num1=Integer.parseInt(txtAmount.getText());
					num2=Integer.parseInt(balance1.getText());
					ans=num1+num2;
					balance1.setText(Integer.toString(ans));
					
				//Setting the final balance to another window (BalancePage)	
					frmOttotellerSimulation.dispose();
   				    BalancePage nf1=new BalancePage();
   					BalancePage.Label1.setText(balance1.getText());
   					nf1.setVisible(true);
			
				//Updating the final balance to the database (AccountDatabase)	
					String query1="Update AccountDatabase set Balance='"+balance1.getText()+"'where Account='"+TheKing.getText()+"' ";
                    PreparedStatement pst1=connection.prepareStatement(query1);
                    pst1.execute();
                    pst1.close();
					
				} catch (Exception e) {
				//If something went wrong, this message will appear	
				JOptionPane.showMessageDialog(null, "Please insert valid number!","Error!", JOptionPane.ERROR_MESSAGE);
				{
					e.printStackTrace();
				}
				}
			}
		});
		deposit.setBounds(45, 130, 70, 22);
		frmOttotellerSimulation.getContentPane().add(deposit);
		
		Button withdraw = new Button("Withdraw");
		withdraw.setFont(new Font("Dialog", Font.BOLD, 12));
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int num1,num2,ans;
				try {
				
			    //Inserting transaction details to Transaction History table via SQL connection
				Class.forName("org.sqlite.JDBC");
				String query="insert into Transactions(Date,Amount,Type,Account) values (?,?,?,?)";
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setString(1, date.getText());
				pst.setString(2, txtAmount.getText());
				pst.setString(3, withdraw.getLabel());
				pst.setString(4, TheKing.getText());
				
				pst.execute();
				JOptionPane.showMessageDialog(null, "Transaction was succesfull!","Completed!",JOptionPane.PLAIN_MESSAGE);
                
				//Selecting column "Balance" from database with the	value of JLabel which i set before
				String query2="select Balance from AccountDatabase where Account='"+TheKing.getText()+"'";		   
                   PreparedStatement pst3=connection.prepareStatement(query2);
                   ResultSet rs=pst3.executeQuery(); 
                   while(rs.next())
                   {
                 //Setting "Balance" to label to make amount of current balance  
                       balance1.setText(rs.getString("Balance"));
                   }
                   pst3.close();
                   
                //Calculation which define the final balance
				num1=Integer.parseInt(txtAmount.getText());
				num2=Integer.parseInt(balance1.getText());
				ans=num2-num1;
				balance1.setText(Integer.toString(ans));
				
				//Setting the final balance to another window (BalancePage)
				frmOttotellerSimulation.dispose();
				BalancePage nf1 = new BalancePage();
				BalancePage.Label1.setText(balance1.getText());
				nf1.setVisible(true);

				pst.close();
				
				//Updating the final balance to the database (AccountDatabase)	
				String query1="Update AccountDatabase set Balance='"+balance1.getText()+"'where Account='"+TheKing.getText()+"' ";
                PreparedStatement pst1=connection.prepareStatement(query1);
                pst1.execute();
                pst1.close();
				
			} catch (Exception e) {
				//If something went wrong, this message will appear
				JOptionPane.showMessageDialog(null, "Please insert valid number!","Error!", JOptionPane.ERROR_MESSAGE);
			{
				e.printStackTrace();
			}
				
			}	
			}
		});
		withdraw.setBounds(166, 130, 70, 22);
		frmOttotellerSimulation.getContentPane().add(withdraw);
		
		Button balance = new Button("Balance");
		balance.setFont(new Font("Arial", Font.BOLD, 12));
		balance.setBackground(Color.GREEN);
		balance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//Opening a BalancePage when pressing Balance-button
	                frmOttotellerSimulation.dispose();
	  				BalancePage info = new BalancePage();
	  				info.setVisible(true);
				
			}
		});
		balance.setBounds(233, 196, 70, 42);
		frmOttotellerSimulation.getContentPane().add(balance);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(105, 88, 105, 20);
		frmOttotellerSimulation.getContentPane().add(txtAmount);
		txtAmount.setColumns(10);
		
		JLabel amount = new JLabel("Amount:");
		amount.setForeground(Color.WHITE);
		amount.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		amount.setBounds(45, 91, 70, 14);
		frmOttotellerSimulation.getContentPane().add(amount);
		
		Button ExitButton = new Button("Exit");
		ExitButton.setFont(new Font("Arial", Font.BOLD, 12));
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
				
			}
		});
		ExitButton.setBackground(Color.PINK);
		ExitButton.setForeground(Color.BLACK);
		ExitButton.setBounds(328, 196, 70, 42);
		frmOttotellerSimulation.getContentPane().add(ExitButton);
		
		JLabel Header1 = new JLabel("Welcome to Otto-teller simulator!");
		Header1.setForeground(Color.WHITE);
		Header1.setFont(new Font("Arial", Font.BOLD, 16));
		Header1.setBounds(31, 22, 256, 41);
		frmOttotellerSimulation.getContentPane().add(Header1);
		
		JLabel €Mark = new JLabel("\u20AC");
		€Mark.setForeground(Color.WHITE);
		€Mark.setFont(new Font("Arial", Font.PLAIN, 11));
		€Mark.setBounds(220, 91, 27, 14);
		frmOttotellerSimulation.getContentPane().add(€Mark);
		
	    date = new JLabel();
	    date.setForeground(Color.WHITE);
	    date.setText("Date");
		date.setBounds(309, 22, 115, 41);
		frmOttotellerSimulation.getContentPane().add(date);
		
		
		TheKing.setForeground(Color.WHITE);
		TheKing.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		TheKing.setBounds(31, 11, 115, 22);
		frmOttotellerSimulation.getContentPane().add(TheKing);
		
		Button makepayment = new Button("Make a payment");
		makepayment.setFont(new Font("Arial", Font.BOLD, 12));
		makepayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//Opening a Payments window when pressing Make a payment-button
				frmOttotellerSimulation.dispose();
				Payments info = new Payments();
				info.setVisible(true);
			}
		});
		makepayment.setBounds(31, 196, 172, 42);
		frmOttotellerSimulation.getContentPane().add(makepayment);
		
	    balance1 = new JLabel("Balance:");
	    
		balance1.setBounds(329, 203, 46, 14);
		frmOttotellerSimulation.getContentPane().add(balance1);
		
		balance1.setText("500");
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(31, 57, 367, 2);
		frmOttotellerSimulation.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 177, 367, 2);
		frmOttotellerSimulation.getContentPane().add(separator_1);
		
		JLabel imglabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/bank.png")).getImage();
		imglabel.setIcon(new ImageIcon(img));
		imglabel.setBounds(267, 74, 131, 92);
		frmOttotellerSimulation.getContentPane().add(imglabel);
		
		JLabel backgroundImg = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/slide.png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img4));
		backgroundImg.setBounds(0, 0, 434, 261);
		frmOttotellerSimulation.getContentPane().add(backgroundImg);
	}	
}
