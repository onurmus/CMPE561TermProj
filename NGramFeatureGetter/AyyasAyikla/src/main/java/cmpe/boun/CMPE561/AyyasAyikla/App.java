package cmpe.boun.CMPE561.AyyasAyikla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Hello world!
 *
 */
public class App 
{
	//keep bigrams in the form word1--word2 : count
	static Map<String,Integer> biGramMap;
	
	//keep unigrams in the form word : count
	static Map<String,Integer> uniGramMap;

	//drunk features in the form feature : count
	static Map<String,Double> drunkFeatures;

	//sober features in the form feature : count
	static Map<String,Double> soberFeatures;
	
    public static void main( String[] args ) throws SQLException, FileNotFoundException, UnsupportedEncodingException
    {
    	biGramMap = new HashMap<String,Integer>();
    	uniGramMap = new HashMap<String,Integer>();
    	
    	DBConnection conn = new DBConnection();
		conn.startConnection();
		
		ResultSet ds1Rs = conn.getDataSet2Sober();
		
		System.out.println("db den okudum");
		
		int count = 0;
		while(ds1Rs.next()){
			String tweetStr = ds1Rs.getString("tweet");
			String[] parsedStrings = parseTweet(tweetStr);
			
			for(int i =0 ; i< parsedStrings.length; i++){
				if(uniGramMap.containsKey(parsedStrings[i])){
					uniGramMap.put(parsedStrings[i],uniGramMap.get(parsedStrings[i])+1);
				}else{
					uniGramMap.put(parsedStrings[i],1);
				}
				
				if(i<parsedStrings.length-1){
					String biGramToken = parsedStrings[i] + "-"+parsedStrings[i+1];
					
					if(biGramMap.containsKey(biGramToken)){
						biGramMap.put(biGramToken,biGramMap.get(biGramToken)+1);
					}else{
						biGramMap.put(biGramToken,1);
					}
				}
				
			}
			
			count++;
		}
		
		System.out.println("parse işlemi bitti size is " + count);
		
		printHashMaps("ds2Sober");
		
		System.out.println(" bitti ");
		
    }
    
    private static void printHashMaps(String ds) throws FileNotFoundException, UnsupportedEncodingException{
    	PrintStream out = new PrintStream(new File("bigrams"+ds+".txt"),"UTF-8");
		for(Map.Entry<String,Integer> entry : biGramMap.entrySet()){
			out.print(entry.getKey() + " " + entry.getValue());
			out.println();
		}
		out.close();
		
		PrintStream out2 = new PrintStream(new File("unigrams"+ds+".txt"),"UTF-8");
		for(Map.Entry<String,Integer> entry : uniGramMap.entrySet()){
			out2.print(entry.getKey() + " " + entry.getValue());
			out2.println();
		}
		
		out2.close();
    }
    
    
    public static String[] parseTweet(String tweet){
    	String[] tokens = tweet.split("\\s");
    	
    	for(int i=0; i< tokens.length; i++){
    		//make it lowercase
    		tokens[i] = tokens[i].toLowerCase();
    		
    		//if token is a mention with @ , then make it empty string
    		if(tokens[i].startsWith("@")){
    			tokens[i] = "";
    		}
    		
    		//if token contains a hyperlink then remove it
    		if(tokens[i].contains("http") || tokens[i].contains("https")){
    			tokens[i] = "";
    		}
    		
    		//if token contains any of the indicating hashtag remove hashtag
    		if(tokens[i].contains("#drank") ||tokens[i].contains("#drunk") ||tokens[i].contains("#imdrunk") 
    				||tokens[i].contains("#notdrunk") ||tokens[i].contains("#imnotdrunk") ||tokens[i].contains("#sober")){
    			tokens[i] = "";
    		}
    		
    		//if contains - then remove it
    		if(tokens[i].contains("-")){
    			tokens[i] = tokens[i].replace("-", "");
    		}
    		
    		//if contains # then remove it
    		if(tokens[i].contains("#")){
    			tokens[i] = tokens[i].replace("#", "");
    		}
    	}

    	ArrayList<String> parseList = new ArrayList<String>();
    	for(int i=0; i< tokens.length; i++){
    		if(!tokens[i].trim().equals("")){
    			parseList.add(tokens[i]);
    		}
    	}
    	String []dsf = new String[parseList.size()];
    	parseList.toArray(dsf);

    	return dsf;
    }
    
    public static HashMap<String,Integer> parseTweetFeatures(String tweet){
    	String[] tokens = tweet.split("\\s");

    	HashMap<String,Integer> features = new HashMap<String,Integer>();

    	int hasRepeatedCharacters = 0; //Boolean feature indicating whether a character is repeated three times consecutively. (0 means False, 1 means True)
    	int capitalCount = 0; //Number of capital letters in the tweet
    	int length = 0; //Number of words in the tweet
    	int hasEmoticon = 0; //Boolean feature indicating whether an emoticon is present in the tweet
    	String[] emoticons = {":)", ":d", ":D", ":P", ":p", "xd", "xD", ":(", "x)", ":o", ":O", ":'(", ":/"};
    	
    	for (int i=0; i<tokens.length; i++) {
    		String t = tokens[i];

    		int repeatCounter = 0;
    		capitalCount = 0;

    		//Capital counts and repeatedCharacter feature is determined in this for loop
			for(int j=1; j<t.length(); j++) {

				if(Character.isUpperCase(t.charAt(j))){
					capitalCount++;
				}

   				if(t.charAt(j) == t.charAt(j-1)) {
      				repeatCounter++;
      				if(repeatCounter == 3){
      					hasRepeatedCharacters = 1; //boolean value is true now
      				}
   				}
   				else{
   					repeatCounter = 0;
   				}
			}

			//hasEmoticon feature is determined here
			hasEmoticon = (Collections.disjoint(Arrays.asList(tokens), Arrays.asList(emoticons))) ? 0 : 1; // if two lists are disjoint set hasEmoticon 
																										   // to 0 (False) else set it to 1 (True)

    	}

    	features.put("hasRepeatedCharacters", hasRepeatedCharacters);
    	features.put("capitalisation", capitalCount);
    	features.put("length", tokens.length);
    	features.put("hasEmoticon", hasEmoticon);
    	
    	return features;
    }

    
    
}
