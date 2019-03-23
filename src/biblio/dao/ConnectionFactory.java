package biblio.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class ConnectionFactory {
	private static String defaultJdbcProperties = "jdbc.properties";
	private static String driver, url, user, pwd;
	private static Connection connection;
	
	private ConnectionFactory() {}

	private static void getConnectionData(String connectionDataFileName)
			throws IOException {
		String currKey;
		PropertyResourceBundle propBundle = new PropertyResourceBundle(
				new FileInputStream(new File(connectionDataFileName)));
		Set<?> bundleKeys = propBundle.keySet();
		Iterator<?> iterator = bundleKeys.iterator();
		while (iterator.hasNext()) {
			currKey = (String) iterator.next();
			switch (currKey) {
				case "driver" :
					driver = propBundle.getString(currKey);
					break;
				case "url" :
					url = propBundle.getString(currKey);
					break;
				case "user" :
					user = propBundle.getString(currKey);
					break;
				case "pwd" :
					pwd = propBundle.getString(currKey);
					break;
			}
		}
	}

	public static Connection getDbConnection() throws IOException {
		if (connection != null) {
			return connection;
		} else {
			return getDbConnection(defaultJdbcProperties);
		}
	}

	public static Connection getDbConnection(String connectionDataFileName)
			throws IOException {
		if (connectionDataFileName.equals(defaultJdbcProperties) && connection != null) {
			return connection;
		}
		else {
			getConnectionData(connectionDataFileName);
			if (driver != null && url != null && user != null && pwd != null) {
				return getDbConnection(driver, url, user, pwd);
			}
			else {
				return getDbConnection();
			}
		}
	}

	public static Connection getDbConnection(String driver, String url, String user, String pwd) {
		if (connection != null && ConnectionFactory.driver.equals(driver) && ConnectionFactory.url.equals(url) 
				&& ConnectionFactory.user.equals(user) && ConnectionFactory.pwd.equals(pwd)) {
			return connection;
		}
		else {
			try {
				Class.forName(driver);
				return DriverManager.getConnection(url, user, pwd);
			} catch (SQLException ex) {
				throw new RuntimeException("Error connecting to the database", ex);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Connection getConnectionSansAutoCommit(String driver,
			String url, String user, String pwd) throws SQLException {
		Connection returnConnection = getDbConnection(driver, url, user, pwd);
		returnConnection.setAutoCommit(false);
		return returnConnection;
	}

	public static void main(String[] args) throws IOException {
		// Connection connection = ConnectionFactory.getDbConnection();

	}

}
