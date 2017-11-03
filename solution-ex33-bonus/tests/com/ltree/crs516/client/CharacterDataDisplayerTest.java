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

import com.ltree.crs516.domain.CharDataEntry;
import com.ltree.crs516.domain.CharDataEntry.CharDataType;
import com.ltree.crs516.domain.PrincipalInvestigator;
import com.ltree.crs516.domain.Station;

public class CharacterDataDisplayerTest {
	//TODO 1: Remove the start comment (/*) just after //{{Marker 1
	//and the end comment (*/) just before //}}end of Marker 1. You 
	//might have to do a Project | Clean to force a recompile of the 
	//code. There should be no errors.	
	
	
	//{{Marker 1
	private CharacterDataDisplayer testSubject;
	private Station station;

	@Before
	public void setUp() throws Exception {
		testSubject = new CharacterDataDisplayer();
		station = new Station();
	}

	@Test
	public void testWithNoDataPresent() {
		String displayString = testSubject.createDisplayString(station);
		assertThat(displayString, containsString("No Character Data Present"));
	}
	
	@Test
	public void DOMCheck(){
		try {
			station.setCharacterDataEntries(createCharacterDataEntries());
			String displayString = testSubject.createDisplayString(station);
			//Add "</html>" to the end to make it well formed.
			displayString = displayString+"</html>";
		    InputSource in = new InputSource(new StringReader(displayString));
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();
			//Root element should now be table.
			assertThat(doc.getDocumentElement().getNodeName(), equalTo("html"));
			Element header1 = (Element)doc.getElementsByTagName("h1").item(0);
			assertThat(header1.getTextContent(), equalTo("Character Data and Principal Investigator"));

			Element header3 = (Element)doc.getElementsByTagName("h3").item(0);
			assertThat(header3.getTextContent(), equalTo("There are 3 entries"));

			//Should have 8 rows in table including subtable.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(8));
			
			//Check the headers.
			Element rowElement = (Element)nList.item(0);
			NodeList rowNodeList = rowElement.getElementsByTagName("th");
			//Should have four columns.
			assertThat(rowNodeList.getLength(), equalTo(2));
			assertThat(rowNodeList.item(0).getTextContent(), equalTo("Entry Type"));
			assertThat(rowNodeList.item(1).getTextContent(), equalTo("Value"));
			
			//Check the data rows.
			//Get the first child called table
			Element outerTable = (Element)doc.getElementsByTagName("table").item(0);
			NodeList rowsInOuterTable = outerTable.getChildNodes();
			assertThat(rowsInOuterTable.getLength(), equalTo(4));
			
			//Inspect each row.
			//First one should be <tr><th>Entry Type</th><th>Value</th></tr>
			NodeList piHeaderNodes = rowsInOuterTable.item(0).getChildNodes();
			assertThat(piHeaderNodes.item(0).getTextContent(), equalTo("Entry Type"));
			assertThat(piHeaderNodes.item(1).getTextContent(), equalTo("Value"));

			//Second one should be <tr><td >ORIGINATORS_CRUISE</td><td>0</td></tr>.
			NodeList piRowDataNodes = rowsInOuterTable.item(1).getChildNodes();
			assertThat(piRowDataNodes.item(0).getTextContent(), equalTo("ORIGINATORS_CRUISE"));
			assertThat(piRowDataNodes.item(1).getTextContent(), equalTo("0"));

			//Third one should be <tr><td >ORIGINATORS_STATION_CODE</td><td>1</td></tr>.
			piRowDataNodes = rowsInOuterTable.item(2).getChildNodes();
			assertThat(piRowDataNodes.item(0).getTextContent(), equalTo("ORIGINATORS_STATION_CODE"));
			assertThat(piRowDataNodes.item(1).getTextContent(), equalTo("1"));
			
			// Fourth row should look like
			
			// <tr><td >PRINCIPAL_INVESTIGATOR</td>
			//	<td>
			//	   <table border='0' id='investigators'>
			//	      <tr><th>Code</th><th>Name</th></tr>
			//	      <tr><td>0</td><td>piName0</td></tr>
			//	      <tr><td>1</td><td>piName1</td></tr>
			//	      <tr><td>2</td><td>piName2</td></tr>
			//	    </table>
			//	</td>
			// </tr>.
			
			piRowDataNodes = rowsInOuterTable.item(3).getChildNodes();
			assertThat(piRowDataNodes.item(0).getTextContent(), equalTo("PRINCIPAL_INVESTIGATOR"));
			Element innerTable = ((Element)(piRowDataNodes.item(1)).getFirstChild());
			assertThat(innerTable.getAttribute("id"), equalTo("investigators"));
			NodeList innerTableRows = innerTable.getChildNodes();
			assertThat(innerTableRows.item(0).getChildNodes().item(0).getTextContent(),equalTo("Code"));
			assertThat(innerTableRows.item(0).getChildNodes().item(1).getTextContent(),equalTo("Name"));
			assertThat(innerTableRows.item(1).getChildNodes().item(0).getTextContent(),equalTo("0"));
			assertThat(innerTableRows.item(1).getChildNodes().item(1).getTextContent(),equalTo("piName0"));
			assertThat(innerTableRows.item(2).getChildNodes().item(0).getTextContent(),equalTo("1"));
			assertThat(innerTableRows.item(2).getChildNodes().item(1).getTextContent(),equalTo("piName1"));
			assertThat(innerTableRows.item(3).getChildNodes().item(0).getTextContent(),equalTo("2"));
			assertThat(innerTableRows.item(3).getChildNodes().item(1).getTextContent(),equalTo("piName2"));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}

	
	private CharDataEntry[] createCharacterDataEntries() {
		CharDataEntry[] entries = new CharDataEntry[3];
		for (int i = 0; i < 3; i++) {
			CharDataEntry entry = new CharDataEntry();
			entry.setType(CharDataType.values()[i]);
			if(entry.getType()==CharDataType.PRINCIPAL_INVESTIGATOR){
				PrincipalInvestigator[] investigators = new PrincipalInvestigator[3];
				for (int j = 0; j < 3; j++) {
					PrincipalInvestigator investigator = new PrincipalInvestigator();
					investigator.setPiCode(j);
					investigator.setPiName("piName"+j);
					investigator.setVariableCode(100+j);
					investigator.setVariableCodeString("variableCodeString"+j);
					investigators[j] = investigator;
				}
				entry.setPis(investigators);
			}
			else{entry.setData(""+i);}
			entries[i] = entry;
		}
		return entries;

	}
//}} end Marker 1


}
