package servlets;

import helpers.DatabaseConnection;
import helpers.HttpHelper;
import helpers.SqlHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
     * @see HttpServlet#HttpServlet()
     */
    public Producten() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String pathString = request.getPathInfo();
		
		ArrayList<HashMap> data = null;
		
		if(pathString == null || pathString.equals("/")) {
			try {
				data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFromOuterLeftJoin("producten" ,DatabaseConnection.getInstance()));
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
							data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFromOuterLeftJoinWhere("producten", "product_id", id, DatabaseConnection.getInstance()));
						} catch (SQLException e) {
							LOGGER.warning("Could not fetch product! "+e);
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
						}
						break;
					case "cat":
						try {
							int cat = Integer.parseInt(pathParameters[2]);
							data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFromOuterLeftJoinWhere("producten", "categorie_id", cat, DatabaseConnection.getInstance()));
						} catch (SQLException e) {
							LOGGER.warning("Could not fetch catergie producten! "+e);
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
						}
						break;
					case "fotos":
						try {
							int productID = Integer.parseInt(pathParameters[2]);
							data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFromWhere("product_afbeeldingen", "product_id", productID, DatabaseConnection.getInstance()));
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
					data = SqlHelper.ConvertResultSetToHashMap(SqlHelper.SelectAllFrom("product_afbeeldingen", DatabaseConnection.getInstance()));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
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
