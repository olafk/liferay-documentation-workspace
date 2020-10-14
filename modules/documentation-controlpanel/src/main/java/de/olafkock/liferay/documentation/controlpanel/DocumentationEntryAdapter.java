package de.olafkock.liferay.documentation.controlpanel;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.controlpanel.authoring.api.URLConfig;

final class DocumentationEntryAdapter implements DocumentationEntry {
	private final URLConfig secondaryUrlConfig;

	DocumentationEntryAdapter(URLConfig secondaryUrlConfig) {
		this.secondaryUrlConfig = secondaryUrlConfig;
	}

	@Override
	public boolean isScripted() {
		return secondaryUrlConfig.mediaType != null && 
				secondaryUrlConfig.mediaType.equals("audioguide");
	}

	@Override
	public boolean isAudio() {
		return secondaryUrlConfig.mediaType != null && 
				secondaryUrlConfig.mediaType.startsWith("audio");
	}

	@Override
	public boolean isDocumented() {
		return secondaryUrlConfig.documentationURL != null && 
				! secondaryUrlConfig.documentationURL.isEmpty();
	}

	@Override
	public String getDocumentationUrl() {
		return secondaryUrlConfig.documentationURL;
	}

	@Override
	public String getScriptURL() {
		return secondaryUrlConfig.mediaURL;
	}

	@Override
	public String getAudioURL() {
		return secondaryUrlConfig.mediaURL;
	}
}