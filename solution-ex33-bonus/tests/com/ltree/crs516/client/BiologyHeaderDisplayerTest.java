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

import com.ltree.crs516.domain.BiologyHeader;
import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.Station;

public class BiologyHeaderDisplayerTest {

	//TODO 1: Remove the start comment (/*) just after //{{Marker 1
	//and the end comment (*/) just before //}}end of Marker 1. You 
	//might have to do a Project | Clean to force a recompile of the 
	//code. There should be no errors.	
	//{{Marker 1
	
	private BiologyHeaderDisplayer testSubject;
	private Station station;

	@Before
	public void setUp() throws Exception {
		testSubject = new BiologyHeaderDisplayer();
		station = new Station();
	}

	

	@Test
	public void testWithNoDataPresent() {
		//station.setBiologyHeaders(createBiologyHeaders());
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("No Biology Headers Present"));
	}
	
	@Test
	public void testValues() {
		station.setBiologyHeaders(createBiologyHeaders());
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("0.110"));
		assertThat(displayString, containsString("1.110"));
		assertThat(displayString, containsString("2.110"));
	}

	@Test
	public void testHeadeersString() {
		station.setBiologyHeaders(createBiologyHeaders());
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("headerString0"));
		assertThat(displayString, containsString("headerString1"));
		assertThat(displayString, containsString("headerString2"));
	}

	@Test
	public void testValueMeanings() {
		station.setBiologyHeaders(createBiologyHeaders());
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("valueMeaning0"));
		assertThat(displayString, containsString("valueMeaning1"));
		assertThat(displayString, containsString("valueMeaning2"));
	}
	
	@Test
	public void DOMCheck(){
		try {
			station.setBiologyHeaders(createBiologyHeaders());
			String displayString = testSubject.createDisplayString(station);
			//Remove the "<html>" at the front to make it well formed.
			displayString = displayString.substring("<html>".length());
		    InputSource in = new InputSource(new StringReader(displayString));
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();

			//Root element should now be table.
			assertThat(doc.getDocumentElement().getNodeName(), equalTo("table"));

			//Should have four rows in table including header row.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(4));
			
			//Check the headers.
			Element rowElement = (Element)nList.item(0);
			NodeList rowNodeList = rowElement.getElementsByTagName("th");
			//Should have four columns.
			assertThat(rowNodeList.getLength(), equalTo(4));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("OCL code"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Meaning"));
			assertThat(rowNodeList.item(2).getTextContent(), equalTo("Value"));
			assertThat(rowNodeList.item(3).getTextContent(), equalTo("Value Meaning"));
			
			//check the data rows.
			for (int row = 1; row < nList.getLength(); row++) {
				rowElement = (Element)nList.item(row);
				rowNodeList = rowElement.getElementsByTagName("td");
				assertThat(rowNodeList.getLength(), equalTo(4));
				int index = row-1;
				assertThat(rowNodeList.item(0).getTextContent(), equalTo(""+index));
				assertThat(rowNodeList.item(1).getTextContent(), equalTo("headerString"+index));
				assertThat(rowNodeList.item(2).getTextContent(), equalTo(index+".110"));
				assertThat(rowNodeList.item(3).getTextContent(), equalTo("valueMeaning"+index));
			}
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	private BiologyHeader[] createBiologyHeaders() {
		BiologyHeader[] headers = new BiologyHeader[3];
		for (int i = 0; i < 3; i++) {
			BiologyHeader header = new BiologyHeader();
			Datum datum = new Datum.Builder(i + .11, 2, 3, 3)
					.meaning("meaning" + i).originatorsFlag(i)
					.qualityFlag(2 * i)
					.qualityFlagString("qualityFlagString" + i)
					.build();
			header.setValue(datum);
			header.setHeaderString("headerString"+i);
			header.setValueMeaning("valueMeaning"+i);
			header.setHeaderCode(i);
			headers[i] = header;
		}
		return headers;
	}
//}} end Marker 1

}
