package MyFirstGui;
import java.awt.EventQueue;

import net.proteanit.sql.DbUtils;
import MyFirstGui.BalancePage;
import java.sql.*;
import javax.swing.*;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Window.Type;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
public class TransactionHistory {

	private JFrame frmOttotellerSimulation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransactionHistory window = new TransactionHistory();
					window.frmOttotellerSimulation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Connection to the SQL database via sqliteConnection
    Connection connection=null;
    private JTable table;
    
	/**
	 * Create the application.
	 */
	public TransactionHistory() {
		initialize();
		connection=sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOttotellerSimulation = new JFrame();
		frmOttotellerSimulation.setType(Type.POPUP);
		frmOttotellerSimulation.setTitle("Otto-Teller simulation");
		frmOttotellerSimulation.setBounds(100, 100, 525, 337);
		frmOttotellerSimulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOttotellerSimulation.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 479, 181);
		frmOttotellerSimulation.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		Button LoadTable = new Button("Load table");
		LoadTable.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		LoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Importing all the data to the JTable which are in Transactions SQLite-file
				try {
					String query="select * from Transactions";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		LoadTable.setBounds(148, 68, 99, 22);
		frmOttotellerSimulation.getContentPane().add(LoadTable);
		
		Button ExtBut = new Button("Exit");
		ExtBut.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		ExtBut.setBackground(Color.PINK);
		ExtBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		ExtBut.setBounds(390, 68, 99, 22);
		frmOttotellerSimulation.getContentPane().add(ExtBut);
		
		Button GoBBut = new Button("Go back");
		GoBBut.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));
		GoBBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmOttotellerSimulation.dispose();
				BalancePage.main(null);
			}
		});
		GoBBut.setBounds(269, 68, 99, 22);
		frmOttotellerSimulation.getContentPane().add(GoBBut);
		
		JLabel Header2 = new JLabel("Transaction history");
		Header2.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 20));
		Header2.setBounds(148, 31, 331, 31);
		frmOttotellerSimulation.getContentPane().add(Header2);
		
		JLabel lblimg = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/history.png")).getImage();
		lblimg.setIcon(new ImageIcon(img1));
	
		lblimg.setBounds(10, 11, 128, 90);
		frmOttotellerSimulation.getContentPane().add(lblimg);
		
		JLabel Background2img = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		
		//Fetching account name from database (AccountDatabase) via SQL connection which i did earlier
		JLabel AccName = new JLabel("TheKing");
		AccName.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				try {
					String query2="select Account from AccountDatabase where Account='"+Login.txtUsername.getText()+"' ";
					
					PreparedStatement pst3;
					pst3 = connection.prepareStatement(query2);
					ResultSet rs=pst3.executeQuery();
			          while (rs.next()) {
		//Setting the account name to the JLabel in TransactionHistory

			        AccName.setText(rs.getString("Account"));
			 
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
		AccName.setFont(new Font("Arial", Font.BOLD, 12));
		AccName.setBounds(148, 11, 116, 31);
		frmOttotellerSimulation.getContentPane().add(AccName);
		Background2img.setIcon(new ImageIcon(img4));
		Background2img.setBounds(0, 0, 515, 302);
		frmOttotellerSimulation.getContentPane().add(Background2img);
	}
}
