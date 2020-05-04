package PartIII;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.Date;
import javafx.util.Pair;

public class RandomWords {

	private String filename;
	private ArrayList<String> arr;//store all the 100 words
	private ArrayList<String> PickedWords;//store the 10 words
	//use HashSet to ensure no same words are picked
	private HashSet<Integer> CheckNoSame;
	private ArrayList<Pair<String, String> > Pairs;//store all pairs
	/* choose ArrayList<String> or String[] to store the words */
	
	public RandomWords() {
		this.filename = "data/words";
		arr = new ArrayList<String>();
		PickedWords = new ArrayList<String>();
		CheckNoSame = new HashSet<Integer>();
		Pairs = new ArrayList<>();
		init_getWords();
	}
	
	public RandomWords(String filename) {
		this.filename = filename;
		arr = new ArrayList<String>();
		PickedWords = new ArrayList<String>();
		CheckNoSame = new HashSet<Integer>();
		Pairs = new ArrayList<>();
		init_getWords();
	}

	public void init_getWords(){
		FileReader f = null;
		try {
			f = new FileReader(this.filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(f);
		String lines = null;
		try{
			while( ( lines = in.readLine() ) != null )
				arr.add(lines);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Random Rdm = new Random(new Date().getTime());
		for(int i = 0; i < 10 ;i++){
			int r = Rdm.nextInt(100);
			while(CheckNoSame.contains(r))
				r = Rdm.nextInt(100);
			CheckNoSame.add(r);
			PickedWords.add( arr.get(r) );
		}
		for(int i = 0; i<PickedWords.size() ;i++){
			for(int j = i+1; j<PickedWords.size() ;j++){
				Pair<String,String> WordPair = new Pair<>(PickedWords.get(i),PickedWords.get(j));
				Pairs.add(WordPair);
			}
		}
	}

	public static int LevenshteinDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();

		int[][] cost = new int[m + 1][n + 1];
		for(int i = 0; i <= m; i++)
			cost[i][0] = i;
		for(int i = 1; i <= n; i++)
			cost[0][i] = i;

		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(word1.charAt(i) == word2.charAt(j))
					cost[i + 1][j + 1] = cost[i][j];
				else {
					int a = cost[i][j];
					int b = cost[i][j + 1];
					int c = cost[i + 1][j];
					cost[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
					cost[i + 1][j + 1]++;
				}
			}
		}
		return cost[m][n];
	}
	
	public List<Pair<String, String>> sortedPairsComparator() {
		Cmp cmp = new Cmp();
		Pairs.sort(cmp);
		return Pairs;
	}
	
	public List<Pair<String, String>> sortedPairsLambda() {
		Pairs.sort((Pair<String, String> a, Pair<String, String> b) ->
				RandomWords.LevenshteinDistance(a.getKey(),a.getValue()) - RandomWords.LevenshteinDistance(b.getKey(),b.getValue()));
		return Pairs;
	}

	//inner Class
	class Cmp implements Comparator<Pair<String, String>>{
		public int compare(Pair<String, String> p1, Pair<String, String> p2){
			return RandomWords.LevenshteinDistance(p1.getKey(),p1.getValue()) - RandomWords.LevenshteinDistance(p2.getKey(),p2.getValue());
		}
	}

	//methods to test
	public void getString(){
		for (Object it : arr) {
			System.out.println(it);
		}
	}
	public void getRdm(){
		for (Object it : PickedWords) {
			System.out.println(it);
		}
	}
	public void getPairs(){
		for(Pair<String,String> it : Pairs)
			System.out.println("["+it.getKey()+","+it.getValue()+"]");
	}
	//

	public static void main(String[] args) {
		RandomWords r = new RandomWords();
		//r.getString();
		//r.getRdm();
		r.getPairs();
		System.out.println(" ");
		//System.out.println(RandomWords.LevenshteinDistance("intention","execution"));
		//r.sortedPairsLambda();
		r.sortedPairsComparator();
		r.getPairs();
	}
}
