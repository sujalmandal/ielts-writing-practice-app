package learn.ieltswriting.util;

import java.util.List;

public class WordsUtils {

	public static int totalWords(String text) {
		return text.split(" ").length;
	}

	public static String toCSV(List<String> words) {
		return String.join(",", words);
	}

}
