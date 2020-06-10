package de.olafkock.liferay.documentation.resolver.commerce;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.api.DocumentationResolver;

/**
 * resolves documentation for commerce-pages created by the demo-accellerators based on 
 * the pages' names.
 *
 * Currently an empty implementation
 * 
 * @author olaf
 */


@Component(
		immediate=true,
		service=DocumentationResolver.class
		)
public class CommerceDocumentationResolverImpl implements DocumentationResolver {

	@Override
	public DocumentationEntry getDocumentationEntry(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		String friendlyURL = layout.getFriendlyURL();
		if("/dashboard".equals(friendlyURL)) {
			
		} else if("/catalog".equals(friendlyURL)) {
			
		} else if("/pending-orders".equals(friendlyURL)) {
			
		} else if("/placed-orders".equals(friendlyURL)) {
			
		} else if("/account-management".equals(friendlyURL)) {
			
		} else if("/report-product-defect".equals(friendlyURL)) {
			
		} else if("/knowledge-base".equals(friendlyURL)) {
			
		} else if("/sales-library".equals(friendlyURL)) {
			
		} else if("/account-management".equals(friendlyURL)) {
			
		} else if("/account-management".equals(friendlyURL)) {
			
		}   
		
		return null;
	}

	@Override
	public int getOrder() {
		return 1000;
	}

}
