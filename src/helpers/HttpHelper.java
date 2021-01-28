package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Http helper functions to handle sending information and getting information
 * @author Frederik
 *
 */
public class HttpHelper {
	/**
	 * send the data given in obj as response back
	 * @param request The HttpServlerRequest which contains all the request data
	 * @param response The HttpServletResponse with which the response data is made up
	 * @param obj The data to send
	 * @throws IOException due to PrintWriter.print
	 */
	public static void send(HttpServletRequest request, HttpServletResponse response,	Object obj) throws IOException {
		// get accepted responses from request
		String reqAccept = request.getHeader("Accept");
		
		// check if accepts json or html and response accordingly
		if(reqAccept.contains("application/json")) {
			// accepts json send as json
			sendJson(response, obj);
		} else if(reqAccept.contains("text/html")) {
			// if doesnt accept json, but does accept html, send as html
			sendHtml(response, obj);
		} else {
			//else send as json
			sendJson(response, obj);
		}
	}
	
	/**
	 * responses object as json
	 * @param response The HttpServletResponse with which the response data is made up
	 * @param obj The data to send
	 * @throws IOException due to PrintWriter.print
	 */
	private static void sendJson(HttpServletResponse response,	Object obj) throws IOException {
		//get output writer
		PrintWriter out = response.getWriter();
		//set content type as json
		response.setContentType("application/json");
		
		//parse data to json
		String json = JSONValue.toJSONString(obj);
		
		//set headers
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//write to output
		out.print(json);
	}

	/**
	 * responses object as HTML
	 * @param response The HttpServletResponse with which the response data is made up
	 * @param obj The data to send
	 * @throws IOException due to PrintWriter.print
	 */
	private static void sendHtml(HttpServletResponse response,	Object obj) throws IOException {
		//get output writer
		PrintWriter out = response.getWriter();
		//set content type to html
		response.setContentType("text.html");
		
		//parse data to html/string and give html layout
		String json = JSONValue.toJSONString(obj);
		String html = "<html><body><pre><code><h1>JSON response will contain:</h1>";
		html += json;
		html += "</code></pre></body></html>";
		
		//set headers
		response.setContentType("text/html");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//write data to output
		out.print(html);
	}
	
	/**
	 * gets the data from the request body
	 * @param request The HttpServlerRequest which contains all the request data
	 * @return a string containing the request body
	 * @throws IOException, due to the HttpServletRequest.getReader().readLine()
	 */
	public static String getInput(HttpServletRequest request) throws IOException {
		//make var to store input data
		String buffer = new String();
		//make input reader
	    BufferedReader reader = request.getReader();
	    
	    //make var to store one line of input data
	    String line;
	    //read input line if excists
	    while ((line = reader.readLine()) != null) {
	    	//trim line of outer spaces and add to buffer
	        buffer = buffer.concat(line.trim());
	    }
	    
	    //return read input data
	    return buffer;
	}
	
	/**
	 * Prases the given json string to its object.
	 * @param json_str the given string containing json
	 * @return the parsed object of the json string
	 * @throws ParseException, due to JSONParser.parse(str)
	 */
	public static JSONObject parseToJson(String json_str) throws ParseException {
		//get json praser
		JSONParser parser = new JSONParser();
		//make var to store json 
    	JSONObject obj = new JSONObject();
    	
    	//parse data to json
    	obj = (JSONObject) parser.parse(json_str);
    	//return json object
    	return obj;
	}

}
