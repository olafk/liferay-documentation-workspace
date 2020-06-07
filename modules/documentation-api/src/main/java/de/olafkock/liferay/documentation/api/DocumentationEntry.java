package de.olafkock.liferay.documentation.api;

public interface DocumentationEntry {
	/**
	 * URL of a page containing documentation for this page. 
	 * Typically will be embedded via iframe.
	 * 
	 * @return
	 */
	public String getDocumentationUrl();
	
	/**
	 * URL of a media file that can be played back to document a page
	 * Will be used as a src attribute in an audio tag.
	 * 
	 * @return
	 */
	public String getAudioURL();
	
	/**
	 * URL of a playback script (optional with embedded audio file). 
	 * Format (json) and available commands goes back to original Walkthrough POC
	 *  
	 * @return
	 */
	public String getScriptURL();
	
	/**
	 * quick way to determine if this object is expected to contain a useful
	 * URL to a documentation page
	 * @return
	 */
	public boolean isDocumented();
	
	/**
	 * quick way to determine if this object is expected to contain a useful
	 * URL to an audio file
	 * @return
	 */
	public boolean isAudio();

	/**
	 * quick way to determine if this object is expected to contain a useful
	 * URL to a script file
	 * @return
	 */
	public boolean isScripted();
}
