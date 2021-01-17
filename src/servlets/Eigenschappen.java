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
public class Eigenschappen extends HttpServlet {
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
				data = DataHelper.ConvertAndUnionResultSetsToHashMaps(
						"categorien", SQLHelper.SelectAllFrom("categorien", UserDBConnection.getInstance()), 
						"kleuren", SQLHelper.SelectAllFrom("kleuren", UserDBConnection.getInstance()), 
						"materialen", SQLHelper.SelectAllFrom("materialen", UserDBConnection.getInstance()));
			} catch (SQLException e) {
				LOGGER.warning("Could not fetch eigenschappen! "+e);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		} else {
			String[] pathParameters = pathString.split("/");
			if (pathParameters.length == 2) {
				switch(pathParameters[1]) {
				case ("kleuren"):
					try {
						data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("kleuren", UserDBConnection.getInstance()));
					} catch (SQLException e) {
						LOGGER.warning("Could not fetch kleuren! "+e);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					break;
				case ("materialen"):
					try {
						data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("materialen", UserDBConnection.getInstance()));
					} catch (SQLException e) {
						LOGGER.warning("Could not fetch materialen! "+e);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					break;
				case ("categorien"):
					try {
						data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("categorien", UserDBConnection.getInstance()));
					} catch (SQLException e) {
						LOGGER.warning("Could not fetch kleuren! "+e);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					break;
				default:
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
