package awt;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;


public class FinanaceManager {
	public static void main(String[] args) throws ClassNotFoundException, SQLException,IOException {
		// TODO Auto-generated method stub
		Properties config = DatabaseConfig.loadConfig();
        String url = config.getProperty("DB_URL");
        String user = config.getProperty("DB_USER");
        String pass = config.getProperty("DB_PASS");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection(url, user, pass);
		Statement statement=conn.createStatement();
		statement.execute("CREATE DATABASE finanace");
		

   }
}
