package com.hsbc.meets.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.hsbc.meets.exception.InvalidPropFileException;

/**
 * Reads and stores the SQL connection
 * credentials such as 
 * <li> driver name </li>
 * <li> MySQL database URL </li>
 * <li> Database username </li>
 * <li> Database password </li>
 * 
 * @author alan
 *
 */
public class SQLConnectionCredentials {

	private final static String FILEPATH = "connection.prop";
	
	private String driverName;
	private String url;
	private String username;
	private String password;
	/**
	 * Generates {@link SQLConnectionCredentials} object based
	 * on props file found at constant FILEPATH. 
	 * 
	 * @return SQL connection credentials
	 * @throws InvalidPropFileException if file not found or property not found 
	 */
	public static SQLConnectionCredentials readCredentials() throws InvalidPropFileException {
		try (
			InputStream fis = SQLConnectionCredentials.class.getResourceAsStream(FILEPATH)
		)
		{
			Properties properties = new Properties();

			properties.load(fis);

			String driverName =  properties.getProperty("dname");
			if(driverName == null) {
				throw new InvalidPropFileException("dname");
			}
			String url = properties.getProperty("url");
			if(url == null) {
				throw new InvalidPropFileException("url");
			}
			String username = properties.getProperty("username");
			if(username == null) {
				throw new InvalidPropFileException("username");
			}
			String password = properties.getProperty("password");
			if(password == null) {
				throw new InvalidPropFileException("password");
			}
			return new SQLConnectionCredentials(
				driverName,
				url,
				username,
				password
			);
			
		}catch(IOException e) {
			throw new InvalidPropFileException(e);
		}
		

	}

	/**
	 * Initializes the {@link SQLConnectionCredentials} object
	 * 
	 * @param driverName	The name of JDBC driver to be used
	 * @param url			The URL of the database server followed by the database name
	 * @param username		The username to access database
	 * @param password		The password to access database
	 */
	public SQLConnectionCredentials(String driverName, String url, String username, String password) {
		super();
		this.driverName = driverName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the driver class name
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public static void main(String[] args) {
		try {
			SQLConnectionCredentials cred = readCredentials();
			System.out.println(cred.getDriverName());
		} catch (InvalidPropFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
