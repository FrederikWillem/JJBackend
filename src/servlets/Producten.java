package servlets;

import helpers.DataHelper;
import helpers.HttpHelper;
import helpers.SQLHelper;
import helpers.UserDBConnection;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import java.util.logging.Logger;

/**
 * Servlet implementation class Producten
 */
@WebServlet("/Producten/*")
public class Producten extends SQLServlet {
	private String tableName = "producten";
	
	protected Object GetForZeroParameters() throws SQLException {
		return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoin(tableName ,UserDBConnection.getInstance()));
	}
	
	protected Object GetForOneParameter(int _parameter) throws SQLException {
		return DataHelper.ConvertAndUnionResultSetsToHashMaps(
				"informatie", 	SQLHelper.SelectAllFromOuterLeftJoinWhere(tableName, "product_id", _parameter, UserDBConnection.getInstance()),
				"fotos", 		SQLHelper.SelectAllFromWhere("product_afbeeldingen", "product_id", _parameter, UserDBConnection.getInstance())
				);
		/*
		return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoinWhere("producten", "product_id", id, UserDBConnection.getInstance()));
		*/
	}
	
	protected Object GetForTwoParameters(String _parameter1, int _parameter2) throws SQLException {
		switch(_parameter1) {
			case "categorie":
				return DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoinWhere(tableName, "categorie_id", _parameter2, UserDBConnection.getInstance()));
			default:
				return null;
		}
	}
}
