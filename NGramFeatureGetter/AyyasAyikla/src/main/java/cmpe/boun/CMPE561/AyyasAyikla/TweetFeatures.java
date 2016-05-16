package cmpe.boun.CMPE561.AyyasAyikla;

public class TweetFeatures {
	public String tweetId;
	public double posNounToAdj;
	public double posNounToAdv;
	public double posAdjToAdv;
	public boolean repeatedChars;
	public int capitalCount;
	public int wordCount;
	public int emoticonCount;
	
	public TweetFeatures(){
		posNounToAdj = 0;
		posNounToAdv = 0;
		posAdjToAdv = 0;
		repeatedChars = false;
		capitalCount = 0;
		wordCount = 0;
		emoticonCount = 0;
	}
}
