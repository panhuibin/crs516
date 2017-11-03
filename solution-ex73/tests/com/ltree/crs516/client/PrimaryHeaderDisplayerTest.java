package com.ltree.crs516.client;

import static org.hamcrest.MatcherAssert.assertThat;
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
import com.ltree.crs516.domain.Station.ProfileType;

public class PrimaryHeaderDisplayerTest {

	private PrimaryHeaderDisplayer testSubject;
	private IStation station =SamplesGenerator.getTestStation();

	@Before
	public void setUp() throws Exception {
		testSubject = new PrimaryHeaderDisplayer();
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
			assertThat(doc.getDocumentElement().getNodeName(), equalTo("html"));
			//Should have 21 rows in table.
			NodeList nList = doc.getElementsByTagName("tr");
			assertThat(nList.getLength(), equalTo(21));

			//Get the first table.
			NodeList tableList = doc.getElementsByTagName("table");
			assertThat(tableList.getLength(), equalTo(2));
			
			Element firstTable = (Element)tableList.item(0);
			NodeList rowNodeList = firstTable.getElementsByTagName("tr");
			assertThat(rowNodeList.getLength(), equalTo(13));

			String[] values = new String[]{"OCL unique station number","Cruise code", "NODC country code","Country","Year","Month","Day","Time","Latitude","Longitude","Profile type","Number of levels","# Variables in profile"};
			String[] values1 = new String[]{"67064","11203", "31","null","1934","8","7","10.37","61.93","-172.27",ProfileType.OBSERVED.name(),"4","7"};
			for(int i=0; i<rowNodeList.getLength();i++){
				NodeList dataNodeList = ((Element)rowNodeList.item(i)).getElementsByTagName("td");
				assertThat(dataNodeList.item(0).getTextContent(), equalTo(values[i]));
				assertThat(dataNodeList.item(1).getTextContent(), equalTo(values1[i]));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
