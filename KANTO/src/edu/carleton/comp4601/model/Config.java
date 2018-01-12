package edu.carleton.comp4601.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.BSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * @author Hrishi Mukherjee (100888108).
 */

@XmlRootElement
public class Config implements DBObject {
	private String key;
	private Integer value;
	
	/*
	 * MONGO DB KEYS
	 */
	private static final String KEY = "key";
	private static final String VALUE = "value";

	public Config() {
		this.key = null;
		this.value = null;
	}
	
	public String getKey() { return this.key; }
	public Integer getValue() { return this.value; }

	
	public void setKey(String key) { this.key = key; }
	public void setValue(Integer value) { this.value = value; }

	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Key: " + this.getKey() + "; ");
		result.append("Value: " + this.getValue() + "; ");
		
		return result.toString();
	}
	
	/*
	 * MONGO FUNCTIONS
	 */
	@Override
	public boolean containsField(String key) {
		//System.out.println("containsField()");
		
		if(key.equals(KEY) || key.equals(VALUE)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean containsKey(String key) {
		//System.out.println("containsKey(" + key + ")");
		
		if(key.equals(KEY) || key.equals(VALUE)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object get(String key) {
		//System.out.println("get(" + key + ")");
		
		if(key.equals(KEY)) {
			return getKey();
		} else if(key.equals(VALUE)) {
			return getValue();
		} else {
			return null;
		}
	}

	@Override
	public Set<String> keySet() {
		//System.out.println("keySet()");
		
		Set<String> keySet = new HashSet<String>();
		
		keySet.add(KEY);
		keySet.add(VALUE);
		
		return keySet;
	}

	@Override
	public Object put(String key, Object value) {
		//System.out.println("put(" + key + ", " + value.getClass() + ")");
		
		if(key.equals(KEY)) {
			setKey((String) value);
		} else if(key.equals(VALUE)) {
			setValue((Integer) value);
		} 
		
		return value;
	}

	@Override
	public void putAll(BSONObject arg0) {
		//System.out.println("putAllBSON()");
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAll(Map arg0) {
		//System.out.println("putAllMap()");
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object removeField(String key) {
		//System.out.println("removeField(" + key + ")");
		
		if(key.equals(KEY)) {
			setKey(null);
		} else if(key.equals(VALUE)) {
			setValue(null);
		} 
		
		return null;
	}

	@Override
	public Map toMap() {
		//System.out.println("toMap()");
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartialObject() {
		//System.out.println("isPartialObject()");
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markAsPartialObject() {
		//System.out.println("markAsPartialObject()");
		
		// TODO Auto-generated method stub
		
	}
}
