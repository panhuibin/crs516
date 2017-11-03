package com.ltree.crs516.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ltree.crs516.data.SamplesGenerator;
import com.ltree.crs516.domain.IStation;
import com.ltree.crs516.domain.Station;

public class BiologyHeaderDisplayerTest {

	private BiologyHeaderDisplayer testSubject;
	private IStation station =SamplesGenerator.getTestStation();

	@Before
	public void setUp() throws Exception {
		testSubject = new BiologyHeaderDisplayer();
	}

	@Test
	public void testWithNoDataPresent() {
		station = new Station(); //empty station
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("No Biology Headers Present"));
	}
	
	/**
	 * Test that data corresponds to known values from sample that NOAA gives.
	 */
	@Test
	public void DOMCheck(){
		try {
			String displayString = testSubject.createDisplayString(station);
			//Add the "</html>" at the end to make it well formed.
			displayString = displayString+"</html>";
		    InputSource in = new InputSource(new StringReader(displayString));
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();
			//Rroot element should now be table.
			assertThat(doc.getDocumentElement().getNodeName(), equalTo("html"));
			//Should have 9 rows in table.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(9));
			//Check the headers.
			Element rowElement = (Element)nList.item(0);
			NodeList rowNodeList = rowElement.getElementsByTagName("th");
			//Should have four columns.
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("OCL code"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Meaning"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("Value"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("Value Meaning"));
			//Check the data rows.
			//Inspect each row.
			rowElement = (Element)nList.item(1);
			rowNodeList = rowElement.getElementsByTagName("td");
			//Should have four columns.
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("2"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Sampling duration (minutes)"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("18.00"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo(""));
			rowElement = (Element)nList.item(2);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("3"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Mesh size (µm)"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("76"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo(""));
			rowElement = (Element)nList.item(3);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("4"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Type of tow"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("2"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("VERTICAL TOW"));
			rowElement = (Element)nList.item(4);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("7"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Gear code"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("103"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("Plankton Net (Silk)"));
			rowElement = (Element)nList.item(5);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("9"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Net mouth area (m2)"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("0.05"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo(""));
			rowElement = (Element)nList.item(6);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("13"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Count method"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("11"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("ALIQUOT AND FULL; counted aliquout  then counted FULL"));
			rowElement = (Element)nList.item(7);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("16"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Sampling start time (GMT)"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("10.37"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo(""));
			rowElement = (Element)nList.item(8);
			rowNodeList = rowElement.getElementsByTagName("td");
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("30"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Accession number for the biology"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("9500110"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("0/ 0/1995 10/10/1996 BECKER; PETER"));
		} catch (Exception e) {
			e.printStackTrace();
		  }
	}

}
