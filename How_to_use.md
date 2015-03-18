# Introduction #

In this article I try to explain how to use this project.


# Details #

  * irst you must download latest stable build from here.
  * nzip it somewhere in your system.
  * un start.bat file.
> In result you will see comand window with debug information, and as the end generator create some .java class files.

# config.xml #
In this file you told generator what pages he must visit, what tags from that pages you need and what you must do before get this tags.
Let me explain:

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

in this example you say to generator:
  1. go to page http://www.google.com
  1. create file GooglePage.java for that page
  1. fill input with class='gsfi' by text='test'
  1. click on button with name='btnK'
  1. after than take all tags input, table and form