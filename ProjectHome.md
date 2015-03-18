# UPDATED #
Now you can specify in config.xml not only web-pages, you can specify web-pages on your local machine throgh file protocol.
Example:
[file:///C:/Doc/Test.htm](file:///C:/Doc/Test.htm)

# About this project #
This project can help you create java class that you can easy use with Selenium PageFactory object.
You can specify elements and pages for which you want to create java class.

## How to use ##

  * Download latest build
  * Unzip
  * Edit config.xml
  * Run generator.jar
  * In the folder you can saw several .java files

## Customize config.xml ##
```
<?xml version="1.0" encoding="UTF-8"?>
<pages>
	<page value="http://www.google.com">
	<classname value="GooglePage"/>
		<actions>
			<action type="fill" path="//input[@class='gsfi']" text="test"/>
			<action type="click" path="//input[@name='btnK']"/>
		</actions>
		<target tag="input" />
		<target tag="table" />
		<target tag="form" />
	</page>
</pages>
```