package MyFirstGui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import java.awt.Button;
import MyFirstGui.Login;
import MyFirstGui.BalancePage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class Payments extends JFrame {
//Adding default version serial ID
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private JPanel contentPane;
	public  JTextField amount1txt;
	private JTextField receivertxt;
	private JLabel time1;
	private JLabel TheKing4;
	private JLabel lblPayment;
	private JLabel balance12;
	private JLabel transfer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Payments frame = new Payments();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Adding a Date time to Payments-page and making it change the date by itself
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
					
					time1.setText(day+"/"+month+"/"+year);
					
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
	private JSeparator separator;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel label;
	/**
	 * Create the frame.
	 */
	public Payments() {
		setTitle("Otto-Teller simulation");
		Date();
		connection=sqliteConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 289);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Fetching account name from database (AccountDatabase) via SQL connection which i did earlier
		TheKing4 = new JLabel("");
		TheKing4.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				try {
					String query2="select Account from AccountDatabase where Account='"+Login.txtUsername.getText()+"' ";
	
					PreparedStatement pst3;
					pst3 = connection.prepareStatement(query2);
					ResultSet rs=pst3.executeQuery();
			          while (rs.next()) {
			        	  
			        //Setting the account name to the JLabel in Payments-page
			        TheKing4.setText(rs.getString("Account"));
			 
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
		TheKing4.setForeground(Color.WHITE);
		TheKing4.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		TheKing4.setBounds(10, 11, 118, 20);
		contentPane.add(TheKing4);
		
		time1 = new JLabel("Date");
		time1.setForeground(Color.WHITE);
		time1.setBounds(322, 32, 83, 26);
		contentPane.add(time1);
		
		lblPayment = new JLabel("Payment");
		lblPayment.setForeground(Color.WHITE);
		lblPayment.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 20));
		lblPayment.setBounds(10, 28, 108, 26);
		contentPane.add(lblPayment);
		
		JLabel lblNewLabel_3 = new JLabel("Amount:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(10, 80, 118, 30);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Receiver:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(10, 126, 108, 30);
		contentPane.add(lblNewLabel_4);
		
		amount1txt = new JTextField();
		amount1txt.setBounds(110, 87, 101, 20);
		contentPane.add(amount1txt);
		amount1txt.setColumns(10);
		
		receivertxt = new JTextField();
		receivertxt.setBounds(110, 133, 137, 20);
		contentPane.add(receivertxt);
		receivertxt.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("\u20AC");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(234, 90, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		Button confirm = new Button("Confirm");
		confirm.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		confirm.setBackground(Color.GREEN);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				
				
				int num1,num2,ans;
				try {
					//Selecting column "Balance" from database with the	value of JLabel which i set before
					String query2="select Balance from AccountDatabase where Account='"+TheKing4.getText()+"'";
	                    PreparedStatement pst3=connection.prepareStatement(query2);
	                    ResultSet rs=pst3.executeQuery();
	                    while(rs.next())
	                    {
	                    	//Setting "Balance" to label to make amount of current balance
	                        balance12.setText(rs.getString("Balance"));
	                    }
	                    pst3.close();
					
	                //Inserting transaction details to Transaction History table via SQL connection
					Class.forName("org.sqlite.JDBC");
					String query="insert into Transactions(Date,Amount,Type,Account,Receiver)values (?,?,?,?,?)";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, time1.getText());
					pst.setString(2, amount1txt.getText());
					pst.setString(3, lblPayment.getText());
					pst.setString(4, TheKing4.getText());
					pst.setString(5, receivertxt.getText());
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Payment was succesfull!","Completed!",JOptionPane.PLAIN_MESSAGE);
					
					//Calculation which define the final balance
					num2=Integer.parseInt(amount1txt.getText());
					num1=Integer.parseInt(balance12.getText());
					ans=num1-num2;
					balance12.setText(Integer.toString(ans));
					
					//Setting the final balance to another window (BalancePage)	
					dispose();
					BalancePage nf1=new BalancePage();
					BalancePage.Label1.setText(balance12.getText());
					nf1.setVisible(true);
					
					//Updating the final balance to the database (AccountDatabase)
					String query1="Update AccountDatabase set Balance='"+balance12.getText()+"'where Account='"+TheKing4.getText()+"' ";
                    PreparedStatement pst1=connection.prepareStatement(query1);
                    pst1.execute();
                    pst1.close();
                    
                           
                  //Selecting column "Balance" from database with the value of Receiver TextField
                    String query4="select Balance from AccountDatabase where Account='"+receivertxt.getText()+"'";
	                    PreparedStatement pst4=connection.prepareStatement(query4);
	                    ResultSet rs1=pst4.executeQuery();
	                    while(rs1.next())
	                    {
	                    	//Setting "Balance" to another label to make amount of current balance 
	                        transfer.setText(rs1.getString("Balance"));
	                    }
	                    pst4.close();
	                    
	                //Calculation which define how much will be transfered to another account   
	                    num2=Integer.parseInt(amount1txt.getText());
						num1=Integer.parseInt(transfer.getText());
						ans=num1+num2;
						transfer.setText(Integer.toString(ans));
                    
					//Updating the final balance to the database (AccountDatabase)
                    String query5="Update AccountDatabase set Balance='"+transfer.getText()+"'where Account='"+receivertxt.getText()+"' ";
                    PreparedStatement pst5=connection.prepareStatement(query5);
                    pst5.execute();
                    pst5.close();
					
					pst.close();
//					
				} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please insert valid number!","Error!", JOptionPane.ERROR_MESSAGE);
				{
					e.printStackTrace();
				}
				}
			}
		});
		confirm.setBounds(10, 172, 237, 32);
		contentPane.add(confirm);
		
		Button cancel = new Button("Cancel");
		cancel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		cancel.setBackground(Color.PINK);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Payment was canceled!","Canceled!", JOptionPane.ERROR_MESSAGE);
				dispose();
				BalancePage info = new BalancePage();
				info.setVisible(true);
			}
		});
		cancel.setBounds(10, 210, 237, 22);
		contentPane.add(cancel);
		
		Button EXit = new Button("Exit");
		EXit.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		EXit.setBackground(Color.PINK);
		EXit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		EXit.setBounds(288, 210, 117, 22);
		contentPane.add(EXit);
		
		balance12 = new JLabel("500");
		balance12.setBounds(288, 218, 46, 14);
		contentPane.add(balance12);
		
		separator = new JSeparator();
		separator.setBounds(10, 69, 395, 20);
		contentPane.add(separator);
		
		lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/visa.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(278, 80, 146, 109);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/card1.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1.setBounds(110, 26, 37, 32);
		contentPane.add(lblNewLabel_1);
		
		label = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/slide.png")).getImage();
		label.setIcon(new ImageIcon(img4));
		label.setBounds(0, 0, 434, 250);
		contentPane.add(label);
		
		transfer = new JLabel("500");
		transfer.setBounds(190, 32, 46, 14);
		contentPane.add(transfer);
		
	}
}
