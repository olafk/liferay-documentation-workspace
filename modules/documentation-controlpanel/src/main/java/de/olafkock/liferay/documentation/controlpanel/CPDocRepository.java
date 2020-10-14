package de.olafkock.liferay.documentation.controlpanel;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.controlpanel.authoring.api.PortletDocumentation;
import de.olafkock.liferay.documentation.controlpanel.authoring.api.URLConfig;

public class CPDocRepository {

	@SuppressWarnings("unused")
	private CPDocConfiguration config;
	private Map<String, PortletDocumentation> directory;

	public CPDocRepository(CPDocConfiguration config) {
		this.config = config;
		directory = PortletDocumentationFactory.getDirectory(config.directoryURL());
	}

	public DocumentationEntry getEntry(String portletId, String secondary) {
		PortletDocumentation portletDocumentation = directory.get(portletId);
		if(portletDocumentation != null) {
			URLConfig secondaryUrlConfig = portletDocumentation.getSecondaryUrlConfig(secondary);
			if(secondaryUrlConfig != null) {
				return new DocumentationEntryAdapter(secondaryUrlConfig);
			}
		}
		
		return null;
	}
	Log log = LogFactoryUtil.getLog(getClass());
}
