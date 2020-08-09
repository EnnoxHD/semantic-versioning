package com.github.ennoxhd.semver;

public final class SemanticVersionRegex {

	private SemanticVersionRegex() { }
	
	public static final String letter = "[A-Za-z]";
	public static final String positive_digit = "[1-9]";
	public static final String digit = "0|" + positive_digit;
	public static final String digits = "(" + digit + ")+";
	public static final String non_digit = letter + "|-";
	public static final String identifier_character = digit + "|" + non_digit;
	public static final String identifier_characters = "(" + identifier_character + ")+";
	public static final String numeric_identifier = "0|(" + positive_digit + "(" + digits + ")?)";
	public static final String alphanumeric_identifier = "((" + non_digit + ")|(" + identifier_characters + "(" + non_digit + ")))" + "(" + identifier_characters + ")?";
	public static final String build_identifier = "(" + alphanumeric_identifier + ")|" + digits;
	public static final String pre_release_identifier = "(" + alphanumeric_identifier + ")|(" + numeric_identifier + ")";
	public static final String dot_separated_build_identifiers = "(" + build_identifier + ")(\\.(" + build_identifier + "))?";
	public static final String build = dot_separated_build_identifiers;		
	public static final String dot_separated_pre_release_identifiers = "(" + pre_release_identifier + ")" + "(\\.(" + pre_release_identifier + "))?";
	public static final String pre_release = dot_separated_pre_release_identifiers;
	public static final String patch = numeric_identifier;
	public static final String minor = numeric_identifier;
	public static final String major = numeric_identifier;
	public static final String version_core = "(" + major + ")\\.(" + minor + ")\\.(" + patch + ")";
	public static final String valid_semver = version_core + "(-(" + pre_release + "))?(\\+("+ build + "))?";
}
