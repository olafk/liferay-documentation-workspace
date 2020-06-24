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
 * Or rather: It <em>would</em> resolve... Currently an empty implementation
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
			// TODO
		} else if("/catalog".equals(friendlyURL)) {
			// TODO
		} else if("/pending-orders".equals(friendlyURL)) {
			// TODO
		} else if("/placed-orders".equals(friendlyURL)) {
			// TODO
		} else if("/account-management".equals(friendlyURL)) {
			// TODO
		} else if("/report-product-defect".equals(friendlyURL)) {
			// TODO
		} else if("/knowledge-base".equals(friendlyURL)) {
			// TODO
		} else if("/sales-library".equals(friendlyURL)) {
			// TODO
		} else if("/account-management".equals(friendlyURL)) {
			// TODO
		} else if("/account-management".equals(friendlyURL)) {
			// TODO
		}   
		
		return null;
	}

	@Override
	public int getOrder() {
		return 1000;
	}

}
