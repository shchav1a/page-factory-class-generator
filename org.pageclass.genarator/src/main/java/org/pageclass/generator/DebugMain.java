package org.pageclass.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DebugMain {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		XmlManager xml = new XmlManager();
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Map<String, List<String>> tags = new HashMap<String, List<String>>();
		List<String> pages = xml.getSettings("//page/@value");
		int i = 0;
		for (String s : pages) {
			tags.put(pages.get(i),
					xml.getSettings("//page[@value='" + s + "']/target/@tag"));
			i++;
		}
		System.out.println("Config file:");
		System.out.println(pages);
		System.out.println(tags);
		for (String s : pages) {
			String filename=xml.getSettings("//page[@value='" + s + "']/classname/@value").get(0);
			File test = new File(filename + ".java");
			test.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(test, true));
			bw.write("import org.openqa.selenium.WebElement;\n import org.openqa.selenium.support.FindBy;\n import org.openqa.selenium.support.How; \n");
			bw.write("public final class "+filename+" {\n");
			driver.get(s);
			Chains.doAction(s,driver);
			for (String st : tags.get(s)) {
				List<WebElement> elements = driver.findElements(By.xpath("//"
						+ st));
				int j=0;
				for (WebElement e : elements) {
					bw.write("@FindBy(how = How.XPATH, using = \"");
					bw.write("//"
							+ e.toString().replaceFirst(" ", "[@")
									.replaceAll(" ", " and @")
									.replaceAll("<", "").replaceAll(">", "]")
									.replaceAll(" and @/", "")
									.replaceAll("\"", "'"));
					bw.write("\")\n");
					bw.write("WebElement "+st+j+";\n");
					j++;
				}

				/*
				 * System.out .println("//" + elements.get(0).toString()
				 * .replaceFirst(" ", "[@") .replaceAll(" ", " and @")
				 * .replaceAll("<", "") .replaceAll(">", "]")
				 * .replaceAll(" and @/", ""));
				 */
			}
			bw.write("}");
			bw.close();
		}
	}

}
