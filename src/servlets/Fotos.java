package servlets;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import helpers.DataHelper;
import helpers.SQLHelper;
import helpers.UserDBConnection;

/**
 * Servlet implementation class Fotos
 */
@WebServlet("/Fotos/*")
public class Fotos extends SQLServlet {
	private String tableName = "product_afbeeldingen"; 
	
	protected Object GetForZeroParameters() throws SQLException {
		return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromWhere(tableName, "hoofd_afbeelding", 1, UserDBConnection.getInstance()));
	}
	
	protected Object GetForOneParameter(int _parameter) throws SQLException {
		return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromWhere(tableName, "product_id", _parameter, UserDBConnection.getInstance()));
	}
}
