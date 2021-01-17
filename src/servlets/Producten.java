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
public class Producten extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathString = request.getPathInfo();
		
		Object data = null;
		
		if(pathString == null || pathString.equals("/")) {
			try {
				data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoin("producten" ,UserDBConnection.getInstance()));
			} catch (SQLException e) {
				LOGGER.warning("Could not fetch producten list! "+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} else {
			String[] pathParameters = pathString.split("/");
			if(pathParameters.length == 3 && StringUtils.isNumeric(pathParameters[2])) {
				switch(pathParameters[1]) {
					case "id":
						try {
							int id = Integer.parseInt(pathParameters[2]);
							data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoinWhere("producten", "product_id", id, UserDBConnection.getInstance()));
							data = DataHelper.ConvertAndUnionResultSetsToHashMaps(
									"informatie", 	SQLHelper.SelectAllFromOuterLeftJoinWhere("producten", "product_id", id, UserDBConnection.getInstance()),
									"fotos", 		SQLHelper.SelectAllFromWhere("product_afbeeldingen", "product_id", id, UserDBConnection.getInstance())
									);
						} catch (SQLException e) {
							LOGGER.warning("Could not fetch product! "+e);
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
						}
						break;
					case "cat":
						try {
							int cat = Integer.parseInt(pathParameters[2]);
							data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromOuterLeftJoinWhere("producten", "categorie_id", cat, UserDBConnection.getInstance()));
						} catch (SQLException e) {
							LOGGER.warning("Could not fetch catergie producten! "+e);
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
						}
						break;
					case "fotos":
						try {
							int productID = Integer.parseInt(pathParameters[2]);
							data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFromWhere("product_afbeeldingen", "product_id", productID, UserDBConnection.getInstance()));
						} catch (SQLException e) {
							LOGGER.warning("Could not fetch foto's of product! "+e);
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
						}
						break;
					default:
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						return;
				}
			} else if (pathParameters.length == 2 && pathParameters[1].equals("fotos")) {
				try {
					data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("product_afbeeldingen", UserDBConnection.getInstance()));
				} catch (SQLException e) {
					LOGGER.warning("Could not fetch foto's producten! "+e);
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			} else {
				LOGGER.warning("Bad request!");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
		
		try {
			HttpHelper.send(request, response, data);
		} catch (IOException e) {
			LOGGER.warning("Could not send data! "+e);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
			return;
		}
	}
}
