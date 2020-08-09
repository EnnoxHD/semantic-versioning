package com.github.ennoxhd.semver;

abstract class AbstractSemanticVersionCreator {
	
	static SemanticVersion _createInitialDevelopmentVersion() {
		return new SemanticVersion(0, 0, 1);
	}
	
	static SemanticVersion _createInitialPublicVersion() {
		return new SemanticVersion(1, 0, 0);
	}

	public static SemanticVersion newVersion() {
		return _createInitialDevelopmentVersion();
	}
	
	public static SemanticVersion newVersion(final boolean isPublic) {
		return isPublic ? _createInitialPublicVersion() : _createInitialDevelopmentVersion();
	}
	
	public static SemanticVersion newVersion(final int majorVersion, final int minorVersion, final int patchVersion) {
		return new SemanticVersion(majorVersion, minorVersion, patchVersion);
	}
	
	public static SemanticVersion newVersionPreRelease(final int majorVersion, final int minorVersion, final int patchVersion, final String preRelease) {
		SemanticVersion version = new SemanticVersion(majorVersion, minorVersion, patchVersion);
		if(!Utilities.isNullOrBlank(preRelease))
			version._setPreReleaseString(preRelease);
		return version;
	}
	
	public static SemanticVersion newVersionPreRelease(final SemanticVersion versionCore, final String preRelease) {
		return newVersionPreRelease(versionCore.getMajorVersion(), versionCore.getMinorVersion(), versionCore.getPatchVersion(), preRelease);
	}
	
	public static SemanticVersion newVersionBuildMetadata(final int majorVersion, final int minorVersion, final int patchVersion, final String buildMetadata) {
		SemanticVersion version = new SemanticVersion(majorVersion, minorVersion, patchVersion);
		if(!Utilities.isNullOrBlank(buildMetadata))
			version._setBuildMetadataString(buildMetadata);
		return version;
	}
	
	public static SemanticVersion newVersionBuildMetadata(final SemanticVersion versionCore, final String buildMetadata) {
		return newVersionBuildMetadata(versionCore.getMajorVersion(), versionCore.getMinorVersion(), versionCore.getPatchVersion(), buildMetadata);
	}
	
	public static SemanticVersion newVersionPreReleaseBuildMetadata(final int majorVersion, final int minorVersion, final int patchVersion, final String preRelease, final String buildMetadata) {
		SemanticVersion version = new SemanticVersion(majorVersion, minorVersion, patchVersion);
		if(!Utilities.isNullOrBlank(preRelease))
			version._setPreReleaseString(preRelease);
		if(!Utilities.isNullOrBlank(buildMetadata))
			version._setBuildMetadataString(buildMetadata);
		return version;
	}
	
	public static SemanticVersion newVersionPreReleaseBuildMetadata(final SemanticVersion versionCore, final String preRelease, final String buildMetadata) {
		return newVersionPreReleaseBuildMetadata(versionCore.getMajorVersion(), versionCore.getMinorVersion(), versionCore.getPatchVersion(), preRelease, buildMetadata);
	}
	
	static SemanticVersion increaseMajor(final SemanticVersion version) {
		return new SemanticVersion(version.getMajorVersion() + 1, 0, 0);
	}
	
	static SemanticVersion increaseMinor(final SemanticVersion version) {
		return new SemanticVersion(version.getMajorVersion(), version.getMinorVersion() + 1, 0);
	}
	
	static SemanticVersion increasePatch(final SemanticVersion version) {
		return new SemanticVersion(version.getMajorVersion(), version.getMinorVersion(), version.getPatchVersion() + 1);
	}
}
