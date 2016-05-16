package cmpe.boun.CMPE561.AyyasAyikla;

public class TweetFeatures {
	public String tweetId;
	public double posNounToAdj;
	public double posNounToAdv;
	public double posAdjToAdv;
	public double repeatedChars;
	public double capitalCount;
	public double wordCount;
	public double emoticonCount;
	
	public TweetFeatures(){
		posNounToAdj = 0;
		posNounToAdv = 0;
		posAdjToAdv = 0;
		repeatedChars = 0;
		capitalCount = 0;
		wordCount = 0;
		emoticonCount = 0;
	}
}
