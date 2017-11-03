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
import com.ltree.crs516.domain.Station;

public class SecondaryHeaderDisplayerTest {
	
	private SecondaryHeaderDisplayer testSubject;
	private Station station =SamplesGenerator.getTestStation();

	@Before
	public void setUp() throws Exception {
		testSubject = new SecondaryHeaderDisplayer();
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
			//Should have 8 rows in table.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(8));

			//Get the first table.
			NodeList tableList = doc.getElementsByTagName("table");
			assertThat(tableList.getLength(), equalTo(1));
			
			Element header1 = (Element)root.getElementsByTagName("h1").item(0);
			assertThat(header1.getTextContent(), equalTo("Secondary Headers"));

			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr 
		     = xpath.compile("//html/table/tr");
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList trNodes = (NodeList) result;
			assertThat(trNodes.getLength(), equalTo(8));
		
			String[] answers = new String[]{"Code", "NODC Accession Number", "1427", "SCRIPPS INSTITUTION OF OCEANOGRAPHY; LA JOLLA; CA"};

			for(int i=0; i<4;i++){
				String tdContent = trNodes.item(i).getChildNodes().item(i).getTextContent();
				assertThat(tdContent, equalTo(answers[i]));
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
