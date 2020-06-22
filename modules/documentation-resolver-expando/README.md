# Expando Resolver for Documentation

This resolver will read a layout's (page's) expando fields

* audioguide-audio
* audioguide-script
* documentation-url

to resolve any documentation you might have for a particular page. 

If you _only_ have audio, use `audioguide-audio`. If you have _illustrated_ audio, use `audioguide-script`, which has the URL for the audio file embedded in it. 

The expando fields are created automatically once this plugin is utilized - no need for manual intervention.

For ContentPages (only), you may provide different URLs depending on the `view` or `edit` mode. This is done by embedding the placeholder `${mode}` in the URL - it will be replaced with either `view`or `edit`. 

Example: `https://www.olafkock.de/liferay/audioguide/contentpage-${mode}-generic.json`