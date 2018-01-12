package edu.carleton.comp4601.crawler;

import edu.carleton.comp4601.dao.ConfigCollection;
import edu.carleton.comp4601.model.Config;
import edu.carleton.comp4601.utils.Constants;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	
	public static String crawlSeed = Constants.BASE_URL_ARTIST;
	
	public static int FIXED_DELAY = 20000;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Initializing...");
		
		String crawlStorageFolder = "../data/crawl/root";
		int numberOfCrawlers = 1;
		int maxPagesToFetch = 1000;
		
		System.out.println("Setting Configuration...");
		
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setIncludeBinaryContentInCrawling(true);
		config.setMaxPagesToFetch(maxPagesToFetch);
		config.setMaxDownloadSize(Integer.MAX_VALUE);
		config.setPolitenessDelay(FIXED_DELAY);
		
		System.out.println("Initializing Controller...");
		
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/*
		 * For each crawl, you need to add some seed URLs. These are the
		 * first URLs that are fetched and then the crawler starts following
		 * links which are found in these pages.
		 */
		System.out.println("Adding Seed to Controller...");
		
		controller.addSeed(crawlSeed);
		
		/*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
		System.out.println("Crawl Seed: " + crawlSeed);
		System.out.println("Starting Crawl...");
		
		controller.start(MusicCrawler.class, numberOfCrawlers);
		
		System.out.println("Crawl Completed!"); 
	}
	
	/**
	 * Initializes the Config class.
	 * Should be called before the very first music crawl.
	 */
	public static void initializeConfig() {
		Config currentId = new Config();
		currentId.setKey(Constants.DB_CONFIG_CURRENT_ID);
		currentId.setValue(0);
		
		ConfigCollection.getInstance().add(currentId);
	}

}
