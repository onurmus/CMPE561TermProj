package cmpe.boun.CMPE561.AyyasAyikla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;


public class kNNAlgorithm {

	public ArrayList<TweetFeatures> drunkDocs;
	public ArrayList<TweetFeatures> soberDocs;
	public TweetFeatures currentTweet;
	
	public HashMap<String, ArrayList<Double>> distances;

	public int k;
	
	public kNNAlgorithm(int k,ArrayList<TweetFeatures> drunkDocs,
			ArrayList<TweetFeatures> soberDocs,TweetFeatures currentTweet){
		this.k = k;
		this.currentTweet = currentTweet;
		this.soberDocs = soberDocs;
		this.drunkDocs = drunkDocs;
	}
	
	public kNNAlgorithm(){
		
	}
	
	public String getClassOFTweet(){
		distances = new HashMap<String,ArrayList<Double>>(); // Hashmap of type tweetClass : distance

		for(int i=0; i<drunkDocs.size(); i++){
			TweetFeatures dt = drunkDocs.get(i); //dt = current drunk tweet
			TweetFeatures ct = currentTweet;

			int repeatedCharsDT = (dt.repeatedChars) ? 1 : 0;
			int repeatedCharsCT = (ct.repeatedChars) ? 1 : 0;

			double distance = Math.pow((Math.pow((dt.posNounToAdj - ct.posNounToAdj),2) 
					  + Math.pow((dt.posNounToAdv - ct.posNounToAdv),2)
					  + Math.pow((dt.posAdjToAdv - ct.posAdjToAdv),2)
					  + Math.pow((repeatedCharsDT - repeatedCharsCT),2)
					  + Math.pow((dt.capitalCount - ct.capitalCount),2)
					  + Math.pow((dt.wordCount - ct.wordCount),2)
					  + Math.pow((dt.emoticonCount - ct.emoticonCount),2)),1/7);

			distances.get("drunk").add(distance);
		}

		for(int i=0; i<soberDocs.size(); i++){
			TweetFeatures dt = soberDocs.get(i); //dt = current drunk tweet
			TweetFeatures ct = currentTweet;

			int repeatedCharsDT = (dt.repeatedChars) ? 1 : 0;
			int repeatedCharsCT = (ct.repeatedChars) ? 1 : 0;

			double distance = Math.pow((Math.pow((dt.posNounToAdj - ct.posNounToAdj),2) 
					  + Math.pow((dt.posNounToAdv - ct.posNounToAdv),2)
					  + Math.pow((dt.posAdjToAdv - ct.posAdjToAdv),2)
					  + Math.pow((repeatedCharsDT - repeatedCharsCT),2)
					  + Math.pow((dt.capitalCount - ct.capitalCount),2)
					  + Math.pow((dt.wordCount - ct.wordCount),2)
					  + Math.pow((dt.emoticonCount - ct.emoticonCount),2)),1/7);

			distances.get("sober").add(distance);
		}

		ArrayList<Double> drunkDistances = distances.get("drunk");
		ArrayList<Double> soberDistances = distances.get("sober");
		Collections.reverse(drunkDistances); //sort in descending order
		Collections.reverse(soberDistances); //sort in descending order

		double[] max5 = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		int i = 0;
		int j = 0;
		int k = 0;
		while(k<5){
			if(drunkDistances.get(i) > soberDistances.get(j)){
				max5[k] = drunkDistances.get(i);
				i++;
			}
			else{
				max5[k] = soberDistances.get(j);
				j++;
			}
			k++;
		}

		if (i>j)
			return "drunk";
		else
			return "sober";
	}
	
}
