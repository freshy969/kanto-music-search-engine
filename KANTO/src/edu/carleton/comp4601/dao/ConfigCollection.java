package edu.carleton.comp4601.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.carleton.comp4601.model.Config;
import edu.carleton.comp4601.utils.Constants;

/**
 * @author Hrishi Mukherjee (100888108).
 */

public class ConfigCollection {
	
	private static ConfigCollection instance;
	
	// MongoDB
	MongoClient mongoClient;
	DB db;
	DBCollection collection;
	
	private ConfigCollection() {
		mongoClient = new MongoClient(Constants.LOCALHOST);
		db = mongoClient.getDB(Constants.DB_NAME);
		collection = db.getCollection(Constants.DB_COLL_CONFIG);
		collection.setObjectClass(Config.class);
	}
	
	public static ConfigCollection getInstance() {
		if(instance == null) {
			instance = new ConfigCollection();
		}
		
		return instance;
	}
	
	public boolean add(Config config) {
		if(!doesExist(config)) {
			collection.insert(config);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean update(Config config) {
		BasicDBObject query = new BasicDBObject("key", config.getKey());
		
		DBObject updatedUser = collection.findAndModify(query, config);
		
		if(updatedUser == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean doesExist(Config config) {
		BasicDBObject query = new BasicDBObject("key", config.getKey());
		
		DBCursor cursor = collection.find(query);
		
		if(cursor.hasNext()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Config find(Config config) {
		BasicDBObject query = new BasicDBObject("key", config.getKey());
		
		DBCursor cursor = collection.find(query);
		
		if(cursor.hasNext()) {
			return (Config) cursor.next();
		} else {
			return null;
		}
	}
	
	public List<Config> findAll() {
		List<Config> configs = new ArrayList<Config>();
		
		DBCursor cursor = collection.find();
		
		while(cursor.hasNext()) {
			configs.add((Config) cursor.next());
		}
		
		return configs;
	}
	
}
