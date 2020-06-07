package de.olafkock.liferay.documentation.api;

public interface DocumentationEntry {
	public String getDocumentationUrl();
	public String getAudioURL();
	public String getScriptURL();
	public boolean isDocumented();
	public boolean isAudio();
	public boolean isScripted();
}
