package de.olafkock.liferay.documentation.controlpanel.resources;

import com.liferay.portal.kernel.json.JSON;

@JSON
public class URLConfig {
	public URLConfig() {
	}
	@JSON
	public String secondary;
	@JSON
	public String mediaURL;
	@JSON
	public String mediaType;
	@JSON
	public String documentationURL;
}