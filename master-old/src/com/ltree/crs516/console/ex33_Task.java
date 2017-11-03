package com.ltree.crs516.console;

import java.io.File;
import java.io.IOException;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.domain.Station;

public final class Task {

	private File file = new File(DATA_DIR + "OSDO5711.gz");

	private void execute() {
#
#//TODO 1: Note: the code below will need to be in a try/catch block
#// but we shall first ignore that.		
#		
@		try(DataService dataService = createDataService()){
#		
#		//{{Marker 1	
#	
&		DataService dataService = createDataService();
#
^//TODO 1,2: The method createDataService() below will get you a DataService. 
^//Loop through all the stations of the DataService printing the 
^//number of bytes to the console.
#			//TODO 2: 
#			//Write a foreach loop going through all the stations of 
#			//dataService and printing the number of bytes in the profile
#			//to the console. 
#
@			for (Station station : dataService) {
@				System.out.println(station.getBytesInProfile());
@			}
$
$
$
&			//}}end Marker 1	
@		} catch (IOException e) {
@			e.printStackTrace();
@		}
%TODO 2:<br/>//{{Marker 1<br/>for (Station station : dataService) {<br/>&#160;&#160;System.out.println(station.getBytesInProfile());<br/>}<br/>//}}end Marker 1	

&//TODO 3: Highlight the code from  //{{Marker 1 to //}}end Marker 1, 
&//right-click the highlighted code | Surround With | Try/catch.
&//Create a pair of parentheses next to 'try' so that 'try{' becomes 'try(){'.
&//Move the declaration and instantiation of Dataservice into the parentheses. 
&//Now you have a try-with-resources statement. 

%TODO 3:<br/>try(DataService dataService = createDataService()){<br/>&#160;&#160;//{{Marker 1<br/><br/>...<br/>&#160;&#160;//}}end Marker 1<br/>} catch (IOException e) {<br/>&#160;&#160;e.printStackTrace();<br/>}

	}

	public static void main(String[] args) {
		new Task().execute();
	}

	private DataService createDataService() {
		DataService dataService = new FileBasedDataService(null);
		dataService.load(file);
		while (dataService.isLoading()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return dataService;
	}
	
}
