package de.olafkock.liferay.documentation.controlpanel;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
	    id = "de.olafkock.liferay.documentation.controlpanel.CPDocConfiguration"
	    , localization = "content/Language"
	    , name = "controlpanel-resolver-configuration-name"
	    , description = "controlpanel-resolver-configuration-description"
	)
public interface CPDocConfiguration {
    
	@Meta.AD(
            deflt = "https://www.olafkock.de/liferay/controlpaneldocumentation/${version}/content.json",
            description = "directory-url-description",
            name = "directory-url",
            required = false
        )

	public String directoryURL();
}