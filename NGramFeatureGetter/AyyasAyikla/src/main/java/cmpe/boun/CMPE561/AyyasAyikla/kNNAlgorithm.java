package cmpe.boun.CMPE561.AyyasAyikla;

import java.util.ArrayList;

public class kNNAlgorithm {

	public ArrayList<TweetFeatures> drunkDocs;
	public ArrayList<TweetFeatures> soberDocs;
	public TweetFeatures currentTweet;
	
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
		return "";
	}
	
}
