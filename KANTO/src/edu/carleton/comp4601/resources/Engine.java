package edu.carleton.comp4601.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import edu.carleton.comp4601.factory.JSONFactory;
import edu.carleton.comp4601.lucene.Lucene;
import edu.carleton.comp4601.model.Song;
import edu.carleton.comp4601.utils.Constants;
import edu.carleton.comp4601.utils.HTMLBuilder;
import edu.carleton.comp4601.utils.Logger;

/**
 * 
 * Entry point into KANTO music search engine.
 * 
 * @author Hrishi Mukherjee (100888108) & 
 * 		   Yonathan Kidanemariam (100890519).
 *
 */

@Path("engine")
public class Engine {
	
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	@Context
	private ServletContext servletContext;
	
	private static final String APP_NAME = "Kanto Music Search Engine";
	
	public Engine() {
		
	}
	
	/**
	 * Returns the name of the web application.
	 * 
	 * @return name of web application.
	 */
	@GET
	public String printName() {
		Logger.d("", 1);
		Logger.d("x---------x", 1);
		Logger.d("Request Type: GET Home Page", 1);
		Logger.d("Request Time: " + new Date(), 1);
		Logger.d("APP NAME: " + APP_NAME, 1);
		Logger.d("Responding...", 1);
		Logger.d("x---------x", 1);
		
		return "Welcome to " + APP_NAME + "!";
	}
	
	/**
	 * Searches the indexed music database for
	 * songs best matching the query. Returns the
	 * top 15 songs represented as a JSON.
	 * 
	 * @param query lyrics query made by the user
	 * @return a JSON message with the search results
	 */
	@GET
	@Path("search/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response processSearchRequestJSON(@PathParam("query") String query) {
		Logger.d("", 1);
		Logger.d("x---------x", 1);
		Logger.d("Request Type: GET Process Search Request (JSON)", 1);
		Logger.d("Request Time: " + new Date(), 1);
		Logger.d("Query: " + query, 1);
		
		Response response = null;
		
		// Retrieve Best Hits for Query
		List<Song> searchResults = new ArrayList<Song>();
		
		Logger.d("\nRetrieving best song matches from DB...", 1);
		searchResults = Lucene.getInstance().query(Constants.FIELD_LYRICS, query);
		
		// Convert Search Results to JSON
		Logger.d("\nConverting to JSON...", 1);
		
		JSONObject json = JSONFactory.getJSON(searchResults);
		
		// Add extra response fields to JSON
		try {
			json.put(Constants.FIELD_TYPE, Constants.TYPE_SEARCH_RESULTS);
			json.put(Constants.FIELD_QUERY, query);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		// Convert JSON to String
		String jsonString = null;
		
		try {
			jsonString = json.toString(4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Logger.d(jsonString, 1);
		
		// Set Response Object
		response = Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
		
		Logger.d("Responding...", 1);
		Logger.d("x---------x", 1);
		
		return response;
	}
	
	/**
	 * Searches the indexed music database for
	 * songs best matching the query. Returns the
	 * top 15 songs represented as an HTML.
	 * 
	 * @param query lyrics query made by the user
	 * @return an HTML representation with the search results
	 */
	@GET
	@Path("search/{query}")
	@Produces(MediaType.TEXT_HTML)
	public Response processSearchRequestHTML(@PathParam("query") String query) {		
		Logger.d("", 1);
		Logger.d("x---------x", 1);
		Logger.d("Request Type: GET Process Search Request (HTML)", 1);
		Logger.d("Request Time: " + new Date(), 1);
		Logger.d("Query: " + query, 1);
		
		Response response = null;
		
		// Retrieve Best Hits for Query
		List<Song> searchResults = new ArrayList<Song>();
		
		Logger.d("\nRetrieving best song matches from DB...", 1);
		searchResults = Lucene.getInstance().query(Constants.FIELD_LYRICS, query);
		
		// Convert Search Results to HTML
		Logger.d("\nCreating HTML...", 1);
		
		String html = HTMLBuilder.buildSearchResultsTable(query, searchResults);
		
		// Set Response Object
		response = Response.ok(html, MediaType.TEXT_HTML).build();
		
		Logger.d("Responding...", 1);
		Logger.d("x---------x", 1);
		
		return response;
	}

}
