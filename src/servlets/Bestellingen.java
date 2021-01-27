package servlets;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import helpers.DataHelper;
import helpers.SQLHelper;
import helpers.UserDBConnection;

/**
 * Servlet implementation class Bestellingen
 */
@WebServlet("/Bestellingen/*")
public class Bestellingen extends SQLServlet {
	String tableName = "bestellingen";
	
	protected Object GetForZeroParameters() throws SQLException {
		return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom(tableName, UserDBConnection.getInstance()));
	}
	
	protected Object GetForTwoParameters(String _parameter1, int _parameter2) throws SQLException {
		switch(_parameter1) {
			case "klant":
				return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromWhere(tableName, "klant_id", _parameter2, UserDBConnection.getInstance()));
			default:
				return null;
		}
	}
}
