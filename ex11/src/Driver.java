import java.awt.Dimension;
import java.awt.Toolkit;

import com.ltree.crs516.client.BiologyDisplayHelper;
import com.ltree.crs516.client.CharacterDataDisplayHelper;
import com.ltree.crs516.client.DisplayTab;
import com.ltree.crs516.client.PrimaryDisplayerHelper;
import com.ltree.crs516.client.ProfileDataDisplayHelper;
import com.ltree.crs516.client.ProfileDataTab;
import com.ltree.crs516.client.SecondaryDisplayHelper;
import com.ltree.crs516.client.TaxaDisplayHelper;
import com.ltree.crs516.client.WOD;
import com.ltree.crs516.controller.WODController;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;

public class Driver {
	
	private static final double GOLDEN_RATIO = (1 + Math.sqrt(5)) / 2;
	private static final Dimension FRAME_SIZE 
			= new Dimension((int) (600 * GOLDEN_RATIO), 600);

	/**
	 * Starting point for the whole application. The input argument is not used.
	 */
	public static void main(String[] args) {
		WOD wod = new WOD("World Ocean Database Browser");
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
		DisplayTab profileDataTab = new ProfileDataTab();
		profileDataTab.setTitle("Profile Data");
		primaryHeaderTab.setHelper(new PrimaryDisplayerHelper());
		characterDataTab.setHelper(new CharacterDataDisplayHelper());
		secondaryHeaderTab.setHelper(new SecondaryDisplayHelper());
		biologyHeaderTab.setHelper(new BiologyDisplayHelper());
		taxaTab.setHelper(new TaxaDisplayHelper());
		profileDataTab.setHelper(new ProfileDataDisplayHelper());
		controller.addObserver(primaryHeaderTab);
		controller.addObserver(characterDataTab);
		controller.addObserver(secondaryHeaderTab);
		controller.addObserver(biologyHeaderTab);
		controller.addObserver(taxaTab);
		controller.addObserver(profileDataTab);
		wod.addTab(primaryHeaderTab);
		wod.addTab(characterDataTab);
		wod.addTab(secondaryHeaderTab);
		wod.addTab(biologyHeaderTab);
		wod.addTab(taxaTab);
		wod.addTab(profileDataTab);
		wod.setController(controller);
		DataService dataService = new FileBasedDataService(null);
		controller.setDataService(dataService);
		wod.setSize(FRAME_SIZE);
		try {
			Thread.sleep(2000); // Allow splash screen to be seen.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Splash screen is centered on monitor screen. 
		//Make wod frame appear at the same place.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int xLoc = screenSize.width/2-wod.getWidth()/2;
		int yLoc = screenSize.height/2-wod.getHeight()/2;
		wod.setLocation(xLoc, yLoc);
		wod.setVisible(true);
	}
}
