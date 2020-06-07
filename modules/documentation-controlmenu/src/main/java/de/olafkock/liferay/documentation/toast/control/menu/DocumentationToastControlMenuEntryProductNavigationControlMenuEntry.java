package de.olafkock.liferay.documentation.toast.control.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import de.olafkock.liferay.documentation.api.DocumentationEntry;
import de.olafkock.liferay.documentation.api.DocumentationResolver;
import de.olafkock.liferay.documentation.metaresolver.DocumentationMetaResolverImpl;

/**
 * @author olaf
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=1"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class DocumentationToastControlMenuEntryProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIcon(HttpServletRequest request) {
		return "caret-double";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "show-documentation-toast");
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return "javascript:toast_trigger();";
	}

	@Override
		public String getLinkCssClass(HttpServletRequest httpServletRequest) {
			return "nothidden toastlink";
		}
	
	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		DocumentationEntry entry = documentationResolver.getDocumentationEntry(request);
		return entry != null && entry.isDocumented();
	}
		
	@Override
	public Map<String, Object> getData(HttpServletRequest request) {
		DocumentationEntry entry = documentationResolver.getDocumentationEntry(request);
		Map<String, Object> result = new HashMap<String, Object>();
		if(entry != null && entry.isDocumented()) {
			result.put("documentation-toast", entry.getDocumentationUrl());
			result.put("documentation-edit", "xxxx");
			result.put("identifier", "toast");
		}
		return result;
	}
	
	@Reference( 
			cardinality = ReferenceCardinality.MULTIPLE,
		    policyOption = ReferencePolicyOption.GREEDY,
		    unbind = "doUnRegister" 
	)
	void doRegister(DocumentationResolver resolver) {
		documentationResolver.doRegister(resolver);
	}
	
	void doUnRegister(DocumentationResolver resolver) {
		documentationResolver.doUnRegister(resolver);
	}

	DocumentationMetaResolverImpl documentationResolver = new DocumentationMetaResolverImpl();
}