package de.olafkock.liferay.documentation.defaultimpl;

import com.liferay.portal.kernel.util.HtmlUtil;

import javax.servlet.http.HttpServletRequest;

public class ControlPanelSecondaryKeyResolver {
	public static String getSecondaryTopic(HttpServletRequest request, String portletId) {
		for (String p : SECONDARY_KEYS) {
			// we're operating on the decorated original servlet request, namespace still not resolved
			// will break in case this way of namespacing ever changes.
			String result = request.getParameter("_" + portletId + "_" + p);
			if (result != null)
				return HtmlUtil.escape(simplify(result));
		}
		return "-";
	}

	private static String simplify(String result) {
		if(result.startsWith("/")) {
			result = result.substring(1);
		}
		result = result.replace('/', '_');
		return result;
	}

	static final String[] SECONDARY_KEYS = new String[] { "toolbarItem", "type", "navigation", "tab", 
			"tabs1", "tabs2", "configurationScreenKey", "screenNavigationCategoryKey", "pid", 
			"factoryPid", "roleType", "mvcRenderCommandName", "mvcPath", "commerceAdminModuleKey"};

}
