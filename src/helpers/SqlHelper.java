package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class SQLHelper {
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//public static ResultSet SelectAllProducts(Connection _connection) throws SQLException {
		//Statement statement = _connection.createStatement();
		//String query = GenerateSelectAllFromLeftOuterJoinQuery("producten", GetForeignKeysInfo("producten", _connection));
		
		//return statement.executeQuery(query);
	//}
	
	public static ResultSet SelectAllFromOuterLeftJoin(String _table, Connection _connection) throws SQLException {
		Statement statement = _connection.createStatement();
		
		String outerLeftJoin = GenerateLeftOuterJoinString(_table, GetForeignKeysInfo(_table, _connection));
		String query = "SELECT * FROM "+_table+outerLeftJoin+";";
		
		LOGGER.finest("Executing query: "+query);
		return statement.executeQuery(query);
	}
	
	public static ResultSet SelectAllFromOuterLeftJoinWhere(String _table, String _column, String _value, Connection _connection) throws SQLException {
		String outerLeftJoin = GenerateLeftOuterJoinString(_table, GetForeignKeysInfo(_table, _connection));
		String query = "SELECT * FROM "+_table+outerLeftJoin+" WHERE "+_table+"."+_column+" = ?;";
		
		PreparedStatement preparedStatement = _connection.prepareStatement(query);
		preparedStatement.setString(1, _value);
		
		LOGGER.finest("Executing query: "+query);
		return preparedStatement.executeQuery();
	}
	
	public static ResultSet SelectAllFromOuterLeftJoinWhere(String _table, String _column, int _value, Connection _connection) throws SQLException {
		String outerLeftJoin = GenerateLeftOuterJoinString(_table, GetForeignKeysInfo(_table, _connection));
		String query = "SELECT * FROM "+_table+outerLeftJoin+" WHERE "+_table+"."+_column+" = ?;";
		
		PreparedStatement preparedStatement = _connection.prepareStatement(query);
		preparedStatement.setInt(1, _value);
		
		LOGGER.finest("Executing query: "+query);
		return preparedStatement.executeQuery();
	}
	
	public static ArrayList<HashMap> GetForeignKeysInfo(String _table, Connection _connection) throws SQLException {
		String query = "SELECT COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
				+ "WHERE CONSTRAINT_NAME IN "
				+ "(SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
				+ "WHERE CONSTRAINT_TYPE = 'FOREIGN KEY' AND TABLE_NAME = '"+_table+"') "
				+ "AND TABLE_NAME = '"+_table+"';";
		
		Statement statement = _connection.createStatement();
		LOGGER.finest("Getting FK info from database from table "+_table);
		ResultSet resultSet = statement.executeQuery(query);
		
		return DataHelper.ConvertResultSetToHashMap(resultSet);
	}
	
	public static String GenerateLeftOuterJoinString(String _table, ArrayList<HashMap> _foreignKeysInfo) {
		String query = "";
		
		int lengthMap = _foreignKeysInfo.size();
		for(int i = 0; i < lengthMap; i++) {
			HashMap info = _foreignKeysInfo.get(i);
			String column = (String) info.get("COLUMN_NAME");
			String refTable = (String) info.get("REFERENCED_TABLE_NAME");
			String refColumn = (String) info.get("REFERENCED_COLUMN_NAME");
			query += " LEFT OUTER JOIN "+ refTable +" ON "+_table+"."+column+" = "+refTable+"."+refColumn;
		}
		
		return query;
	}
	
	public static ResultSet SelectAllFrom(String _table, Connection _connection) throws SQLException {
		Statement statement = _connection.createStatement();
		LOGGER.finest("Executing query: 'SELECT * FROM "+_table+";'");
		return statement.executeQuery("SELECT * FROM "+_table+";");
	}
	
	public static ResultSet SelectAllFromWhere(String _table, String _column, int _value, Connection _connection) throws SQLException {
		PreparedStatement preparedStatement = _connection.prepareStatement("SELECT * FROM "+_table+" WHERE "+_column+" = ?;");
		preparedStatement.setInt(1, _value);
		LOGGER.finest("Executing query: 'SELECT * FROM "+_table+" WHERE "+_column+" = "+_value+";'");
		return preparedStatement.executeQuery();
	}
	
	public static ResultSet SelectAllFromWhere(String _table, String _column, String _value, Connection _connection) throws SQLException {
		PreparedStatement preparedStatement = _connection.prepareStatement("SELECT * FROM "+_table+" WHERE "+_column+" = ?;");
		preparedStatement.setString(1, _value);
		LOGGER.finest("Executing query: 'SELECT * FROM "+_table+" WHERE "+_column+" = "+_value+";'");
		return preparedStatement.executeQuery();
	}
	
	public static ResultSet SelectAllFromWhereAndWhere(String _table, String _column1, int _value1, String _column2, int _value2, Connection _connection) throws SQLException {
		PreparedStatement preparedStatement = _connection.prepareStatement("SELECT * FROM "+_table+" WHERE "+_column1+" = ? AND "+_column2+" = ?;");
		preparedStatement.setInt(1, _value1);
		preparedStatement.setInt(2, _value2);
		LOGGER.finest("Executing query: 'SELECT * FROM "+_table+" WHERE "+_column1+" = "+_value1+"AND "+_column2+" = "+_value2+";'");
		return preparedStatement.executeQuery();
	}
}
