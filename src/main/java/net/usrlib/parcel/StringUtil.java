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
}
