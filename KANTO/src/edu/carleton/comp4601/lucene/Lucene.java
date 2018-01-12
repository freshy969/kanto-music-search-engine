package edu.carleton.comp4601.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.carleton.comp4601.model.Song;
import edu.carleton.comp4601.utils.Constants;
import edu.carleton.comp4601.utils.Logger;

/**
 * @author Yonathan Kidanemariam (100890519).
 */

public class Lucene {
	
	String INDEX_DIR = Constants.INDEX_PATH; //NAME OF INDEX DIRECTORY
	private static Lucene instance;
	
	// Sets an instance of Lucene
	public static void setInstance(Lucene instance) {
		Lucene.instance = instance;
	}
	
	// Gets an instance of Lucene
	public static Lucene getInstance() {
		if(instance == null)
			instance = new Lucene();
		return instance;
	}
	
	public Lucene() {
		
	}
	
	/**
	 * Converts the specified list of Song objects into Lucene Document Objects.
	 * Returns a new list of Lucene Documents.
	 * 
	 * @param List<Song> songs
	 * @return List<Document> luceneDocs
	 */
	public List<Document> convertSongs(List<Song> songs) {
		Logger.d("", 2);
		Logger.d("-----------------------", 2);
		Logger.d("Converting List of Songs to Lucene Documents:", 2);
		Logger.d("", 2);
		
		List<Document> luceneDocs = new ArrayList<Document>();
		
		for(Song song: songs) {
			luceneDocs.add(createDoc(song));
			Logger.d("Document Created!", 2);
			Logger.d("", 2);
		}
		
		Logger.d("CONVERSION COMPLETE!", 2);
		Logger.d("-----------------------", 2);
		
		return luceneDocs;
		
	}
	
	/**
	 * SCreates a new Lucene Document, using the fields of the
	 * specified Song object. 
	 *  
	 * @param Song song
	 * @return Lucene Document
	 */
	public Document createDoc(Song song) {
		
		Logger.d("Coverting Song '" + song.getName() + "' to Lucene Document...", 2);
		Document doc = new Document();
		
		Logger.d("Adding ID field to document: " + song.getId(), 2);
		doc.add(new TextField("id", song.getId().toString(), Field.Store.YES));
	
		Logger.d("Adding ARTIST field to document: " + song.getArtist(), 2);
		doc.add(new TextField("artist", song.getArtist(), Field.Store.YES));
		
		Logger.d("Adding NAME field to document: " + song.getName(), 2);
		doc.add(new TextField("name", song.getName(), Field.Store.YES));
		
		Logger.d("Adding LYRICS field to document: " + song.getLyrics(), 2);
		doc.add(new TextField("lyrics", song.getLyrics(), Field.Store.YES));
		
		return doc;
	}
	
	/**
	 * Insert song list into Lucene index.
	 * 
	 * @param songs the list of songs to insert
	 * @return true if success, false otherwise
	 */
	public boolean indexSongs(List<Song> songs) {
		// Convert Songs tp Docs
		List<Document> docs = convertSongs(songs);
		
		return addToIndex(docs);
	}
	
