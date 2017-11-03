import java.awt.Dimension;
import java.util.Arrays;
import java.util.Observer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

import com.ltree.crs516.client.BiologyHeaderDisplayer;
import com.ltree.crs516.client.CharacterDataDisplayer;
import com.ltree.crs516.client.DisplayTab;
import com.ltree.crs516.client.PrimaryHeaderDisplayer;
import com.ltree.crs516.client.ProfileDataDisplayer;
import com.ltree.crs516.client.ProfileDataTab;
import com.ltree.crs516.client.SecondaryHeaderDisplayer;
import com.ltree.crs516.client.TaxaDisplayer;
import com.ltree.crs516.client.WOD;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.controller.WODController;
import com.ltree.crs516.data.FileBasedDataService;

public final class Driver {
	
	private static final double GOLDEN_RATIO =(1 + Math.sqrt(5)) / 2; 
	private static final Dimension FRAME_SIZE 
				= new Dimension((int) (600 * GOLDEN_RATIO), 600);

	/**
	 * Starting point for the whole application. The input argument is not used.
	 */
	public static void main(String[] args) {
		
//When you have finished coding the configuration xml file you
//will use Spring to instantiate the WOD rather than the 'new' keyword.
		
		WOD wod = new WOD("World Ocean Database Browser");






//You will use the code between the markers for guidance as you write the 
//configuration file, guiConfig.xml. Later you will comment it all out.		
		
		//{{Marker 1
		WODController controller = new WODController(wod);				
		DisplayTab primaryHeaderTab = new DisplayTab();
		primaryHeaderTab.setTitle("Primary Headers ");
		DisplayTab characterDataTab = new DisplayTab();
		characterDataTab.setTitle("Character Data ");
		DisplayTab secondaryHeaderTab = new DisplayTab();
		secondaryHeaderTab.setTitle("Secondary Headers ");
		DisplayTab biologyHeaderTab = new DisplayTab();
		biologyHeaderTab.setTitle("Biology Headers ");
		DisplayTab taxaTab = new DisplayTab();
		taxaTab.setTitle("Taxa Sets ");
		ProfileDataTab profileDataTab = new ProfileDataTab();
		profileDataTab.setTitle("Profile Data");
		
		//Give the tabs displayers.
		primaryHeaderTab.setDisplayer(new PrimaryHeaderDisplayer());
		characterDataTab.setDisplayer(new CharacterDataDisplayer());
		secondaryHeaderTab.setDisplayer(new SecondaryHeaderDisplayer());
		biologyHeaderTab.setDisplayer(new BiologyHeaderDisplayer());
		taxaTab.setDisplayer(new TaxaDisplayer());
		profileDataTab.setDisplayer(new ProfileDataDisplayer());
		
		DisplayTab[] tabs = new DisplayTab[]{primaryHeaderTab, characterDataTab, 
				secondaryHeaderTab,biologyHeaderTab,taxaTab,profileDataTab};
		
		List<DisplayTab> tabList = Arrays.asList(tabs);
		
		Observer[] observers = new DisplayTab[]{primaryHeaderTab, characterDataTab, 
				secondaryHeaderTab,biologyHeaderTab,taxaTab,profileDataTab};
		List<Observer> observerList = Arrays.asList(observers);
		
		//Link the observers and observable.
		wod.setTabs(tabList);
		controller.setObservers(observerList);
		DataService dataService = new FileBasedDataService(null);
		controller.setDataService(dataService);
		wod.setController(controller);

		//}} end Marker 1
		
		wod.setSize(FRAME_SIZE);
		wod.setVisible(true);
	}
	
}
