package edu.carleton.comp4601.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import edu.carleton.comp4601.model.Song;
import edu.carleton.comp4601.utils.Constants;

/**
 * @author Yonathan Kidanemariam (100890519) &
 * 		   Hrishi Mukherjee (100888108).
 */

public class SongCollection {

	private static SongCollection instance;
	
	public static void setInstance(SongCollection instance) {
		SongCollection.instance = instance;
	}
	
	public static SongCollection getInstance() {
		if(instance == null)
			instance = new SongCollection();
		return instance;
	}
	
	// MongoDB
	private static MongoClient mongoClient;
	private static DB database;
	private static DBCollection songColl;
	
	private SongCollection() {
		mongoClient = new MongoClient(Constants.LOCALHOST);
		
		System.out.println("Creating Kanto Database...");
		database = mongoClient.getDB(Constants.DB_NAME);

		System.out.println("Creating Songs Collection...");
		songColl = database.getCollection(Constants.DB_COLL_SONGS);
		
		songColl.setObjectClass(Song.class);
	}
	
	public void add(Song song) {
		System.out.println("Adding Song to Database | Id: " 
				+ song.getId() + " Name: " 
				+ song.getName());
		
		songColl.insert(song);
	}
	
	public Song find(Integer id) {
		Song song = null;
		
		System.out.println("Finding song...");
		
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = songColl.find(query);
		
		if(cursor.hasNext()) {
			song = (Song) cursor.next();
		}
		
		if(song != null) {
			System.out.println("Song " + id + " Found!");
		}
		
		return song;
		
	}
	
	public List<Song> getAllSongs() {
		System.out.println("Retrieving All Songs...");
		
		List<Song> songList = new ArrayList<Song>();
		Song song = null;
		
		DBCursor cursor = songColl.find();
		
		System.out.println("Retrieved! Adding songs to list...");
		
		while(cursor.hasNext()) {
			song = (Song) cursor.next();
			songList.add(song);
		}
		
		System.out.println("Returning Song List...");
		
		return songList;
	}
	
}

