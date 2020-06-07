package de.olafkock.liferay.documentation.entry;

public class DocumentationEntry {

	public DocumentationEntry(String audioUrl, String scriptUrl, String documentationUrl) {
		this.audioUrl = audioUrl;
		this.scriptUrl = scriptUrl;
		this.documentationUrl = documentationUrl;
	}
	
	public String getAudioguideAudioURL() {
		return audioUrl;
	}

	public String getAudioguideScriptURL() {
		return scriptUrl;
	}
	
	public String getDocumentationURL() {
		return documentationUrl;
	}
	
	public boolean isAudio() {
		return hasContent(audioUrl);
	}
	
	public boolean isIllustrated() {
		return isAudio() && hasContent(scriptUrl);
	}
	
	public boolean isDocumented() {
		return hasContent(documentationUrl);
	}
	
	private String audioUrl;
	private String scriptUrl;
	private String documentationUrl;

	private boolean hasContent(String string) {
		return string != null && ! string.isEmpty();
	}
}
