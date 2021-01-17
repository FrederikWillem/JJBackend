package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class AdminDBConnection {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	// define properties
	private static AdminDBConnection db;
	private Connection dbh;
	
	private String nameDatabase = "jade_jewels";
	private String portDatabase = "3306";
	private String nameUser = "root";
	private String password = "";

	AdminDBConnection() {
		//constructor makes connection and puts class with connection into static db property
		try{
			//the way to make a connection with com.mysql.jdbc package
			Class.forName("com.mysql.jdbc.Driver");
			dbh = DriverManager.getConnection("jdbc:mysql://localhost:"+portDatabase+"/"+nameDatabase,nameUser,password);
			LOGGER.finest("MysqlConnection.constructor: Connection made with DB.");
		} catch(Exception e) {
			LOGGER.severe("MysqlConnection.constructor: Connection error: "+e);
		}
	}
	
	/**
	 * If not made yet, it makes a connection to the database and stores that instance into the static db property.
	 * From then on it will always return the same connection to the database, so not multiple have to be created.
	 * @return always the same connection to the database
	 */
	public static Connection getInstance() {
		// method to call to make connection
		if (!(db instanceof AdminDBConnection)) {
			//if static property not already loaded, then there is not previous connection made
			//so load new object into static db property, which calls the constructor to make a connection
			db = new AdminDBConnection();
		}// else there is already a previous connection made, so no action needed
		
		//return the connection of this static class
		return db.dbh;
	}
}
