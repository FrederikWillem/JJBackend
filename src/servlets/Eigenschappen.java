package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.DataHelper;
import helpers.HttpHelper;
import helpers.SQLHelper;
import helpers.UserDBConnection;

/**
 * Servlet implementation class Eigenschappen
 */
@WebServlet("/Eigenschappen/*")
public class Eigenschappen extends SQLServlet {

	protected Object GetForZeroParameters() throws SQLException {
		return DataHelper.ConvertAndUnionResultSetsToHashMaps(
				"categorien", SQLHelper.SelectAllFrom("categorien", UserDBConnection.getInstance()), 
				"kleuren", SQLHelper.SelectAllFrom("kleuren", UserDBConnection.getInstance()), 
				"materialen", SQLHelper.SelectAllFrom("materialen", UserDBConnection.getInstance()));
	}
	
	protected Object GetForOneParameter(String _parameter) throws SQLException {
		switch(_parameter) {
			case "kleuren":
				return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("kleuren", UserDBConnection.getInstance()));
			case "materialen":
				return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("materialen", UserDBConnection.getInstance()));
			case "categorien":
				return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("categorien", UserDBConnection.getInstance()));
			default:
				return null;
		}
	}
}
