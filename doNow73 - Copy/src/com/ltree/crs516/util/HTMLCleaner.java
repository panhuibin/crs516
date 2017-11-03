package com.ltree.crs516.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to clean up HTML strings.
 * @author crs 516 development team.
 *
 */
public class HTMLCleaner {

	static Pattern leftAngleBracketPattern = Pattern.compile("<");
	static Pattern rightAngleBracketPattern = Pattern.compile(">");
	
	public static String escape(String inHtml) {
		Matcher noLeftAngleBracketMatcher = leftAngleBracketPattern.matcher(inHtml);
		String noLeftAngleBracket = noLeftAngleBracketMatcher.replaceAll("&lt;");
		Matcher noRightAngleBracketMatcher 
							= rightAngleBracketPattern.matcher(noLeftAngleBracket);
		String noRightAngleBracket = noRightAngleBracketMatcher.replaceAll("&gt;");
		return noRightAngleBracket;
	}
	
}
