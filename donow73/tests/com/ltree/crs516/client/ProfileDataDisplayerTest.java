package com.ltree.crs516.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ltree.crs516.data.SamplesGenerator;
import com.ltree.crs516.domain.IStation;

public class ProfileDataDisplayerTest {
	
	private ProfileDataDisplayer testSubject;
	private IStation station =SamplesGenerator.getTestStation();

	@Before
	public void setUp() throws Exception {
		testSubject = new ProfileDataDisplayer();
	}

	@Test
	public void DOMCheck(){
		
		try {
			String displayString = testSubject.createDisplayString(station);
			//Add the "</html>" at the end to make it well formed.
			displayString = displayString+"</html>";
			InputSource in = new InputSource(new StringReader(displayString));
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(in);
			//Root element should now be table.
			Element root = doc.getDocumentElement();
			assertThat(root.getNodeName(), equalTo("html"));
			//Should have 56 rows in table.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(56));

			//Get the first table.
			NodeList tableList = doc.getElementsByTagName("table");
			assertThat(tableList.getLength(), equalTo(13));
			
			Element header1 = (Element)root.getElementsByTagName("h1").item(0);
			assertThat(header1.getTextContent(), equalTo("Profile Data"));
			Element header2 = (Element)root.getElementsByTagName("h2").item(0);
			assertThat(header2.getTextContent(), equalTo("Details for each level"));
			
			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr 
		     = xpath.compile("//html/table");
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList topLeveltablenodes = (NodeList) result;
			assertThat(topLeveltablenodes.getLength(), equalTo(5));
			
			for(int i=0; i<4;i++){
				Element topLevelTableElement = (Element)topLeveltablenodes.item(i);
				NodeList innerTableNodeList = topLevelTableElement.getElementsByTagName("table");
				assertThat(innerTableNodeList.getLength(), equalTo(2));
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
}
