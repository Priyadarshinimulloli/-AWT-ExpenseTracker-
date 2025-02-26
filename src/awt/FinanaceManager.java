package awt;
//import java.awt.*;
//import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;


public class FinanaceManager {
	public static void main(String[] args) throws ClassNotFoundException, SQLException,IOException {
		// TODO Auto-generated method stub
		try {
		Properties config = DatabaseConfig.loadConfig();
		String url = config.getProperty("DB_URL", "").trim();
		String user = config.getProperty("DB_USER", "").trim();
		String pass = config.getProperty("DB_PASS", "").trim();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection(url,user,pass);
		Statement statement=conn.createStatement();
		//statement.execute("CREATE DATABASE finanace IF NOT EXISTS finanace");
		 statement.execute("USE finanace");
		
		String customer="CREATE TABLE IF NOT EXISTS CUSTOMER("
		                 +"id INT AUTO_INCREMENT PRIMARY KEY,"
		                 + "name VARCHAR(255) NOT NULL,"
		                 +"email VARCHAR(255) UNIQUE NOT NULL,"
		                 +"phone VARCHAR(255) UNIQUE NOT NULL"
		                 +")";
		statement.executeUpdate(customer);
		 System.out.println(" CUSTOMER table created successfully!");
		}catch(SQLException e) {
			e.printStackTrace();
		}

   }
}
