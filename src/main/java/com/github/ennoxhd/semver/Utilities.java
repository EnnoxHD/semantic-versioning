package com.github.ennoxhd.semver;

import java.util.List;

final class Utilities {
	
	private Utilities() { }
	
	private static final class Regex {
		static final String ASCII_ALPHANUMERICS_AND_HYPHEN = "^[0-9A-Za-z-]+$";
		static final String NUMERIC_IDENTIFIER = "^[0-9]+$";
		static final String LEADING_ZERO = "^0.+$";
	}
	
	static boolean hasInformation(final List<?> list) {
		return list != null && !list.isEmpty();
	}
	
	static boolean isNegative(final int version) {
		return version < 0;
	}
	
	static boolean isNullOrBlank(final String str) {
		return str == null || str.isBlank();
	}
	
	static boolean isAsciiAlphanumericsAndHyphen(final String str) {
		return str.matches(Regex.ASCII_ALPHANUMERICS_AND_HYPHEN);
	}
	
	static boolean isNumericIdentifier(final String str) {
		return str.matches(Regex.NUMERIC_IDENTIFIER);
	}
	
	static boolean hasLeadingZero(final String str) {
		return str.matches(Regex.LEADING_ZERO);
	}
}