	/**
	 * Adds specified list of Lucene documents into the Lucene Indexer
	 * 
	 * @param List docs
	 * @return true if documents are successfully added to Lucene Index
	 */
	public boolean addToIndex(List<Document> docs) {
		Logger.d("Adding All Documents to Lucene Index:", 2);
		Logger.d("", 2);
		
		IndexWriter writer = null;
		Directory dir = null;
		
		if(!docs.isEmpty()) {
		
			try {
				dir = FSDirectory.open(new File(INDEX_DIR).toPath());
				
				// Builds and analyzer with the DEFAULT Stop Words (english words not useful for searching)
				Analyzer analyzer = new StandardAnalyzer(); 
				Logger.d("Created Standard Analyzer...", 2);
				
				IndexWriterConfig iwc = new IndexWriterConfig(analyzer); 
				Logger.d("Created IndexWriterConfig...", 2);
				
				iwc.setOpenMode(OpenMode.CREATE);
				Logger.d("Set IWC to openMode...", 2);
				
				// Creates and maintains an Index, using the analyzer, and places it into the index directory
				writer = new IndexWriter(dir, iwc);
				
				for(Document document: docs) {
					writer.addDocument(document);
				}
					
				writer.close();
				
				Logger.d("", 2);
				Logger.d("DOCUMENTS ADDED TO INDEX!", 2);
				Logger.d("---------------------------", 2);
				Logger.d("", 2);
				
				return true;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		
			Logger.d("ERROR: The inputed Document list is empty!", 2);
			Logger.d("", 2);
			return false;
		}
		
		return false;
		
	}
	
	/**
	 * Gets an arraylist of all the lucene documents from the index directory.
	 * 
	 * @return list of lucene documents from the Index directory
	 */
	public ArrayList<Document> getDocuments() {
		
		Logger.d("Getting All Lucene Documents from Index Directory:", 2);
		Logger.d("", 2);
		
		ArrayList<Document> list = new ArrayList<Document>();
		IndexReader reader;
		
		try {
			reader = DirectoryReader.open(
					FSDirectory.open(new File(INDEX_DIR).toPath()));
			Logger.d("Reader Created...", 2);

			Logger.d("Total Documents: " + reader.numDocs(), 2);
			
			for(int i=0; i<reader.numDocs(); i++) {
				Document doc = reader.document(i);
				list.add(doc);
				list.isEmpty();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Logger.d("", 2);
		Logger.d("DOCUMENTS RETRIEVED!", 2);
		Logger.d("----------------------", 2);
		
		return list;
		
	}
	
	/**
	 * Returns true if the specified directory contains no elements
	 * 
	 * @param Directory 
	 * @return true if specified directory contains no elements
	 */
	public boolean isEmpty() {
		IndexReader reader;
		
		try {
			reader = DirectoryReader.open(
					FSDirectory.open(new File(INDEX_DIR).toPath()));
			
			if(reader.numDocs() > 0) {
				Logger.d("Lucene Directory is NOT empty.", 2);
				return true;
			} else {
				Logger.d("Lucene Directory is empty.", 2);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
		
	}
	
	/**
	 * Returns a list of the top queried lucene documents from the index directory, 
	 * using the specified field and string.
	 * @param searchField Lucene document field
	 * @param searchString string being queried 
	 * @return List of Song Objects from query
	 */
	public List<Song> query(String searchField, String searchString) {
		List<Song> hitList = new ArrayList<Song>();
		
		Logger.d("QUERY: " + searchString, 2);
		Logger.d("SEARCH FIELD: " + searchField + ", SEARCH STRING: " + searchString, 2);
		Logger.d("", 2);
		
		IndexReader reader;
		
		try {
			Logger.d("Creating Reader...", 2);
			reader = DirectoryReader.open(
					FSDirectory.open(new File(INDEX_DIR).toPath()));
			
			Logger.d("Creating IndexSearcher...", 2);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			Logger.d("Creating Stantard Analyzer...", 2);
			Analyzer analyzer = new StandardAnalyzer();
			
			QueryParser parser = new QueryParser(searchField, analyzer);
			Query query = parser.parse(searchString);
			
			//Returns top
			TopDocs results = searcher.search(query, 15); // retrieves top 15
			
			ScoreDoc[] hits = results.scoreDocs;
			
			// Display results			
			Logger.d("", 2);
			Logger.d("Found " + hits.length + " hits:", 2);
			for(int i=0; i<hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				
				displayElements(d);
				
				// Convert to new song
				Song song = new Song();
				song.setId(Integer.valueOf(d.get("id")));
				song.setName(d.get("name"));
				song.setArtist(d.get("artist"));
				song.setLyrics(d.get("lyrics"));
				
				// Add to hitList to return all the found documents
				hitList.add(song);
			}
			
			reader.close();
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return hitList;
		
	}
	
	public void displayElements(Document d) {
		Logger.d("ID: " + d.get("id") + ", NAME: " + d.get("name") + " ARTIST: " + d.get("artist"), 2);
	}

	
}

