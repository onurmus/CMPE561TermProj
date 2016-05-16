package cmpe.boun.CMPE561.AyyasAyikla;

import java.util.ArrayList;

public class RocchioAlgotihm {
	
	public ArrayList<TweetFeatures> drunkDocs;
	public ArrayList<TweetFeatures> soberDocs;
		
	public RocchioAlgotihm(ArrayList<TweetFeatures> drunkDocs,ArrayList<TweetFeatures> soberDocs){
		this.soberDocs = soberDocs;
		this.drunkDocs = drunkDocs;
	}
	
	
	public String getClassOFTweet(TweetFeatures currentTweet){
		int numOfFeatures = 7;
		
		TweetFeatures meanDrunk = new TweetFeatures();
		for(int i=0; i<drunkDocs.size(); i++){
			TweetFeatures current = drunkDocs.get(i);
			meanDrunk.capitalCount += current.capitalCount;
			meanDrunk.posNounToAdj += current.posNounToAdj;
			meanDrunk.posNounToAdv += current.posNounToAdv;
			meanDrunk.posAdjToAdv += current.posAdjToAdv;
			meanDrunk.wordCount += current.wordCount;
			meanDrunk.emoticonCount += current.emoticonCount;
			meanDrunk.repeatedChars += current.repeatedChars;
		}
		
		meanDrunk.capitalCount /= drunkDocs.size();
		meanDrunk.posNounToAdj /= drunkDocs.size();
		meanDrunk.posNounToAdv /= drunkDocs.size();
		meanDrunk.posAdjToAdv /= drunkDocs.size();
		meanDrunk.wordCount /= drunkDocs.size();
		meanDrunk.emoticonCount /= drunkDocs.size();
		meanDrunk.repeatedChars /= drunkDocs.size();
		
		
		TweetFeatures meanSober = new TweetFeatures();
		for(int i=0; i<soberDocs.size(); i++){
			TweetFeatures current = soberDocs.get(i);
			meanSober.capitalCount += current.capitalCount;
			meanSober.posNounToAdj += current.posNounToAdj;
			meanSober.posNounToAdv += current.posNounToAdv;
			meanSober.posAdjToAdv += current.posAdjToAdv;
			meanSober.wordCount += current.wordCount;
			meanSober.emoticonCount += current.emoticonCount;
			meanSober.repeatedChars += current.repeatedChars;
		}
		
		meanSober.capitalCount /= soberDocs.size();
		meanSober.posNounToAdj /= soberDocs.size();
		meanSober.posNounToAdv /= soberDocs.size();
		meanSober.posAdjToAdv /= soberDocs.size();
		meanSober.wordCount /= soberDocs.size();
		meanSober.emoticonCount /= soberDocs.size();
		meanSober.repeatedChars /= soberDocs.size();
		
		double distDrunk = Math.pow((meanDrunk.capitalCount-currentTweet.capitalCount), 2) +
				Math.pow((meanDrunk.posNounToAdj-currentTweet.posNounToAdj), 2) +
				Math.pow((meanDrunk.posNounToAdv-currentTweet.posNounToAdv), 2) +
				Math.pow((meanDrunk.posAdjToAdv-currentTweet.posAdjToAdv), 2) +
				Math.pow((meanDrunk.wordCount-currentTweet.wordCount), 2) +
				Math.pow((meanDrunk.emoticonCount-currentTweet.emoticonCount), 2) +
				Math.pow((meanDrunk.repeatedChars-currentTweet.repeatedChars), 2);
		distDrunk = Math.sqrt(distDrunk);
		
		double distSober = Math.pow((meanSober.capitalCount-currentTweet.capitalCount), 2) +
				Math.pow((meanSober.posNounToAdj-currentTweet.posNounToAdj), 2) +
				Math.pow((meanSober.posNounToAdv-currentTweet.posNounToAdv), 2) +
				Math.pow((meanSober.posAdjToAdv-currentTweet.posAdjToAdv), 2) +
				Math.pow((meanSober.wordCount-currentTweet.wordCount), 2) +
				Math.pow((meanSober.emoticonCount-currentTweet.emoticonCount), 2) +
				Math.pow((meanSober.repeatedChars-currentTweet.repeatedChars), 2);
		distSober = Math.sqrt(distSober);
		
		
		if(distDrunk > distSober){
			return "drunk";
		}else if(distDrunk < distSober){
			return "sober";
		}else{
			return "sober";
		}
		
	}
	
}
