package MyFirstGui;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import MyFirstGui.HomePage;
import MyFirstGui.TransactionHistory;
import MyFirstGui.AccountDatabase;
import MyFirstGui.Login;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

//Adding default version serial ID
public class BalancePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public  JLabel label;
	public static JLabel Label1;
	private JLabel AccountNam;
	
	
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BalancePage frame = new BalancePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//Connection to the SQL database via sqliteConnection
	Connection connection=null;
	public BalancePage() {
		connection=sqliteConnection.dbConnector();
		setTitle("Otto-Teller simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 443, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel ThankYoulbl = new JLabel("Thank you! Your current balance is:");
		ThankYoulbl.setForeground(Color.WHITE);
		ThankYoulbl.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		ThankYoulbl.setBounds(42, 63, 231, 27);
		contentPane.add(ThankYoulbl);
		
		Button TransHistoryBut = new Button("View transaction history");
		TransHistoryBut.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		TransHistoryBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//Open a TransactionHistory window when pressing View transaction history-button
				contentPane.setVisible(false);
				dispose();
				TransactionHistory.main(null);
			}
		});
		
		TransHistoryBut.setBounds(42, 126, 154, 37);
		contentPane.add(TransHistoryBut);
		
		Button ADatabaseBut = new Button("Account database");
		ADatabaseBut.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		ADatabaseBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//Opening a AccountDatabase window when pressing Account database-button
				contentPane.setVisible(false);
				dispose();
				AccountDatabase.main(null);
			}
		});
		ADatabaseBut.setBounds(239, 126, 154, 37);
		contentPane.add(ADatabaseBut);
		
		Button AnotherBut = new Button("Do another transaction");
		AnotherBut.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		AnotherBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//Going back to HomePage when pressing Do another transaction-button		
				contentPane.setVisible(false);
				dispose();
				HomePage.main(null);
			}
		});
		AnotherBut.setBounds(42, 184, 154, 37);
		contentPane.add(AnotherBut);
		
		Button ExitBut = new Button("Exit");
		ExitBut.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		ExitBut.setBackground(Color.PINK);
		ExitBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
		//Exiting when pressing Exit-button		
				System.exit(0);
			}
		});
		ExitBut.setBounds(239, 184, 154, 37);
		contentPane.add(ExitBut);
		
		//Fetching account name from database (AccountDatabase) via SQL connection which i did earlier
		AccountNam = new JLabel("");
		AccountNam.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				try {
					String query2="select Account from AccountDatabase where Account='"+Login.txtUsername.getText()+"' ";
					
					PreparedStatement pst3;
					pst3 = connection.prepareStatement(query2);
					ResultSet rs=pst3.executeQuery();
			          while (rs.next()) {
		 //Setting the account name to the JLabel in BalancePage
			        AccountNam.setText(rs.getString("Account"));
			 
			          }
			           pst3.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			public void ancestorMoved(AncestorEvent arg0) {
			}
			public void ancestorRemoved(AncestorEvent arg0) {
			}
		});
		AccountNam.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		AccountNam.setForeground(Color.WHITE);
		AccountNam.setBounds(42, 11, 71, 20);
		contentPane.add(AccountNam);
	
		
		Label1 = new JLabel("500");
		Label1.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				//Selecting column "Balance" from database with the	value of JLabel which i set before
				try {
					String query2="select Balance from AccountDatabase where Account='"+HomePage.TheKing.getText()+"'";
					PreparedStatement pst3;
					pst3 = connection.prepareStatement(query2);
					ResultSet rs=pst3.executeQuery();
			          while (rs.next()) {
			    //Setting "Balance" to label to make amount of current balance    	  
			        Label1.setText(rs.getString("Balance"));
			 
			          }
			           pst3.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			public void ancestorMoved(AncestorEvent arg0) {
			}
			public void ancestorRemoved(AncestorEvent arg0) {
			}
		});
		
		Label1.setForeground(Color.GREEN);
		Label1.setBackground(Color.WHITE);
		Label1.setFont(new Font("Tahoma", Font.BOLD, 15));
		Label1.setBounds(278, 62, 71, 27);
		contentPane.add(Label1);
			     	
		JLabel imglbl1 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/euro1.png")).getImage();
		imglbl1.setIcon(new ImageIcon(img));
		imglbl1.setBounds(333, 63, 46, 27);
		contentPane.add(imglbl1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 101, 349, 2);
		contentPane.add(separator);
		
		JLabel Header_1 = new JLabel("Balance page");
		Header_1.setForeground(Color.WHITE);
		Header_1.setFont(new Font("Arial", Font.BOLD, 22));
		Header_1.setBounds(81, 31, 298, 27);
		contentPane.add(Header_1);
		
		JLabel imgSlbl = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/dollar1.png")).getImage();
		imgSlbl.setIcon(new ImageIcon(img1));
		imgSlbl.setBounds(42, 31, 43, 27);
		contentPane.add(imgSlbl);
		
		
		
		JLabel backgroundIMG = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/slide.png")).getImage();
		backgroundIMG.setIcon(new ImageIcon(img4));
		backgroundIMG.setBounds(0, 0, 427, 253);
		contentPane.add(backgroundIMG);
	}
}
