package edu.carleton.comp4601.utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author Hrishi Mukherjee (100888108).
 */

public class Logger {
	
	private static final int LIMIT = 
			Constants.LOGGING_LIMIT;
	
	private static final String DUMP_FILE = 
			Constants.DUMP_FILE_PATH;
	
	private static final boolean APPEND_MODE = true;
	
	/**
	 * Logs a debug message to the
	 * console and to the dump file.
	 * 
	 * Only logs the messages which fall
	 * within the logging depth specified by user.
	 * This can be used to control how much detail
	 * of logs are being outputted during the
	 * debugging process.
	 * 
	 * @param message debugging message
	 * @param depth the depth relative to the application for this message
	 */
	public static void d(String message, int depth) {
		// If depth is less than limit, log the message
		if(depth <= LIMIT) {
			// Print to console
			if(Constants.DEBUG_MODE) {
				System.out.println(message);
			}
			
			// Output to file
			if(Constants.LOG_MODE) {
				dump(message);
			}
		}
	}
	
	/**
	 * Dumps message to output file.
	 * 
	 * @param message
	 */
	public static void dump(String message) {
		try {
			PrintWriter writer = 
					new PrintWriter(new FileWriter(DUMP_FILE, APPEND_MODE));
			
			writer.println(message);
			
			writer.flush();
			writer.close();
		} catch(IOException e) { 
			e.printStackTrace();
		}
	}

}
