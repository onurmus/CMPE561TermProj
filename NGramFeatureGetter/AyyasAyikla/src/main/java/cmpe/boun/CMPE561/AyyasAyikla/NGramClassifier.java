package cmpe.boun.CMPE561.AyyasAyikla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;


public class NGramClassifier {

	public HashMap<String, Double> drunkNgrams;
	public HashMap<String, Double> soberNgrams;

	
	public NGramClassifier(HashMap<String, Double> drunkNgrams,
			HashMap<String, Double> soberNgrams){
		this.drunkNgrams = drunkNgrams;
		this.soberNgrams = soberNgrams;
	}

	public double[] getClassOFTweet(String currentTweet, String nGram){
		String[] tokens = App.parseTweet(currentTweet);

		double drunkWordCount = 0.0;
		double soberWordCount = 0.0;

		for (double value : drunkNgrams.values()) {
	    	drunkWordCount += value;
		}
		for (double value : soberNgrams.values()) {
    		soberWordCount += value;
		}

		if(nGram.equals("unigram")){

			double probDrunk = 0;

			for(int i=0; i<tokens.length; i++){
				String word = tokens[i];
				probDrunk += (Math.log(drunkNgrams.get(word)) - Math.log(drunkWordCount));
			}

			probDrunk += Math.log(drunkWordCount/(drunkWordCount+soberWordCount));


			double probSober = 0;

			for(int i=0; i<tokens.length; i++){
				String word = tokens[i];
				probSober += (Math.log(soberNgrams.get(word)) - Math.log(soberWordCount));
			}

			probSober += Math.log(soberWordCount/(drunkWordCount+soberWordCount));

			double[] probs = {probDrunk, probSober};
			return probs;
		}
		else if(nGram.equals("bigram")){

			double probDrunk = 0;


			for(int i=1; i<tokens.length; i++){
				String word1 = tokens[i-1];
				String word2 = tokens[i];
				String bigram = word1 + "-" + word2;
				probDrunk += (Math.log(drunkNgrams.get(bigram)) - Math.log(drunkWordCount));
			}

			probDrunk += Math.log(drunkWordCount/(drunkWordCount+soberWordCount));


			double probSober = 0;

			for(int i=1; i<tokens.length; i++){
				String word1 = tokens[i-1];
				String word2 = tokens[i];
				String bigram = word1 + "-" + word2;
				probSober += (Math.log(soberNgrams.get(bigram)) - Math.log(soberWordCount));
			}

			probSober += Math.log(soberWordCount/(drunkWordCount+soberWordCount));

			double[] probs = {probDrunk, probSober};
			return probs;
		}
		else{
			System.out.print("Invalid NGram type");
			return null;
		}
	}
}