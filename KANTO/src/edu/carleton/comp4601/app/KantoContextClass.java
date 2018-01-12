package edu.carleton.comp4601.app;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.carleton.comp4601.utils.Constants;
import edu.carleton.comp4601.utils.Logger;

/**
 * This listens for the web application starting
 * and closing.
 * 
 * @author Hrishi Mukherjee (100888108) &
 * 	       Yonathan Kidanemariam (100890519).
 * 
 */

public class KantoContextClass implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 * We make sure that we stop the running timer which talks to the registry.
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		Date endTime = new Date();
		
		Logger.d("", 0);
		Logger.d("KANTO SERVER CLOSING... ", 0);
		Logger.d("End Time: " + endTime, 0);
		Logger.d("############################################", 0);
		Logger.d("", 0);
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * Any context-param elements in web.xml will have their values saved in the SDAConstants.properties
	 * object which is accessible anywhere within the application.
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		Date startTime = new Date();
		
		// Verify if resource directory exists
		File baseDir = new File(Constants.BASE_DIRECTORY);
		
		if(!baseDir.exists()) {
			System.out.println("BASE DIR does not exist at " + 
					Constants.BASE_DIRECTORY);
			
			// Create Base Directory
			baseDir.mkdir();
			
			// Verify if created
			if(baseDir.exists()) {
				System.out.println("BASE DIR created at " + Constants.BASE_DIRECTORY);
			} else {
				System.out.println("BASE DIR not created.");
			}
		} else {
			System.out.println("BASE DIR exists at " + Constants.BASE_DIRECTORY);
		}
		
		Logger.d("############################################", 0);
		Logger.d("KANTO SERVER STARTING... ", 0);
		Logger.d("Start Time: " + startTime, 0);
		Logger.d("", 0);
	}

}
