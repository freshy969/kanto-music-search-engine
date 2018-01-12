package edu.carleton.comp4601.utils;

import java.util.List;

import edu.carleton.comp4601.model.Song;

/**
 * @author Hrishi Mukherjee (100888108).
 */

public class HTMLBuilder {
	
	public static final String SEARCH_RESULTS = "SEARCH RESULTS";
	
	public static String buildSearchResultsTable(String query, List<Song> songs) {
		StringBuilder htmlDoc = new StringBuilder();
		
		// BUILD HEADER
		htmlDoc.append("<html>");
		htmlDoc.append("<head><title>" + SEARCH_RESULTS + "</title></head>");
		
		// BUILD BODY
		htmlDoc.append("<body>");
		
		htmlDoc.append("<b><font color=\"royalblue\" size=\"6\" face=\"verdana\">" + "<center>" +
				SEARCH_RESULTS + ": " + query + "</center>" + "</font></b>");
		htmlDoc.append("<br/>");
		htmlDoc.append("<br/>");
		
		htmlDoc.append("<table style=\"width:100%\" border=\"1\">");
		
		// Insert Table Headers
		htmlDoc.append("<tr>");
		htmlDoc.append("<th>" + "<b><font color=\"seagreen\" size=\"4\" face=\"verdana\">" +
				"TITLE" + "</font></b>"  + "</th>");
		htmlDoc.append("<th>" + "<b><font color=\"seagreen\" size=\"4\" face=\"verdana\">" +
				"ARTIST" + "</font></b>"  + "</th>");
		htmlDoc.append("<th>" + "<b><font color=\"seagreen\" size=\"4\" face=\"verdana\">" +
				"LYRICS" + "</font></b>"  + "</th>");
		htmlDoc.append("</tr>");
		
		// Insert Table Rows
		for(Song song: songs) {
			htmlDoc.append("<tr>");
			
			htmlDoc.append("<td align=\"center\">");
			htmlDoc.append(song.getName());
			htmlDoc.append("</td>");
			
			htmlDoc.append("<td align=\"center\">");
			htmlDoc.append(song.getArtist());
			htmlDoc.append("</td>");
			
			htmlDoc.append("<td align=\"center\">");
			htmlDoc.append(song.getLyrics());
			htmlDoc.append("</td>");
			
			htmlDoc.append("</tr>");
		} 
		
		htmlDoc.append("</table>");
		htmlDoc.append("</body>");
		
		return htmlDoc.toString();
	}

}
