package de.olafkock.liferay.documentation.api;

import javax.servlet.http.HttpServletRequest;

/**
 * @author olaf
 */
public interface DocumentationResolver {
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request);
}