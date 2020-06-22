package de.olafkock.liferay.documentation.defaultimpl;

import de.olafkock.liferay.documentation.api.DocumentationEntry;

public class DocumentationEntryImpl implements DocumentationEntry {

	public DocumentationEntryImpl(String documentationUrl, String audioUrl, String scriptUrl) {
		this.documentationUrl = documentationUrl;
		this.audioUrl = audioUrl;
		this.scriptUrl = scriptUrl;
	}
	
	@Override
	public String getDocumentationUrl() {
		return documentationUrl;
	}

	@Override
	public String getAudioURL() {
		return audioUrl;
	}

	@Override
	public String getScriptURL() {
		return scriptUrl;
	}

	@Override
	public boolean isDocumented() {
		return hasContent(documentationUrl);
	}

	@Override
	public boolean isAudio() {
		return hasContent(audioUrl);
	}

	@Override
	public boolean isScripted() {
		return hasContent(scriptUrl);
	}

	@Override
	public String toString() {
		return "[" + documentationUrl + "," + audioUrl + "," + scriptUrl + "]"; 
	}
	
	private boolean hasContent(String string) {
		return string != null && ! string.isEmpty();
	}
	
	private String documentationUrl;
	private String audioUrl;
	private String scriptUrl;

}
