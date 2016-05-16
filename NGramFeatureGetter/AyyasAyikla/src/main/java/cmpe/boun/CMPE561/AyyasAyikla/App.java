package cmpe.boun.CMPE561.AyyasAyikla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

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
	
	static public ArrayList<TweetFeatures> drunkDocs;
	static public ArrayList<TweetFeatures> soberDocs;
	
    public static void main( String[] args ) throws SQLException, FileNotFoundException, UnsupportedEncodingException
    {
    	DBConnection conn = new DBConnection();
		conn.startConnection();
		
		ReadFeatures rf = new ReadFeatures("1");
		drunkDocs = rf.drunkDocs;
		soberDocs = rf.soberDocs;
		
		//System.out.println("drunk size : " + drunkDocs.size() + " sober size : " +soberDocs.size());
		//System.out.println("drunk : " + drunkDocs.get(1000).posAdjToAdv + "  " +drunkDocs.get(1000).posNounToAdj + " "+drunkDocs.get(1000).capitalCount);

		RocchioAlgotihm ra = new RocchioAlgotihm(drunkDocs,soberDocs);
    }
    
    private static void getPOSTags(DBConnection conn) throws FileNotFoundException, UnsupportedEncodingException, SQLException{
    	ResultSet ds1Rs = conn.getDataSet2Drunk();
		
		String saveFileName = "curTweet.txt";
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		
		PrintStream out = new PrintStream(new File("posDs2Drunk.txt"),"UTF-8");
		
		while(ds1Rs.next()){
			String tweetStr = ds1Rs.getString("tweet");
			String tweetId = ds1Rs.getString("tweetId");
			
			String[] strArr = parseTweet(tweetStr);
			
			String tweet = "";
			for(int i=0;i<strArr.length; i++){
				tweet += strArr[i]+" ";
			}
			
			String taggedTweet = tagger.tagString(tweet);
			//System.out.println(taggedTweet);
			
			String[] tagPairs = taggedTweet.split("\\s");
			
			int nounCounter = 0;
			int adjCounter = 0;
			int advCounter = 0;
			for(int i=0 ; i<tagPairs.length; i++){				
				int index = tagPairs[i].lastIndexOf("_");
				
				if(index == -1){
					continue;
				}
				String[] pair = new String[]{tagPairs[i].substring(0, index),tagPairs[i].substring(index+1, tagPairs[i].length())};
				
				if(pair[1].equals("NN") || pair[1].equals("NNP") ||  pair[1].equals("NNPS") ||  pair[1].equals("NNS")){ //noun
					nounCounter++;
				}else if(pair[1].equals("JJ") || pair[1].equals("JJR") ||  pair[1].equals("JJS") ){//adjective
					adjCounter++;
				}else if(pair[1].equals("RB") ||pair[1].equals("RBR") ||pair[1].equals("RBS") ||pair[1].equals("WRB")){ //adverb
					advCounter++;
				}
			}
			
			double NounToAdj = 0;
			double NounToAdv = 0;
			double AdvToAdj = 0;
			if(nounCounter != 0 && adjCounter != 0){
				NounToAdj = (double)nounCounter / adjCounter;
			}
			
			if(nounCounter != 0 && advCounter != 0){
				NounToAdv = (double)nounCounter / advCounter;
			}

			if(adjCounter != 0 && advCounter != 0){
				AdvToAdj = (double)advCounter / adjCounter;
			}
			
			out.println(tweetId + " : < "+ NounToAdj +" , "+ NounToAdv + " , " + AdvToAdj + " > ");
			
		}
		
    	out.close();
    	
    	System.out.println("bitti");
    }
    
    
    private static void getNgramFeatures(DBConnection conn) throws SQLException, FileNotFoundException, UnsupportedEncodingException{
    	biGramMap = new HashMap<String,Integer>();
    	uniGramMap = new HashMap<String,Integer>();
		
		ResultSet ds1Rs = conn.getDataSet2Sober();
		
		System.out.println("db den okudum");
		
		int count = 0;
		try {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    

    
    
}
