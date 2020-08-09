package com.github.ennoxhd.semver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class SemanticVersion extends AbstractSemanticVersionCreator implements Comparable<SemanticVersion> {

	// ---------------------------------------------- OBJECT ATTRIBUTES -----------------------------------------------
	private int _majorVersion;
	private int _minorVersion;
	private int _patchVersion;
	private List<String> _preReleaseIdentifiers;
	private List<String> _buildMetadataIdentifiers;
	
	// ----------------------------------------------- PRIVATE METHODS ------------------------------------------------
	
	private void _setMajorVersion(final int majorVersion) {
		if(Utilities.isNegative(majorVersion))
			throw new VersionException("a negative major version is not compliant with semantic versioning");
		
		_majorVersion = majorVersion;
	}
	
	private void _setMinorVersion(final int minorVersion) {
		if(Utilities.isNegative(minorVersion))
			throw new VersionException("a negative minor version is not compliant with semantic versioning");
		
		_minorVersion = minorVersion;
	}
	
	private void _setPatchVersion(final int patchVersion) {
		if(Utilities.isNegative(patchVersion))
			throw new VersionException("a negative patch version is not compliant with semantic versioning");
		
		_patchVersion = patchVersion;
	}
	
	private List<String> _getPreReleaseIdentifiers() {
		return _preReleaseIdentifiers;
	}
	
	private void _setPreReleaseIdentifiers(final List<String> preReleaseIdentifiers) {
		_preReleaseIdentifiers = preReleaseIdentifiers;
	}
	
	void _setPreReleaseString(final String preRelease) {
		if(Utilities.isNullOrBlank(preRelease))
			throw new VersionException("the pre release string must not be empty to be compliant with semantic versioning");
		String regexForSplit = Pattern.quote(String.valueOf(Notation.PRE_RELEASE_SEPARATOR));
		List<String> preReleaseList = Arrays.asList(preRelease.split(regexForSplit));
		for(String preReleasePart : preReleaseList) {
			if(Utilities.isNullOrBlank(preReleasePart))
				throw new VersionException("parts of the pre release string must not be empty to be compliant with semantic versioning");
			if(!Utilities.isAsciiAlphanumericsAndHyphen(preReleasePart))
				throw new VersionException("some characters of the pre release string are not compliant with semantic versioning, "
						+ "only ascii alphanumerics and hyphen characters are allowed");
			if(Utilities.isNumericIdentifier(preReleasePart) && Utilities.hasLeadingZero(preReleasePart))
				throw new VersionException("leading zeros in numeric identifiers of a pre release string "
						+ "are not compliant with semantic versioning");
		}
		_setPreReleaseIdentifiers(preReleaseList);
	}
	
	private boolean _hasPreReleaseInformation() {
		return Utilities.hasInformation(_getPreReleaseIdentifiers());
	}
	
	private int _getPreReleaseInformationLength() {
		return _hasPreReleaseInformation() ? _getPreReleaseIdentifiers().size() : 0;
	}
	
	private List<String> _getBuildMetadataIdentifiers() {
		return _buildMetadataIdentifiers;
	}
	
	private void _setBuildMetadataIdentifiers(final List<String> buildMetadataIdentifiers) {
		_buildMetadataIdentifiers = buildMetadataIdentifiers;
	}
	
	void _setBuildMetadataString(final String buildMetadata) {
		if(Utilities.isNullOrBlank(buildMetadata))
			throw new VersionException("the build metadata string must not be empty to be compliant with semantic versioning");
		String regexForSplit = Pattern.quote(String.valueOf(Notation.BUILD_METADATA_SEPARATOR));
		List<String> buildMetadataList = Arrays.asList(buildMetadata.split(regexForSplit));
		for(String buildMetadataPart : buildMetadataList) {
			if(Utilities.isNullOrBlank(buildMetadata))
				throw new VersionException("parts of the build metadata string must not be empty to be compliant with semantic versioning");
			if(!Utilities.isAsciiAlphanumericsAndHyphen(buildMetadataPart))
				throw new VersionException("some characters of the build metadata string are not compliant with semantic versioning, "
						+ "only ascii alphanumerics and hyphen characters are allowed");
		}
		_setBuildMetadataIdentifiers(buildMetadataList);
	}
	
	private boolean _hasBuildMetadataInformation() {
		return Utilities.hasInformation(_getBuildMetadataIdentifiers());
	}
	
	SemanticVersion(final int majorVersion, final int minorVersion, final int patchVersion) {
		_setMajorVersion(majorVersion);
		_setMinorVersion(minorVersion);
		_setPatchVersion(patchVersion);
		_setPreReleaseIdentifiers(Collections.emptyList());
		_setBuildMetadataIdentifiers(Collections.emptyList());
	}
	
	// -------------------------------------------------- PUBLIC API --------------------------------------------------
	
	public static final class Notation {
		
		private Notation() { }
		
		public static final char VERSION_NUMBER_SEPARATOR = '.';
		public static final char PRE_RELEASE_PREFIX = '-';
		public static final char PRE_RELEASE_SEPARATOR = '.';
		public static final char BUILD_METADATA_PREFIX = '+';
		public static final char BUILD_METADATA_SEPARATOR = '.';
	}
	
	public int getMajorVersion() {
		return _majorVersion;
	}
	
	public int getMinorVersion() {
		return _minorVersion;
	}
	
	public int getPatchVersion() {
		return _patchVersion;
	}
	
	public String getVersionCore() {
		return new StringBuilder()
				.append(getMajorVersion())
				.append(Notation.VERSION_NUMBER_SEPARATOR)
				.append(getMinorVersion())
				.append(Notation.VERSION_NUMBER_SEPARATOR)
				.append(getPatchVersion())
				.toString();
	}
	
	public List<String> getPreReleaseIdentifiers() {
		return new ArrayList<>(_getPreReleaseIdentifiers());
	}
	
	public String getPreRelease() {
		return _hasPreReleaseInformation() ? _getPreReleaseIdentifiers().stream().collect(Collectors.joining(String.valueOf(Notation.PRE_RELEASE_SEPARATOR))) : "";
	}
	
	public String getPreReleaseWithPrefix() {
		return _hasPreReleaseInformation() ? Notation.PRE_RELEASE_PREFIX + getPreRelease() : "";
	}
	
	public List<String> getBuildMetadataIdentifiers() {
		return new ArrayList<>(_getBuildMetadataIdentifiers());
	}
	
	public String getBuildMetadata() {
		return _hasBuildMetadataInformation() ? _getBuildMetadataIdentifiers().stream().collect(Collectors.joining(String.valueOf(Notation.BUILD_METADATA_SEPARATOR))) : "";
	}
	
	public String getBuildMetadataWithPrefix() {
		return _hasBuildMetadataInformation() ? Notation.BUILD_METADATA_PREFIX + getBuildMetadata() : "";
	}
	
	public String getVersion() {
		return getVersionCore()
				+ (_hasPreReleaseInformation() ? getPreReleaseWithPrefix() : "")
				+ (_hasBuildMetadataInformation() ? getBuildMetadataWithPrefix() : "");
	}
	
	public boolean isInitialDevelopment() {
		return getMajorVersion() == 0;
	}
	
	public boolean hasStablePublicAPI() {
		return !isInitialDevelopment();
	}
	
	public boolean isPreReleaseVersion() {
		return _hasPreReleaseInformation();
	}
	
	public boolean isNormalVersion() {
		return !isPreReleaseVersion();
	}
	
	// ---------------------------------------------- CONSTUCTOR METHODS ----------------------------------------------
	
	public SemanticVersion increaseMajor() {
		return super.increaseMajor(this);
	}
	
	public SemanticVersion increaseMinor() {
		return super.increaseMinor(this);
	}
	
	public SemanticVersion increasePatch() {
		return super.increasePatch(this);
	}
	
	// ------------------------------------------------ OBJECT METHODS ------------------------------------------------
	
	@Override
	public boolean equals(final Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof SemanticVersion))
			return false;
		SemanticVersion other = (SemanticVersion) o;
		if(getMajorVersion() != other.getMajorVersion())
			return false;
		if(getMinorVersion() != other.getMinorVersion())
			return false;
		if(getPatchVersion() != other.getPatchVersion())
			return false;
		if(_hasPreReleaseInformation() != other._hasPreReleaseInformation())
			return false;
		if(_getPreReleaseIdentifiers().size() != other._getPreReleaseIdentifiers().size())
			return false;
		for(int i = 0; i < _getPreReleaseIdentifiers().size(); i++) {
			if(!_getPreReleaseIdentifiers().get(i).equals(other._getPreReleaseIdentifiers().get(i)))
				return false;
		}
		if(_hasBuildMetadataInformation() != other._hasBuildMetadataInformation())
			return false;
		if(_getBuildMetadataIdentifiers().size() != other._getBuildMetadataIdentifiers().size())
			return false;
		for(int i = 0; i < _getBuildMetadataIdentifiers().size(); i++) {
			if(!_getBuildMetadataIdentifiers().get(i).equals(other._getBuildMetadataIdentifiers().get(i)))
				return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getMajorVersion(), getMinorVersion(), getPatchVersion(), _getPreReleaseIdentifiers(), _getBuildMetadataIdentifiers());
	}
	
	@Override
	public String toString() {
		return getVersion();
	}
	
	// ----------------------------------------- COMPARABLE INTERFACE METHOD ------------------------------------------
	
	@Override
	public int compareTo(final SemanticVersion other) {
		if(this == other)
			return 0;
		
		if(getMajorVersion() > other.getMajorVersion()) {
			return 1;
		} else if(getMajorVersion() < other.getMajorVersion()) {
			return -1;
		}
		
		if(getMinorVersion() > other.getMinorVersion()) {
			return 1;
		} else if(getMinorVersion() < other.getMinorVersion()) {
			return -1;
		}
		
		if(getPatchVersion() > other.getPatchVersion()) {
			return 1;
		} else if(getPatchVersion() < other.getPatchVersion()) {
			return -1;
		}
		
		if(!_hasPreReleaseInformation() && other._hasPreReleaseInformation()) {
			return 1;
		} else if(_hasPreReleaseInformation() && !other._hasPreReleaseInformation()) {
			return -1;
		} else if(!_hasPreReleaseInformation()) {
			return 0;
		}
		
		int minLength = Math.min(_getPreReleaseInformationLength(), other._getPreReleaseInformationLength());
		for(int i = 0; i < minLength; i++) {
			String thisIdentifier = _getPreReleaseIdentifiers().get(i);
			String otherIdentifier = other._getPreReleaseIdentifiers().get(i);
			
			if(!Utilities.isNumericIdentifier(thisIdentifier) && Utilities.isNumericIdentifier(otherIdentifier)) {
				return 1;
			} else if(Utilities.isNumericIdentifier(thisIdentifier) && !Utilities.isNumericIdentifier(otherIdentifier)) {
				return -1;
			} else if(Utilities.isNumericIdentifier(thisIdentifier)) {
				int compared = Integer.valueOf(thisIdentifier).compareTo(Integer.valueOf(otherIdentifier));
				if(compared == 0)
					continue;
				return compared;
			} else {
				int compared = thisIdentifier.compareTo(otherIdentifier);
				if(compared == 0)
					continue;
				return compared;
			}
		}
		
		return Integer.valueOf(_getPreReleaseInformationLength()).compareTo(Integer.valueOf(other._getPreReleaseInformationLength()));
	}
}
