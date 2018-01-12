package edu.carleton.comp4601.crawler;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.carleton.comp4601.dao.ConfigCollection;
import edu.carleton.comp4601.dao.SongCollection;
import edu.carleton.comp4601.model.Config;
import edu.carleton.comp4601.model.Song;
import edu.carleton.comp4601.utils.Constants;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MusicCrawler extends WebCrawler {
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif"
            + "|mp3|mp3|zip|gz))$");
	private static final String URL_RESTRICTION = Constants.BASE_URL_LYRICS;
	
	private static Integer songId = 0;
	private static SongCollection songsDB = null;
	private static ConfigCollection configDB = null;
	
	@Override
	public void onStart() {
		System.out.println("******************");
		System.out.println("Music Crawler: onStart()");
		
		// Get Song Collection
		System.out.println("Initializing DB Collections...");
		songsDB = SongCollection.getInstance();
		configDB = ConfigCollection.getInstance();
		
		// Get Current ID
		System.out.println("Retrieving Previously Saved Song ID...");
		Config query = new Config();
		query.setKey(Constants.DB_CONFIG_CURRENT_ID);
		
		Config result = configDB.find(query);
		
		songId = (Integer) result.getValue();
		
		System.out.println("Current ID: " + songId);
		
		System.out.println("******************");
	}  
	
	@Override
	public void onBeforeExit() {
		System.out.println("******************");
		System.out.println("Music Crawler: onBeforeExit()");
		
		// Update Current ID in DB Config
		System.out.println("Updating DB Config...");
		System.out.println("Saving Current ID...");
		Config currentId = new Config();
		currentId.setKey(Constants.DB_CONFIG_CURRENT_ID);
		currentId.setValue(songId);
		
		System.out.println(currentId);
		
		ConfigCollection.getInstance().update(currentId);
		System.out.println("Current ID Saved.");
		System.out.println("******************");
	} 
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.startsWith(URL_RESTRICTION) && 
				!FILTERS.matcher(href).matches();
	}
	
	@Override
	public void visit(Page page) {
		System.out.println();
		System.out.println("x------------x");
		
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);
		
		if(page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			
			System.out.println("Title: " + htmlParseData.getTitle());
			System.out.println("Number of Outgoing Links: " + 
					htmlParseData.getOutgoingUrls().size());
			
			// If this is not the artist page, then extract song details
			if(!url.equals(Constants.BASE_URL_ARTIST)) {
				System.out.println("\nThis is a lyrics page. Extracting details.");
				
				Document document = null;
				String songTitle = null;
				String lyrics = null;
				
				try {
					// Create a parseable HTML doc
					document = Jsoup.connect(url).get();
					
					System.out.println("\nRetrieving Song Title...");
					songTitle = getSongTitle(document);
					System.out.println("Song Title: " + songTitle);
					
					System.out.println("\nRetrieving Lyrics...");
					lyrics = getLyrics(document);
					System.out.println("Lyrics: " + lyrics);
					
					// Create a new Song
					System.out.println("\nCurrent Song ID: " + songId);
					
					Song song = new Song();
					song.setId(songId);
					song.setName(songTitle);
					song.setArtist(Constants.ARTIST_NAME);
					song.setLyrics(lyrics);
					
					// Add to DB
					System.out.println("\nSong being added to DB:");
					System.out.println(song);
					
					songsDB.add(song);
					
					System.out.println("Added to DB!");
					
					// Update Song ID
					songId++;
					System.out.println("Next Song ID: " + songId);
				} catch (IOException e) {
					System.out.println("JSoup Error!\n");
					e.printStackTrace();
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("\nThis is not a lyrics page. No data to extract.");
			}
		}
		
		System.out.println("x------------x");
		System.out.println();
	}
	
	/**
	 * Retrieves the lyrics from a given
	 * song lyrics page.
	 * 
	 * @param page song lyrics page
	 * @return Extracted Lyrics from the page
	 */
	public static String getLyrics(Document page) {
		String lyrics = null;
		
		// Navigate to 'container main-page' div
		Elements query = page.getElementsByClass("container main-page");
		Element mainContainer = query.first();
		
		// Navigate to 'row' div
		Elements mcChildren = mainContainer.children();
		Element row = mcChildren.first();
		
		// Navigate to 'center' div
		Elements rowChildren = row.children();
		Element center = rowChildren.get(1);
		
		// Navigate to the lyrics div
		Elements centerChildren = center.children();
		
		Elements featElement = centerChildren.select("span.feat");
		
		Element lyricsDiv = null;
		
		if(featElement.size() != 0) {
			System.out.println("Featured Artists Exist");
			System.out.println(featElement.text());
			
			// Get Lyrics Div
			lyricsDiv = centerChildren.get(9);
		} else {
			System.out.println("No Featured Artists");
			
			// Get Lyrics Div
			lyricsDiv = centerChildren.get(7);
		}
		
		lyrics = lyricsDiv.text();
		
		return lyrics;
	}
	
	
	/**
	 * Retrieves the song title from a given
	 * song lyrics page.
	 * 
	 * @param page song lyrics page
	 * @return Extracted Song Title from the page
	 */
	public static String getSongTitle(Document page) {
		String songTitle = null;
		
		// Navigate to 'container main-page' div
		Elements query = page.getElementsByClass("container main-page");
		Element mainContainer = query.first();
		
		// Navigate to 'row' div
		Elements mcChildren = mainContainer.children();
		Element row = mcChildren.first();
		
		// Navigate to 'center' div
		Elements rowChildren = row.children();
		Element center = rowChildren.get(1);
		
		// Navigate to the lyrics div
		Elements centerChildren = center.children();
		Element songTitleRaw = centerChildren.get(4);
		
		songTitle = songTitleRaw.text();
		
		// Clean Up Song Title (Remove Quotes)
		songTitle = songTitle.substring(1, songTitle.length() - 1);
		
		return songTitle;
	}

}
