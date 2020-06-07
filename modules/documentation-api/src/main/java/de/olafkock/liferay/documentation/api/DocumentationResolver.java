package de.olafkock.liferay.documentation.api;

import javax.servlet.http.HttpServletRequest;

/**
 * @author olaf
 */
public interface DocumentationResolver {

	/**
	 * Find a DocumentationEntry to illustrate/document the current request.
	 * 
	 * @param request
	 * @return a DocumentationEntry for this request/page, or null if no match
	 */
	
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request);
	/**
	 * This resolver's usage order: low order number means that it will be used
	 * earlier than one with higher order number.
	 * 
	 * May be used to position generic or compute-intensive resolvers towards the 
	 * back of the list of resolvers, or to optimize for certain usage patterns. 
	 * 
	 * @return the usage/execution order for this resolver 
	 */
	public int getOrder();
}