package com.github.ennoxhd.semver;

abstract class IncreasedVersionCreator {
	
	public abstract SemanticVersion increaseMajor();
	
	abstract SemanticVersion increaseMajor(SemanticVersion version);
	
	public abstract SemanticVersion increaseMinor();
	
	abstract SemanticVersion increaseMinor(SemanticVersion version);
	
	public abstract SemanticVersion increasePatch();
	
	abstract SemanticVersion increasePatch(SemanticVersion version);
}
