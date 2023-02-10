package de.olafkock.liferay.documentation.controlpanel;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.api.DocumentationResolver;
import de.olafkock.liferay.documentation.defaultimpl.ControlPanelSecondaryKeyResolver;

/**
 * @author olaf
 */
@Component(
	immediate = true,
	configurationPid = "de.olafkock.liferay.documentation.controlpanel.CPDocConfiguration",
	property = {
			"de.olafkock.liferay.usage=ControlPanelDocumentation"
	},
	service = DocumentationResolver.class
)
public class ControlpanelDocumentationResolver implements DocumentationResolver {

	@Override
	public int getOrder() {
		return 0;
	}
	
	@Override
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		boolean doFilter = themeDisplay.getTheme().isControlPanelTheme()
				&& ! "pop_up".equals(request.getParameter("p_p_state"));
		
		
		if(doFilter) {
			String portletId = PortalUtil.getPortletId(request);
			String secondary = getSecondaryTopic(request, portletId);
			log.debug("ControlPanel: Targeted Portlet ID: " + portletId + "/" + secondary);
			return repository.getEntry(portletId, secondary);
		}	

		return null;
	}
	
	public static String getSecondaryTopic(HttpServletRequest request, String portletId) {
		return ControlPanelSecondaryKeyResolver.getSecondaryTopic(request, portletId);
	}

	@Reference
	protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
	    // configuration update will actually be handled in the @Modified event,
		// which will only be triggered in case we have a @Reference to the 
		// ConfigurationProvider
	}
	
	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		config = ConfigurableUtil.createConfigurable(CPDocConfiguration.class, 
				properties);
		repository = new CPDocRepository(config);
	}

	Log log = LogFactoryUtil.getLog(getClass());

	private volatile CPDocConfiguration config = null;

	private CPDocRepository repository = null;

}
