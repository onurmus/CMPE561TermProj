package cmpe.boun.CMPE561.AyyasAyikla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadNGramFeatures {

	HashMap<String,Double> drunkUniGrams;
	HashMap<String,Double> soberUniGrams;
	
	HashMap<String,Double> drunkBiGrams;
	HashMap<String,Double> soberBiGrams;
	
	
	public ReadNGramFeatures(String dsNum) throws FileNotFoundException{
		drunkUniGrams = new HashMap<String,Double>();
		soberUniGrams = new HashMap<String,Double>();
		drunkBiGrams = new HashMap<String,Double>();
		soberBiGrams = new HashMap<String,Double>();
		readFile("bigramsds"+dsNum+"Drunk.txt",drunkBiGrams);
		readFile("bigramsds"+dsNum+"Sober.txt",soberBiGrams);
		readFile("unigramsds"+dsNum+"Drunk.txt",drunkUniGrams);
		readFile("unigramsds"+dsNum+"Sober.txt",soberUniGrams);
	}
	
	public void readFile(String filePath,HashMap<String,Double> currentMap) throws FileNotFoundException{
		Scanner scn = new Scanner(new File(filePath),"UTF-8");
		
		while(scn.hasNextLine()){
			if(!scn.hasNext()) break;
			
			String token = scn.next();
			int freq = scn.nextInt();
			
			currentMap.put(token, (double)freq);
		}
	}
}
