
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
		
	}

	public static String tail(String str) {
		if (str.length() == 1) {
			return "";
		}

		else{
			return str.substring(1);
		}

	}

	public static int levenshtein(String word1, String word2) {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		int lengthWord1 = word1.length();
		int lengthWord2 = word2.length();

		int insert = 0;
		int delete = 0;
		int replace = 0;


		if (lengthWord1 == 0) {
			return lengthWord2;
		}

		if (lengthWord2 == 0) {
			return lengthWord1;
		}

		else if (word1.charAt(0) == word2.charAt(0)){
			return levenshtein(tail(word1), tail(word2));
		}


		else {

			insert = levenshtein(tail(word1), word2);
			delete = levenshtein(word1, tail(word2));
			replace = levenshtein(tail(word1), tail(word2));

			int minTemp = Math.min(insert, delete);
			int min = Math.min(minTemp, replace);
			
			return 1 + min;
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];
		In in = new In(fileName);

		for (int i =0; i < dictionary.length; i++) {
			dictionary[i] = in.readString();
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int minDistance = levenshtein(word, dictionary[0]);
		String wordMinDistance = "" ;

		for (int i = 0; i < dictionary.length; i++) {
			if (levenshtein(word, dictionary[i]) < minDistance ) {
				wordMinDistance = dictionary[i];
				minDistance = levenshtein(word, dictionary[i]);
			}
		}
			
		if (minDistance > threshold) {
			return word;
		}	

		else {
			return wordMinDistance;
		}
	}

}
