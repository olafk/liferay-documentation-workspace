package de.olafkock.liferay.documentation.controlpanel;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.controlpanel.resources.PortletDocumentation;
import de.olafkock.liferay.documentation.controlpanel.resources.PortletDocumentationFactory;
import de.olafkock.liferay.documentation.controlpanel.resources.URLConfig;

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
		
//		return new DocumentationEntry() {
//			@Override
//			public boolean isScripted() {
//				return true;
//			}
//			@Override
//			public boolean isAudio() {
//				return true;
//			}
//			@Override
//			public String getScriptURL() {
//				return "https://www.olafkock.de/liferay/audioguide/widgetpage-demo.json";
//			}
//			@Override
//			public String getAudioURL() {
//				return "https://www.olafkock.de/liferay/audioguide/widgetpage-demo.json";
//			}
//			@Override
//			public String getDocumentationUrl() {
//				return "http://localhost:7280";
//			}
//			@Override
//			public boolean isDocumented() {
//				return true;
//			}
//		};
	}
	Log log = LogFactoryUtil.getLog(getClass());
}
