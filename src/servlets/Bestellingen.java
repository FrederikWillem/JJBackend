package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import helpers.DataHelper;
import helpers.HttpHelper;
import helpers.SQLHelper;
import helpers.UserDBConnection;

/**
 * Servlet implementation class Bestellingen
 */
@WebServlet("/Bestellingen/*")
public class Bestellingen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doGetWithSQLException(request, response);
		} catch (SQLException e) {
			LOGGER.warning("Could not get data from database! "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
	
	protected void doGetWithSQLException(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String pathString = request.getPathInfo();
		
		Object data = null;
		
		if(pathString == null || pathString.equals("/")) {
			data = DataHelper.ConvertResultSetToHashMap(SQLHelper.SelectAllFrom("bestellingen", UserDBConnection.getInstance()));
		} else {
			String[] pathParameters = pathString.split("/");
			if (pathParameters.length == 3 && StringUtils.isNumeric(pathParameters[2])) {
				switch(pathParameters[1]) {
				case ("klant"):
					
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
