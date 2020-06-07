package de.olafkock.liferay.documentation.resolver.expando;

import de.olafkock.liferay.documentation.api.DocumentationEntry;

public class AudioguideEntryImpl implements DocumentationEntry {
	
	public AudioguideEntryImpl(String audioUrl, String scriptUrl, String documentationUrl) {
		this.audioUrl = audioUrl;
		this.scriptUrl = scriptUrl;
		this.documentationUrl = documentationUrl;
	}
	
	public String getDocumentationUrl() {
		return documentationUrl;
	}

	public String getAudioURL() {
		return audioUrl;
	}
	
	public String getScriptURL() {
		return scriptUrl;
	}
	
	public boolean isDocumented() {
		return hasContent(documentationUrl);
	}
	
	public boolean isAudio() {
		return hasContent(audioUrl) || hasContent(scriptUrl);
	}
	
	public boolean isScripted() {
		return isAudio() && hasContent(scriptUrl);
	}
	
	private String documentationUrl;
	private String audioUrl;
	private String scriptUrl;
	
	private boolean hasContent(String string) {
		return string != null && ! string.isEmpty();
	}

}

