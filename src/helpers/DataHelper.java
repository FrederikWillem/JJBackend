package helpers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class DataHelper {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static HashMap<String, ArrayList<HashMap>> ConvertAndUnionResultSetsToHashMaps(String _key1, ResultSet _resultSet1, String _key2, ResultSet _resultSet2) throws SQLException {
		HashMap hashMap = new HashMap<String, ArrayList<HashMap>>();
		
		ArrayList<HashMap> hashMap1 = ConvertResultSetToHashMap(_resultSet1);
		ArrayList<HashMap> hashMap2 = ConvertResultSetToHashMap(_resultSet2);
		
		hashMap.put(_key1, hashMap1);
		hashMap.put(_key2, hashMap2);
		
		return hashMap;
	}
	
	public static HashMap<String, ArrayList<HashMap>> ConvertAndUnionResultSetsToHashMaps(String _key1, ResultSet _resultSet1, String _key2, ResultSet _resultSet2, String _key3, ResultSet _resultSet3) throws SQLException {
		HashMap hashMap = new HashMap<String, ArrayList<HashMap>>();
		
		ArrayList<HashMap> hashMap1 = ConvertResultSetToHashMap(_resultSet1);
		ArrayList<HashMap> hashMap2 = ConvertResultSetToHashMap(_resultSet2);
		ArrayList<HashMap> hashMap3 = ConvertResultSetToHashMap(_resultSet3);
		
		hashMap.put(_key1, hashMap1);
		hashMap.put(_key2, hashMap2);
		hashMap.put(_key3, hashMap3);
		
		return hashMap;
	}
	
	public static ArrayList<HashMap> ConvertResultSetToHashMap(ResultSet _resultSet) throws SQLException {
		//get metadata
		ResultSetMetaData rsmd = _resultSet.getMetaData();
		int amountOfColumns = rsmd.getColumnCount();
		
		//make vars to store columns names and types, and the hashmap
		String[] columnNames = new String[amountOfColumns];
		String[] columnTypes = new String[amountOfColumns];
		ArrayList<HashMap> hashMap = new ArrayList();
		
		//get column names and types
		for (int i = 0; i < amountOfColumns; i++) {
			columnNames[i] = rsmd.getColumnName(i+1);
			int type = rsmd.getColumnType(i+1);
			if(type == Types.VARCHAR || type == Types.CHAR) {
				columnTypes[i] = "String";
			} else if (type == Types.INTEGER || type == Types.BIGINT || type == Types.SMALLINT || type == Types.TINYINT) {
				columnTypes[i] = "Integer";
			}
		}
		
		//go through all the rows in the resultset
		while(_resultSet.next()) {
			// make var to store row values into
			HashMap row = new HashMap();
			//go through all columns
			for(int i = 0 ; i < amountOfColumns; i++) {
				if(columnTypes[i] == "String") {
					//if type of variable is string, get and store string
					row.put(columnNames[i],_resultSet.getString(i+1));
				} else if(columnTypes[i] == "Integer") {
					//if type of variable is int, get and store int
					row.put(columnNames[i], _resultSet.getInt(i+1));
				}
			}
			//store row into return variable
			hashMap.add(row);
		}
		
		return hashMap;
	}
}
