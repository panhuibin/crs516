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

public class TaxaDisplayerTest {
	
	private TaxaDisplayer testSubject;
	private Station station =SamplesGenerator.getTestStation();

	@Before
	public void setUp() throws Exception {
		testSubject = new TaxaDisplayer();
	}

	@Test
	public void DOMCheck(){
		
		try {
			String displayString = testSubject.createDisplayString(station);
			//Add the "</html>" at the end to make it well formed.
			displayString = displayString+"</html>";
			System.out.println(displayString);
			InputSource in = new InputSource(new StringReader(displayString));
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(in);
			//Root element should now be table.
			Element root = doc.getDocumentElement();
			assertThat(root.getNodeName(), equalTo("html"));
			//Should have 86 rows in table
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(86));

			//Get the first table
			NodeList tableList = doc.getElementsByTagName("table");
			assertThat(tableList.getLength(), equalTo(8));
			
			Element header1 = (Element)root.getElementsByTagName("h1").item(0);
			assertThat(header1.getTextContent(), equalTo("Taxonomic Data Sets and Integrated Parameters: 8 sets."));
			NodeList h2Nodes = root.getElementsByTagName("h2");
			for(int i = 0; i< h2Nodes.getLength();i++){
				Element header2 = (Element)h2Nodes.item(i);
				assertThat(header2.getTextContent(), equalTo("Set "+(i+1)+":"));
			}
			XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr 
		     = xpath.compile("//html/table/tr");
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList tableRows = (NodeList) result;
		    String[] expected = new String[]{"Code", "Variable number (>0 ITIS taxon code,<0 WOD taxon or group code)", "0", "", "0", "accepted value", "27", "CBV calculation method", "3", "", "Quality Flag", "accepted value", "2", "lower_depth;     meters", "5", "", "0", "accepted value", "28", "...", "4205000", "Value Meaning", "0", "accepted value", "3", "Taxon_lifestage", "113", "per sample -- Parameter per sample", "0", "accepted value", "29", "Biological Grouping Code (BGC)", "Value", "PGC code=4357500, Rank=genus, Taxonomic Name -[ Author or comments ]-=Oikopleura", "0", "accepted value", "10", "Taxon_modifier", "68", "", "0", "accepted value", "30", "Code Meaning", "88803", "", "0", "accepted value", "20", "Comparable Biological Value (CBV)", "68.40", "", "0", "Quality Flag Meaning", "1", "upper_depth;     meters", "25.0", "NAUPLIUS/NAUPLII", "0", "accepted value", "27", "CBV calculation method", "3", "", "Quality Flag", "accepted value", "2", "lower_depth;     meters", "43", "", "0", "accepted value", "28", "...", "4282040", "Value Meaning", "0", "accepted value", "3", "Taxon_lifestage", "16", "per sample -- Parameter per sample", "0", "accepted value", "29", "Biological Grouping Code (BGC)"};
			for(int i=0; i<tableRows.getLength();i++){
				String content = tableRows.item(i).getChildNodes().item(i%6).getTextContent();
				assertThat(content, equalTo(expected[i]));
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
