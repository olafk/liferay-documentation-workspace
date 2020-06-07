package de.olafkock.liferay.documentation.controlpanel;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
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

/**
 * @author olaf
 */
@Component(
	immediate = true,
	configurationPid = "de.olafkock.liferay.documentation.controlpanel.CPDocConfiguration",
	property = {
	},
	service = DocumentationResolver.class
)
public class ControlpanelDocumentationResolver implements DocumentationResolver {

	@Override
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		boolean doFilter = themeDisplay.getTheme().isControlPanelTheme()
				&& ! "pop_up".equals(request.getParameter("p_p_state"));
		
		
		if(doFilter) {
			String portletId = PortalUtil.getPortletId(request);
			String secondary = getSecondaryTopic(request, portletId);
			log.info("ControlPanel: Targeted Portlet ID: " + portletId + "/" + secondary);
			return repository.getEntry(portletId, secondary);
		}	

		return null;
	}
	
	protected String getSecondaryTopic(HttpServletRequest request, String portletId) {
		for (String p : SECONDARY_KEYS) {
			String result = request.getParameter("_" + portletId + "_" + p);
			if (result != null)
				return HtmlUtil.escape(simplify(result));
		}
		return "-";
	}

	private String simplify(String result) {
		if(result.startsWith("/")) {
			result = result.substring(1);
		}
		result = result.replace('/', '_');
		return result;
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

	
	
	
	
	
	
	
	
	
	
	public DocumentationEntry getDocumentationEntryFake(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		boolean doFilter = themeDisplay.getTheme().isControlPanelTheme()
				&& ! "pop_up".equals(request.getParameter("p_p_state"));
		
		if(doFilter) {
			return new DocumentationEntry() {
				@Override
				public boolean isScripted() {
					return false;
				}
				@Override
				public boolean isAudio() {
					return true;
				}
				@Override
				public boolean isDocumented() {
					return true;
				}
				@Override
				public String getScriptURL() {
					return null;
				}
				@Override
				public String getAudioURL() {
					return "https://www.olafkock.de/liferay/audioguide/controlpanel.mp3";
				}
				@Override
				public String getDocumentationUrl() {
					return "http://localhost:8080/";
				}
			};
		} else 
			return null;
	}

	Log log = LogFactoryUtil.getLog(getClass());

	private CPDocConfiguration config = null;

	private CPDocRepository repository = null;
	
	static final String[] SECONDARY_KEYS = new String[] { "toolbarItem", "type", "navigation", "tab", 
			"tabs1", "tabs2", "configurationScreenKey", "pid", "factoryPid", "roleType",
			"mvcRenderCommandName",	"mvcPath", "commerceAdminModuleKey"};
	
}