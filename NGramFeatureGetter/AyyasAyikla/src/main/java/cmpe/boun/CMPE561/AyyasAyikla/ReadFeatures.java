package cmpe.boun.CMPE561.AyyasAyikla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFeatures {

	public ArrayList<TweetFeatures> drunkDocs;
	public ArrayList<TweetFeatures> soberDocs;

	public ReadFeatures(String dsNum) throws FileNotFoundException{
		
		drunkDocs = new ArrayList<TweetFeatures>();
		soberDocs = new ArrayList<TweetFeatures>();
		
		//firstly Read Files for drunk tweets
		readStylisticFeats("ds"+dsNum+"DrunkFeatures.txt",drunkDocs);
		readPOSFeats("posDs"+dsNum+"Drunk.txt",drunkDocs);
		
		//secondly Read Files
		readStylisticFeats("ds"+dsNum+"SoberFeatures.txt",soberDocs);
		readPOSFeats("posDs"+dsNum+"Sober.txt",soberDocs);
	}
	
	public void readStylisticFeats(String filePath,ArrayList<TweetFeatures> featVector) throws FileNotFoundException{
		Scanner scn = new Scanner(new File(filePath));
		
		while(scn.hasNextLine()){
			if(!scn.hasNext()){
				break;
			}
			String tweetId = scn.next();
			scn.next(); // :
			scn.next(); // <
			int hasRepeatedCharInt = scn.nextInt();
			scn.next(); // ,
			int capitalCount = scn.nextInt();
			scn.next(); // ,
			int wordCount = scn.nextInt();
			scn.next(); // ,
			int emoticonCount = scn.nextInt();
			scn.next();// >
			
			int index = getIndexOfTweetId(featVector,tweetId);
			
			if(index == -1){ // if tweet not found
				TweetFeatures curFeat = new TweetFeatures();
				curFeat.tweetId = tweetId;
				curFeat.capitalCount = capitalCount;
				curFeat.emoticonCount = emoticonCount;
				curFeat.wordCount = wordCount;
				curFeat.repeatedChars = hasRepeatedCharInt;

				featVector.add(curFeat);
			}else{
				TweetFeatures curFeat = featVector.get(index);
				curFeat.tweetId = tweetId;
				curFeat.capitalCount = capitalCount;
				curFeat.emoticonCount = emoticonCount;
				curFeat.wordCount = wordCount;
				curFeat.repeatedChars = hasRepeatedCharInt;
				
				featVector.set(index, curFeat);
			}
		}
	}
	
	public void readPOSFeats(String filePath,ArrayList<TweetFeatures> featVector) throws FileNotFoundException{
		Scanner scn = new Scanner(new File(filePath));
		
		while(scn.hasNextLine()){
			if(!scn.hasNext()){
				break;
			}
			String tweetId = scn.next();
			scn.next(); // :
			scn.next(); // <
			double nounToAdj = scn.nextDouble();
			scn.next(); // ,
			double nounToAdv = scn.nextDouble();
			scn.next(); // ,
			double AdvToAdj = scn.nextDouble();
			scn.next();// >
			
			int index = getIndexOfTweetId(featVector,tweetId);
			
			if(index == -1){ // if tweet not found
				TweetFeatures curFeat = new TweetFeatures();
				curFeat.tweetId = tweetId;
				curFeat.posAdjToAdv = AdvToAdj;
				curFeat.posNounToAdj = nounToAdj;
				curFeat.posNounToAdv = nounToAdv;
				featVector.add(curFeat);
			}else{
				TweetFeatures curFeat = featVector.get(index);
				curFeat.posAdjToAdv = AdvToAdj;
				curFeat.posNounToAdj = nounToAdj;
				curFeat.posNounToAdv = nounToAdv;
				featVector.set(index, curFeat);
			}
		}
	}
	
	private int getIndexOfTweetId(ArrayList<TweetFeatures> featVector, String tweetId){
		for(int i = 0; i<featVector.size(); i++){
			if(featVector.get(i).tweetId.equals(tweetId)){
				return i;
			}
		}
		return -1;
	}
	
	
}
