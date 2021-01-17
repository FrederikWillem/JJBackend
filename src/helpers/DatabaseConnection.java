package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class DatabaseConnection {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	// define properties
	static DatabaseConnection db;
	private Connection dbh;

	DatabaseConnection() {
		//constructor makes connection and puts class with connection into static db property
		try{
			//the way to make a connection with com.mysql.jdbc package
			Class.forName("com.mysql.jdbc.Driver");
			dbh = DriverManager.getConnection("jdbc:mysql://localhost:3306/jade_jewels","root","");
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
		if (!(db instanceof DatabaseConnection)) {
			//if static property not already loaded, then there is not previous connection made
			//so load new object into static db property, which calls the constructor to make a connection
			db = new DatabaseConnection();
		}// else there is already a previous connection made, so no action needed
		
		//return the connection of this static class
		return db.dbh;
	}
}
