package net.usrlib.parcel;

public class StringUtil {
	public static final String replaceLast(final String source, final String substring, final String replacement) {
		int index = source.lastIndexOf(substring);

		if (index == -1) {
			return source;
		}

		return source.substring(0, index) + replacement + source.substring(index + substring.length());
	}

	public static final String trimLast(final String source, final String substring) {
		return replaceLast(source, substring, "");
	}

	public static final String trimEndOfLine(final String source) {
		return replaceLast(source, System.lineSeparator(), "");
	}

	public static final String firstToUppercase(final String source) {
		return Character.toUpperCase(source.charAt(0)) + source.substring(1);
	}

//	public static final String getOptionValue(final String source, final String option) {
//		// Extracts option value from a string of the format: {-i}{string}
//		// Negative look behind to exclude "{-i}{" from {-i}{string}
//		// Also exclude last bracket } with negative look ahead.
//		// (?<=\\{-i\\}\\{)([\\w+\\/\\-.]+)(?=\\})
//		// Good source: http://www.regular-expressions.info/lookaround.html
//
//		final Pattern pattern = Pattern.compile("(?<=\\{" + option + "\\}\\{)([\\w+\\/\\-.]+)(?=\\})");
//		final Matcher matcher = pattern.matcher(source);
//
//		return matcher.find() ? matcher.group(0) : "";
//	}

//	private Pattern buildRegExWithOption(final String option) {
//	System.out.println("[CMD] buildRegExWithOption " + option);
//	// Extracts option value from a string of the format: {--option}{value}
//	// Negative look behind to exclude "{--option}{" from {--option}{value}
//	// Also exclude last bracket } with negative look ahead.
//	// (?<=\\{--option\\}\\{)([\\w+\\/\\-.]+)(?=\\})
//	return Pattern.compile("(?<=\\{" + option.replaceAll("-", "\\\\-") + "\\}\\{)([\\w+\\/\\-.]+)(?=\\})");
//  }
}
