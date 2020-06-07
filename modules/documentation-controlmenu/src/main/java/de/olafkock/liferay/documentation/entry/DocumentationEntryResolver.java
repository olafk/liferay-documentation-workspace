package de.olafkock.liferay.documentation.entry;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

public class DocumentationEntryResolver {
	public static DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		DocumentationEntry entry = (DocumentationEntry) request.getAttribute("DOCUMENTATION_ENTRY");
		if(entry != null) {
			return entry;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		ExpandoBridge expandoBridge = layout.getExpandoBridge();

		try {
			String audioguideAudio = (String) expandoBridge.getAttribute("audioguide-audio");
			String audioguideScript = (String) expandoBridge.getAttribute("audioguide-script");
			entry = new DocumentationEntry(audioguideAudio, audioguideScript, "toast-url");
		} catch (Exception e) {
			try {
				expandoBridge.addAttribute("audioguide-audio", ExpandoColumnConstants.STRING);
				expandoBridge.addAttribute("audioguide-script", ExpandoColumnConstants.STRING);
			} catch (PortalException e1) {
				e1.printStackTrace();
			}
		}
		
		return entry;
	}
	
	static Log _log = LogFactoryUtil.getLog(DocumentationEntryResolver.class);
}
