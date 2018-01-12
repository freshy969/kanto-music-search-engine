package edu.carleton.comp4601.factory;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.carleton.comp4601.model.Song;
import edu.carleton.comp4601.utils.Constants;

/**
 * @author Hrishi Mukherjee (100888108).
 */

public class JSONFactory {
	
	/**
	 * Transforms the list of songs
	 * into a JSON object.
	 * 
	 * @param songs to be appended to JSON
	 * @return JSON Object containing array of songs
	 */
	public static JSONObject getJSON(List<Song> songs) {
		JSONObject json = new JSONObject();
		
		// Put each song into JSON
		for(Song song: songs) {
			JSONObject songJSON = getJSON(song);
			
			try {
				json.append(Constants.FIELD_RESULTS, songJSON);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	/**
	 * Transforms the song's details into
	 * a JSON object.
	 * 
	 * @param song to be transformed to JSON
	 * @return JSON Object containing song details
	 */
	public static JSONObject getJSON(Song song) {
		JSONObject json = new JSONObject();
		
		try {
			json.put(Constants.FIELD_NAME, song.getName());
			json.put(Constants.FIELD_ARTIST, song.getArtist());
			json.put(Constants.FIELD_LYRICS, song.getLyrics());
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}

}
