package edu.carleton.comp4601.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.DBObject;

/**
 * @author Yonathan Kidanemariam (100890519).
 */

public class Song implements DBObject {
	String artist;
	String name;
	String lyrics;
	Integer id;
	
	private static final String ID = "id";
	private static final String ARTIST = "artist";
	private static final String NAME = "name";
	private static final String LYRICS = "lyrics";
	
	public Song() {
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getName() {
		return name;
	}

	public String getLyrics() {
		return lyrics;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("ID: " + this.getId() + "; ");
		result.append("Name: " + this.getName() + "; ");
		result.append("Artist: " + this.getArtist() + "; ");
		result.append("Lyrics: " + this.getLyrics() + ";");
		
		return result.toString();
	}

	@Override
	public boolean containsField(String keys) {
		if(keys.equals(ID) || keys.equals(NAME) || keys.equals(LYRICS) 
				|| keys.equals(ARTIST)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKey(String keys) {
		if(keys.equals(ID) || keys.equals(NAME) || keys.equals(LYRICS)
				|| keys.equals(ARTIST)) {
			return true;
		}
		return false;
	}

	@Override
	public Object get(String keys) {
		if(keys.equals(NAME)) {
			return getName();
		} else if(keys.equals(LYRICS)) {
			return getLyrics();
		} else if(keys.equals(ID)) {
			return getId();
		} else if(keys.equals(ARTIST)) {
			return getArtist();
		}
		
		return null;
	}

	@Override
	public Set<String> keySet() {
		Set<String> keySet = new HashSet<String>();
		
		keySet.add(ID);
		keySet.add(NAME);
		keySet.add(LYRICS);
		keySet.add(ARTIST);
		
		return keySet;
	}

	@Override
	public Object put(String key, Object value) {
		if(key.equals(NAME)) {
			setName((String) value);
		} else if(key.equals(LYRICS)) {
			setLyrics((String) value);
		} else if(key.equals(ID)) {
			setId((Integer) value);
		} else if(key.equals(ARTIST)) {
			setArtist((String) value);
		}
		return null;
	}

	@Override
	public void putAll(BSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAll(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object removeField(String key) {
		if(key.equals(NAME)) {
			setName(null);
		} else if(key.equals(LYRICS)) {
			setLyrics(null);
		} else if(key.equals(ID)) {
			setId(null);
		} else if(key.equals(ARTIST)) {
			setArtist(null);
		}
		return null;
	}

	@Override
	public Map toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartialObject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markAsPartialObject() {
		// TODO Auto-generated method stub
		
	}

}

