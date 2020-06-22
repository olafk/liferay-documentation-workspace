# ControlMenu documentation

This plugin shows up to two buttons in ControlMenu:

One will pop up a documentation toast, much like the [ControlPanelDocumentation](https://web.liferay.com/marketplace/-/mp/application/170064253) plugin 

The other will either play an audio file to describe a page, or play an illustrated audioguide, that can remote control some operations on the page and highlight some areas. 

## Required additional plugins

* documentation-themecontributor must be deployed to provide the necessary Javascript & CSS infrastructure.
* documentation-api and at least one documentation-resolver must be deployed to provide data.