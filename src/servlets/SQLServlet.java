package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import helpers.HttpHelper;

/**
 * Servlet implementation class SQLServlet
 */
abstract class SQLServlet extends HttpServlet {
	protected static final long serialVersionUID = 1L;
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DoGetWithSQLException(request, response);
		} catch (SQLException e) {
			LOGGER.warning("Could not get data from database! "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
	
	protected void DoGetWithSQLException(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException, SQLException{
		String pathString = _request.getPathInfo();
		
		Object data = null;
		
		if(pathString == null || pathString.equals("/")) {
			data = GetForZeroParameters();
		} else {
			String[] pathParameters = pathString.split("/");
			
			switch(pathParameters.length - 1) {
				case 1:
					if(StringUtils.isNumeric(pathParameters[1])) {
						int parameter = Integer.parseInt(pathParameters[1]);
						data = GetForOneParameter(parameter);
					} else {
						data = GetForOneParameter(pathParameters[1]);
					}
					
					break;
				case 2:
					if(StringUtils.isNumeric(pathParameters[2])) {
						int parameter2 = Integer.parseInt(pathParameters[2]);
						data = GetForTwoParameters(pathParameters[1], parameter2);
					} else {
						data = GetForTwoParameters(pathParameters[1], pathParameters[2]);
					}
					break;
				default:
					data = null;
					break;
			}
			
		}
		
		if(data != null) {
			HttpHelper.send(_request, _response, data);
		} else {
			_response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	protected Object GetForZeroParameters() throws SQLException {
		return null;
	}
	
	protected Object GetForOneParameter(int _parameter) throws SQLException {
		return null;
	}
	
	protected Object GetForOneParameter(String _parameter) throws SQLException {
		return null;
	}
	
	protected Object GetForTwoParameters(String _parameter1, int _parameter2) throws SQLException {
		return null;
	}
	
	protected Object GetForTwoParameters(String _parameter1, String _parameter2) throws SQLException {
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
