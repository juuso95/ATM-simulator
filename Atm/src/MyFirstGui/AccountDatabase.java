package MyFirstGui;

import java.awt.EventQueue;
import java.awt.Color;
import java.sql.*;
import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import MyFirstGui.BalancePage;
import java.awt.SystemColor;
import javax.swing.table.DefaultTableModel;
public class AccountDatabase {

	private JFrame frmAsd;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountDatabase window = new AccountDatabase();
					window.frmAsd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Connection to the SQL database  via sqliteConnection
	Connection connection=null;
	public static JTable table;
	private JTextField Firstname;
	private JTextField Lastname;
	private JTextField age;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField id;
	private JTextField accountn;
	private JPasswordField password;
	private JTextField gender;
	/**
	 * Create the application.
	 */
	public AccountDatabase() {
		initialize();
		connection=sqliteConnection.dbConnector();
}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAsd = new JFrame();
		frmAsd.setFont(new Font("Arial", Font.PLAIN, 12));
		frmAsd.setTitle("Otto-Teller simulation");
		frmAsd.setBackground(SystemColor.desktop);
		frmAsd.setForeground(Color.BLACK);
		frmAsd.getContentPane().setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 11));
		frmAsd.setBounds(100, 100, 565, 410);
		frmAsd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAsd.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 195, 450, 164);
		frmAsd.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
		
		Button Update = new Button("Update");
		Update.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		Update.addActionListener(new ActionListener() {
		//Making "YES - NO" message	
			public void actionPerformed(ActionEvent arg0) {
				int action=JOptionPane.showConfirmDialog(null, "Are you sure that you want to continue updating?","Update!",JOptionPane.YES_NO_OPTION);
				if (action==0) {
					
				try {  
					//Updating the data of the table by entering text to fields and pressing Update-button
                    String query="Update AccountDatabase set ID='"+id.getText()+"',Firstname='"+Firstname.getText()+"'" +",Lastname='"+Lastname.getText()+"'" +",age='"+age.getText()+"'where ID='"+id.getText()+"' ";
                    PreparedStatement pst=connection.prepareStatement(query);
                    pst.execute();
                    
                    //Clear the textFields after clicking a button
                    id.setText(null);
					Firstname.setText(null);
					Lastname.setText(null);
					accountn.setText(null);
					password.setText(null);
					age.setText(null);
					gender.setText(null);
                    
                    JOptionPane.showMessageDialog(null, "Data Updated successfully!"+"\n(Remember to type ID, otherwise the data wont be updated!)"+"\nps. You can not update your Account, Password, Gender or ID.","Completed!",JOptionPane.PLAIN_MESSAGE);
                    pst.close();
                }
                catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Please insert valid data!"+"\n(The Field ID is mandatory)","Error!", JOptionPane.ERROR_MESSAGE);
                {
                    e.printStackTrace();
                }
                }
				}
			}
			});
		Update.setBounds(466, 90, 70, 35);
		frmAsd.getContentPane().add(Update);
		
		Button Delete = new Button("Delete");
		Delete.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action=JOptionPane.showConfirmDialog(null, "Are you sure that you want to continue deleting?","Delete",JOptionPane.YES_NO_OPTION);
				if (action==0) {
				try {
					//Deleting the selected data from table by typing ID to textFields and pressing Delete-button
                    String query="delete from AccountDatabase where ID='"+id.getText()+"'";
                    PreparedStatement pst=connection.prepareStatement(query);
                    pst.execute();
                    
                    //Clear the textFields after clicking a button
                    id.setText(null);
					Firstname.setText(null);
					Lastname.setText(null);
					accountn.setText(null);
					password.setText(null);
					age.setText(null);
					gender.setText(null);
                    
                    JOptionPane.showMessageDialog(null, "Data deleted successfully!"+"\n(Remember to type ID, otherwise the data wont be deleted!)","Completed!",JOptionPane.PLAIN_MESSAGE);
                    pst.close();
                }
                catch(Exception ex){
                	JOptionPane.showMessageDialog(null, "Please insert valid data!"+"\n(The Field ID is mandatory)","Error!", JOptionPane.ERROR_MESSAGE);
                
				
                {
                    ex.printStackTrace();
                }
                }
                }
			}    
});
		Delete.setBounds(466, 131, 70, 35);
		frmAsd.getContentPane().add(Delete);
		
		Button LoadTable = new Button("Load table");
		LoadTable.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		LoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Importing all the data to the JTable which are in AccoundDatabase SQLite-file
				try {
					Class.forName("org.sqlite.JDBC");
					String query="select * from AccountDatabase";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		});
		LoadTable.setBounds(466, 195, 70, 54);
		frmAsd.getContentPane().add(LoadTable);
		
		JLabel Headerr = new JLabel("Account database");
		Headerr.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 20));
		Headerr.setBounds(46, 0, 210, 39);
		frmAsd.getContentPane().add(Headerr);
		
		Firstname = new JTextField();
		Firstname.setBounds(93, 78, 86, 20);
		frmAsd.getContentPane().add(Firstname);
		Firstname.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Firstname:");
		lblNewLabel_1.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 81, 77, 14);
		frmAsd.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Lastname:");
		lblNewLabel_2.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 117, 78, 14);
		frmAsd.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Age:");
		lblNewLabel_3.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 157, 77, 14);
		frmAsd.getContentPane().add(lblNewLabel_3);
		
		Lastname = new JTextField();
		Lastname.setBounds(93, 109, 86, 22);
		frmAsd.getContentPane().add(Lastname);
		Lastname.setColumns(10);
		
		age = new JTextField();
		age.setBounds(93, 154, 86, 20);
		frmAsd.getContentPane().add(age);
		age.setColumns(10);
		
		Button Goback = new Button("Go back");
		Goback.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		Goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmAsd.dispose();
				BalancePage.main(null);
				
			}
			
		});
		
		Goback.setBounds(466, 269, 70, 39);
		frmAsd.getContentPane().add(Goback);
		
		
		Button Add = new Button("Add");
		Add.setBackground(Color.GREEN);
		Add.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		Add.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {	
					//Adding new data to table by entering text to and pressing Add-button
					Class.forName("org.sqlite.JDBC");
					String query="insert into AccountDatabase(ID,Firstname,Lastname,Account,Password,Age,Gender) values (?,?,?,?,?,?,?)";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, id.getText());
					pst.setString(2, Firstname.getText());
					pst.setString(3, Lastname.getText());
					pst.setString(4, accountn.getText());
					pst.setString(5, password.getText());
					pst.setString(6, age.getText());
					pst.setString(7, gender.getText());
					
					pst.execute();
					
					//Clear the textFields after clicking a button
					id.setText(null);
					Firstname.setText(null);
					Lastname.setText(null);
					accountn.setText(null);
					password.setText(null);
					age.setText(null);
					gender.setText(null);
					
					
					JOptionPane.showMessageDialog(null, "You succesfully added new details!","Completed!",JOptionPane.PLAIN_MESSAGE);
					
					
					
					pst.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Please insert valid data!"+"\n(The Field ID is mandatory)","Error!", JOptionPane.ERROR_MESSAGE);
				{
					e.printStackTrace();
				}
					
				}	
				
			}
		});
		Add.setBounds(466, 50, 70, 35);
		frmAsd.getContentPane().add(Add);
		
		JLabel lblNewLabel_4 = new JLabel("ID:");
		lblNewLabel_4.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 50, 72, 14);
		frmAsd.getContentPane().add(lblNewLabel_4);
		
		id = new JTextField();
		id.setBounds(93, 47, 86, 20);
		frmAsd.getContentPane().add(id);
		id.setColumns(10);
		
		Button Ext = new Button("Exit");
		Ext.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		Ext.setBackground(Color.PINK);
		Ext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		Ext.setBounds(466, 315, 70, 41);
		frmAsd.getContentPane().add(Ext);
		
		JLabel lblNewLabel_5 = new JLabel("Account:");
		lblNewLabel_5.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(231, 50, 70, 14);
		frmAsd.getContentPane().add(lblNewLabel_5);
		
		accountn = new JTextField();
		accountn.setBounds(318, 47, 86, 20);
		frmAsd.getContentPane().add(accountn);
		accountn.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Password:");
		lblNewLabel_6.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(231, 81, 77, 14);
		frmAsd.getContentPane().add(lblNewLabel_6);
		
		password = new JPasswordField();
		password.setBounds(318, 78, 86, 20);
		frmAsd.getContentPane().add(password);
		
		gender = new JTextField();
		gender.setBounds(318, 111, 86, 20);
		frmAsd.getContentPane().add(gender);
		gender.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		lblGender.setBounds(231, 117, 70, 14);
		frmAsd.getContentPane().add(lblGender);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 182, 526, 2);
		frmAsd.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 35, 526, 2);
		frmAsd.getContentPane().add(separator_1);
		
		JLabel lblNewLabel_7 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/detail.png")).getImage();
		lblNewLabel_7.setIcon(new ImageIcon(img1));
		lblNewLabel_7.setBounds(10, 0, 38, 39);
		frmAsd.getContentPane().add(lblNewLabel_7);
		
		JLabel label = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/back3.png")).getImage();
		label.setIcon(new ImageIcon(img4));
		label.setBounds(0, 0, 547, 371);
		frmAsd.getContentPane().add(label);
	}
}
