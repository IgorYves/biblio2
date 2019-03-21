package biblio.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class ConnectionFactory {
	private static HashMap<String, String> connectionData = new HashMap<String, String>();
	private static String defaultJdbcProperties = "jdbc.properties";
	
	public static void getConnectionData(String connectionDataFileName) throws IOException {
		String currKey;
		PropertyResourceBundle propBundle = 
				new PropertyResourceBundle(new FileInputStream(new File(connectionDataFileName)));
		Set<?> bundleKeys = propBundle.keySet();
		Iterator<?> iterator =  bundleKeys.iterator();
		while (iterator.hasNext()) {
				currKey = (String)iterator.next();
				connectionData.put(currKey, propBundle.getString(currKey));
			}
	}
	
	public static Connection getDbConnection() throws IOException {
		return getDbConnection(defaultJdbcProperties);
	}
	
	public static Connection getDbConnection(String connectionDataFileName) throws IOException {
		getConnectionData(connectionDataFileName);
		return getDbConnection(connectionData.get(new String("driver")),
								connectionData.get(new String("url")),
								connectionData.get(new String("user")),
								connectionData.get(new String("pwd")));
	}

	public static Connection getDbConnection(String driver, String url, String user, String pwd) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, pwd);
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection getConnectionSansAutoCommit(String driver, 
								String url, String user, String pwd) throws SQLException {
		Connection returnConnection = getConnectionSansAutoCommit(driver, url, user, pwd);
		returnConnection.setAutoCommit(false);
		return	returnConnection;
	}
	
	public static void main(String[] args) throws IOException {
		//Connection connection = ConnectionFactory.getDbConnection();

	}

}
