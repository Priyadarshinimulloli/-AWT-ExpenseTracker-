package awt;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class DatabaseConfig {
	public static Properties loadConfig() throws IOException{
		 
	
		        Properties prop = new Properties();
		        FileInputStream fis = new FileInputStream(".env"); // Load .env file
		        prop.load(fis);
		        fis.close();
		        return prop;
		
	}

}
