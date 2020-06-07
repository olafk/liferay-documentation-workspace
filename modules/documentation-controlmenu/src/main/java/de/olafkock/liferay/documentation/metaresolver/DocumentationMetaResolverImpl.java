package de.olafkock.liferay.documentation.metaresolver;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.api.DocumentationResolver;

/**
 * @author Olaf Kock
 */

public class DocumentationMetaResolverImpl {

	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		
		DocumentationEntry entry = (DocumentationEntry) request.getAttribute("DOCUMENTATION_ENTRY");
		if(entry != null) {
			return entry;
		}

		for (DocumentationResolver resolver : resolvers) {
			entry = resolver.getDocumentationEntry(request);
			if(entry != null) {
				request.setAttribute("DOCUMENTATION_ENTRY", entry);
				return entry;
			}
		}
		
		return entry;
	}

	public void doRegister(DocumentationResolver resolver) {
		int index = 0;
		for (DocumentationResolver existing : resolvers) {
			if(existing.getOrder() < resolver.getOrder()) {
				index++;
			} else 
				break;
		}
		resolvers.add(index, resolver);
	}
	
	public void doUnRegister(DocumentationResolver resolver) {
		resolvers.remove(resolver);
	}
	
	volatile List<DocumentationResolver> resolvers = new LinkedList<DocumentationResolver>();
}