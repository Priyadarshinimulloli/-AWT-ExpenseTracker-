package awt;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class FinanaceManager {
	private static Connection conn;
	public static void main(String[] args) throws ClassNotFoundException, SQLException,IOException {
		// TODO Auto-generated method stub
		try {
		Properties config = DatabaseConfig.loadConfig();
		String url = config.getProperty("DB_URL", "").trim();
		String user = config.getProperty("DB_USER", "").trim();
		String pass = config.getProperty("DB_PASS", "").trim();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		 conn=DriverManager.getConnection(url,user,pass);
		Statement statement=conn.createStatement();
		//statement.execute("CREATE DATABASE finanace IF NOT EXISTS finanace");
		 statement.execute("USE finanace");
		
//		String customer="CREATE TABLE IF NOT EXISTS CUSTOMER("
//		                 +"id INT AUTO_INCREMENT PRIMARY KEY,"
//		                 + "name VARCHAR(255) NOT NULL,"
//		                 +"email VARCHAR(255) UNIQUE NOT NULL,"
//		                 +"phone VARCHAR(255) UNIQUE NOT NULL"
//		                 +")";
//		statement.executeUpdate(customer);
//		 System.out.println(" CUSTOMER table created successfully!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		//Frontend
		final JFrame frame=new JFrame("Finance Manager");
		frame.setSize(600,600);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		//Name
		JLabel name=new JLabel("Name");
		name.setBounds(30,100,100,30);
		frame.add(name);
		final JTextField namefield=new JTextField();
		namefield.setBounds(130,100,100,30);
		frame.add(namefield);
		
		//Email
		JLabel email=new JLabel("Email");
		email.setBounds(30,140,100,30);
		frame.add(email);
		final JTextField emailfield=new JTextField();
		emailfield.setBounds(130,140,100,30);
		frame.add(emailfield);
		
		//phone
		JLabel phone=new JLabel("Phone");
		phone.setBounds(30,180,100,30);
		frame.add(phone);
		final JTextField phonefield=new JTextField();
		phonefield.setBounds(130,180,100,30);
		frame.add(phonefield);
		
		//submit button
		
		JButton button=new JButton("Submit");
		button.setBounds(30,250,160,30);
		frame.add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
				String name=namefield.getText();
				String email=emailfield.getText();
				String phone=phonefield.getText();
				
				if(name.isEmpty()||email.isEmpty()||phone.isEmpty()) {
					JOptionPane.showMessageDialog(frame,"Please fill all the fields!!");
				}
				
				String query="INSERT INTO CUSTOMER(name,email,phone)VALUES(?,?,?)";
				PreparedStatement preparedstatement=conn.prepareStatement(query);
				preparedstatement.setString(1, name);
				preparedstatement.setString(2,email);
				preparedstatement.setString(3, phone);
				preparedstatement.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Customer Added Successfully!");
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
	
		frame.setVisible(true);
		

   }
}
