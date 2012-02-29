package org.pageclass.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DebugMain {

	/**
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		XmlManager xml = new XmlManager();
		WebClient webClient = new WebClient();
		 webClient.setJavaScriptEnabled(Boolean.parseBoolean(xml.getSettings("//javascript/@value").get(0)));
		 webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		 webClient.waitForBackgroundJavaScript(30000);
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
			
			HtmlPage page =webClient.getPage(s);
			page=Chains.doAction(s,page);
			
			int j=0;
			for (String st : tags.get(s)) {
				List<HtmlElement> elements = (List<HtmlElement>) page.getByXPath("//"
							+ st);
				
				
				for (HtmlElement e : elements) {
					String id=e.getId();
					String res="";
					if(!id.isEmpty()){
						id="@id='"+id+"'";
						res="[ "+id+" ]";
					}
					bw.write("@FindBy(how = How.XPATH, using = \"");
					bw.write(""
							+e.getCanonicalXPath()+res);		
					bw.write("\")\n");
					bw.write("WebElement "+st.subSequence(0, st.indexOf("["))+j+";\n");
					j++;
				}
			}
			bw.write("}");
			bw.close();
		}
	}

}
