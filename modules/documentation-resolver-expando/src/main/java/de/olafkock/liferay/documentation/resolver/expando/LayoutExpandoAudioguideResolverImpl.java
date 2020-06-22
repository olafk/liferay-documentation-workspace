package de.olafkock.liferay.documentation.resolver.expando;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
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
public class LayoutExpandoAudioguideResolverImpl implements DocumentationResolver {

	@Override
	public int getOrder() {
		return 0;
	}
	
	@Override
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		DocumentationEntryImpl entry = null;
		
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			Layout layout = themeDisplay.getLayout();
			ExpandoBridge expandoBridge = layout.getExpandoBridge();

			lazyInitialize(expandoBridge);

			String audioguideAudio = (String) expandoBridge.getAttribute("audioguide-audio");
			String audioguideScript = (String) expandoBridge.getAttribute("audioguide-script");
			String documentationUrl = (String) expandoBridge.getAttribute("documentation-url");
			
			if((audioguideAudio != null && ! audioguideAudio.isEmpty()) 
					|| (audioguideScript != null && ! audioguideScript.isEmpty())
					|| (documentationUrl != null && ! documentationUrl.isEmpty())) {
				String layoutType = layout.getType();
				if("content".equals(layoutType)) {
					log.info("resolving for content page, mode " + request.getParameter("p_l_mode"));
					if("edit".equals(request.getParameter("p_l_mode"))) {
						entry = new DocumentationEntryImpl(
								StringUtil.replace(documentationUrl, "${mode}", "edit"),
								StringUtil.replace(audioguideAudio,  "${mode}", "edit"),
								StringUtil.replace(audioguideScript, "${mode}", "edit")
							);
					} else {
						entry = new DocumentationEntryImpl(
								StringUtil.replace(documentationUrl, "${mode}", "view"),
								StringUtil.replace(audioguideAudio,  "${mode}", "view"),
								StringUtil.replace(audioguideScript, "${mode}", "view")
							);
					}
				} else {
					entry = new DocumentationEntryImpl(documentationUrl, audioguideAudio, audioguideScript);
				}
				log.info("[" + entry.getDocumentationUrl() + "," + entry.getAudioURL() + "," + entry.getScriptURL() + "]");
			}
		} catch (Exception e) {
			log.error(e);
		}
		return entry;
	}

	private void lazyInitialize(ExpandoBridge expandoBridge) throws PortalException {
		if(!expandoBridge.hasAttribute("audioguide-audio")) {
			expandoBridge.addAttribute("audioguide-audio", ExpandoColumnConstants.STRING);
		}
		if(!expandoBridge.hasAttribute("audioguide-script")) {
			expandoBridge.addAttribute("audioguide-script", ExpandoColumnConstants.STRING);
		}
		if(!expandoBridge.hasAttribute("documentation-url")) {
			expandoBridge.addAttribute("documentation-url", ExpandoColumnConstants.STRING);
		}
	}

	Log log = LogFactoryUtil.getLog(getClass());
}
