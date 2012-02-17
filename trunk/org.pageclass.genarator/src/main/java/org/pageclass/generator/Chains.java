package org.pageclass.generator;

import java.io.IOException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Chains {
	public static HtmlPage doAction(String s, HtmlPage page) throws IOException {
		XmlManager xml = new XmlManager();
		HtmlPage final_page=null;
		int action_count = xml.getSettings(
				"//page[@value='" + s + "']/actions/action").size();
		for (int i = 1; i < action_count+1; i++) {
			if (xml.getSettings(
					"//page[@value='" + s + "']/actions/action[" + i
							+ "]/@type").get(0).equals("fill")) {
			    HtmlInput el=(HtmlInput) page.getFirstByXPath(xml.getSettings("//page[@value='" + s + "']/actions/action["+ i + "]/@path").get(0));
				el.setValueAttribute(xml.getSettings("//page[@value='" + s + "']/actions/action["+ i + "]/@text").get(0));
			}
			if (xml.getSettings(
					"//page[@value='" + s + "']/actions/action[" + i
							+ "]/@type").get(0).equals("click")) {
				HtmlElement el=(HtmlElement) page.getFirstByXPath(xml.getSettings("//page[@value='" + s + "']/actions/action["+ i + "]/@path").get(0));
				final_page=el.click();
			}
		}
		return final_page;
	}

}
