package de.olafkock.liferay.documentation.resolver.generic;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.api.DocumentationResolver;
import de.olafkock.liferay.documentation.defaultimpl.DocumentationEntryImpl;

@Component(
	immediate=true,
	service=DocumentationResolver.class
)

public class GenericDocumentationResolver implements DocumentationResolver {

	@Override
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		String layoutType = layout.getType();
		if("portlet".equals(layoutType)) {
			return new DocumentationEntryImpl(
					"https://www.olafkock.de/liferay/controlpaneldocumentation/com_liferay_layout_admin_web_portlet_GroupPagesPortlet.html?portlet=y", 
					"https://www.olafkock.de/liferay/audioguide/widgetpage-generic.mp3", 
					null);
		} else if("content".equals(layoutType)) {
			if("edit".equals(request.getParameter("p_l_mode"))) {
				return new DocumentationEntryImpl(
						"https://www.olafkock.de/liferay/controlpaneldocumentation/com_liferay_layout_admin_web_portlet_GroupPagesPortlet.html?content=edit", 
						"placeholder", "placeholder");
			} else {
				return new DocumentationEntryImpl(
						"https://www.olafkock.de/liferay/controlpaneldocumentation/com_liferay_layout_admin_web_portlet_GroupPagesPortlet.html?content=view", 
						"placeholder", "placeholder");
			}
		}
		
		return null;
	}

	@Override
	public int getOrder() {
		return 999999999;
	}
}
