package com.github.ennoxhd.semver;

abstract class NewVersionCreator {

	public abstract SemanticVersion newVersion();
	
	public abstract SemanticVersion newVersion(final boolean isPublic);
	
	public abstract SemanticVersion newVersion(final int majorVersion, final int minorVersion, final int patchVersion);
	
	public abstract SemanticVersion newVersionPreRelease(final int majorVersion, final int minorVersion, final int patchVersion, final String preRelease);
	
	public abstract SemanticVersion newVersionPreRelease(final SemanticVersion versionCore, final String preRelease);
	
	public abstract SemanticVersion newVersionBuildMetadata(final int majorVersion, final int minorVersion, final int patchVersion, final String buildMetadata);
	
	public abstract SemanticVersion newVersionBuildMetadata(final SemanticVersion versionCore, final String buildMetadata);
	
	public abstract SemanticVersion newVersionPreReleaseBuildMetadata(final int majorVersion, final int minorVersion, final int patchVersion, final String preRelease, final String buildMetadata);
	
	public abstract SemanticVersion newVersionPreReleaseBuildMetadata(final SemanticVersion versionCore, final String preRelease, final String buildMetadata);
}
