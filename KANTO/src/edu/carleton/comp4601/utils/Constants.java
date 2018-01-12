package edu.carleton.comp4601.utils;

/**
 * @author Hrishi Mukherjee (100888108) & 
 * 		   Yonathan Kidanemariam (100890519).
 */

public class Constants {
	
	/*
	 * APPLICATION
	 */
	public static final String BASE_DIRECTORY = "C:/Users/Hreeels/kanto-resources";
	
	/*
	 * CRAWLING
	 */
	public static final String CATEGORY         = "j";
	public static final String ARTIST           = "john";
	
	public static final String LYRICS           = "lyrics";
	public static final String BASE_URL         = "http://www.azlyrics.com";
	public static final String BASE_URL_ARTIST  = BASE_URL + "/" + CATEGORY + "/" + ARTIST + ".html";
	public static final String BASE_URL_LYRICS  = BASE_URL + "/" + LYRICS + "/" + ARTIST;
	
	// Artist Name to insert in DB
	public static final String ARTIST_NAME      = "Elton John";
	
	/*
	 * DATABASE (MONGODB)
	 */
	public static final String DB_NAME              = "kanto";
	public static final String LOCALHOST            = "localhost";
	
	// Config Collection
	public static final String DB_COLL_CONFIG       = "config";
	public static final String DB_CONFIG_CURRENT_ID = "current_id";
	
	// Songs Collection
	public static final String DB_COLL_SONGS        = "songs";
	
	/*
	 * DATABASE (LUCENE)
	 */
	public static final String INDEX_PATH = "C:/Users/Hreeels/kanto-resources/lucene-index";
	
	/*
	 * FIELDS
	 */
	public static final String FIELD_NAME          = "name";
	public static final String FIELD_ARTIST        = "artist";
	public static final String FIELD_LYRICS        = "lyrics";
	
	public static final String FIELD_RESULTS       = "results";
	public static final String FIELD_TYPE          = "type";
	public static final String FIELD_QUERY         = "query";
	
	public static final String TYPE_SEARCH_RESULTS = "SEARCH_RESULTS";
	
	/*
	 * LOGGING
	 */
	public static final int LOGGING_LIMIT = 2;
	public static final boolean DEBUG_MODE = true;
	public static final boolean LOG_MODE = true;
	
	public static final String DUMP_FILE_PATH = BASE_DIRECTORY + "/log.txt";

	private Constants() {
		throw new RuntimeException();
	}

}
