package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.DatabaseConnection;
import helpers.HttpHelper;
import helpers.SqlHelper;

/**
 * Servlet implementation class Eigenschappen
 */
@WebServlet("/Eigenschappen/*")
public class Eigenschappen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Eigenschappen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathString = request.getPathInfo();
		
		ArrayList<HashMap> data = null;
		
		if(pathString == null || pathString.equals("/")) {
			LOGGER.warning("Bad request!");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} else {
			String[] pathParameters = pathString.split("/");
			if (pathParameters.length == 2) {
				switch(pathParameters[1]) {
				case ("kleuren"):
					try {
						data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFrom("kleuren", DatabaseConnection.getInstance()));
					} catch (SQLException e) {
						LOGGER.warning("Could not fetch kleuren! "+e);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					break;
				case ("materialen"):
					try {
						data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFrom("materialen", DatabaseConnection.getInstance()));
					} catch (SQLException e) {
						LOGGER.warning("Could not fetch materialen! "+e);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					break;
				case ("categorien"):
					try {
						data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFrom("catergorien", DatabaseConnection.getInstance()));
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
