package org.pageclass.generator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Chains {

	public static void doAction(String s, WebDriver driver) {
		System.out.println("do action");
		XmlManager xml = new XmlManager();
		int action_count = xml.getSettings(
				"//page[@value='" + s + "']/actions/action").size();
		System.out.println(action_count);
		for (int i = 1; i < action_count+1; i++) {
			if (xml.getSettings(
					"//page[@value='" + s + "']/actions/action[" + i
							+ "]/@type").get(0).equals("fill")) {
				driver.findElement(
						By.xpath(xml.getSettings(
								"//page[@value='" + s + "']/actions/action["
										+ i + "]/@path").get(0))).sendKeys(
						xml.getSettings(
								"//page[@value='" + s + "']/actions/action["
										+ i + "]/@text").get(0));
			}
			if (xml.getSettings(
					"//page[@value='" + s + "']/actions/action[" + i
							+ "]/@type").get(0).equals("click")) {
				driver.findElement(
						By.xpath(xml.getSettings(
								"//page[@value='" + s + "']/actions/action["
										+ i + "]/@path").get(0))).click();
			}
			if (xml.getSettings(
					"//page[@value='" + s + "']/actions/action[" + i
							+ "]/@type").get(0).equals("wait")) {

				try {
					Thread.sleep(Long.parseLong(xml.getSettings(
							"//page[@value='" + s + "']/actions/action[" + i
									+ "]/@time").get(0)));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

}
