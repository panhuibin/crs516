package com.ltree.crs516.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ltree.crs516.client.BiologyHeaderDisplayer;
import com.ltree.crs516.client.CharacterDataDisplayer;
import com.ltree.crs516.client.DisplayTab;
import com.ltree.crs516.client.PrimaryHeaderDisplayer;
import com.ltree.crs516.client.ProfileDataDisplayer;
import com.ltree.crs516.client.ProfileDataTab;
import com.ltree.crs516.client.SecondaryHeaderDisplayer;
import com.ltree.crs516.client.TaxaDisplayer;
import com.ltree.crs516.client.WOD;
import com.ltree.crs516.controller.WODController;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;

//TODO 1: Add an annotation to this class to indicate that this is a 
//Spring configuration class.

%TODO 1: @Configuration<br/><br/>

public class GuiConfig {


//TODO 2: Annotate this method to indicate that it creates a Spring Bean.	

%TODO 2: @Bean<br/><br/>	

	public BiologyHeaderDisplayer biologyHeaderDisplayer() {
		return new BiologyHeaderDisplayer();
	}

	
	@Bean
	public DisplayTab biologyHeaderTab() {
		
//TODO 3: Create the DisplayTab and set its displayer to be an instance of 
//BiologyHeaderDisplayer created by the above method.

@//		DisplayTab biologyHeaderTab = new DisplayTab();
@//		biologyHeaderTab.setDisplayer(biologyHeaderDisplayer());
@//		return biologyHeaderTab;
$
$
		return null; //Edit this.
%TODO 3:<br/>DisplayTab biologyHeaderTab = new DisplayTab();<br/>biologyHeaderTab.setDisplayer(biologyHeaderDisplayer());<br/>return biologyHeaderTab;<br/><br/>

	}

//TODO 4: Add a method called characterDataDisplayer that will create and 
//return a CharacterDataDisplayer as a Spring bean.	
	
@//	@Bean
@//	public CharacterDataDisplayer characterDataDisplayer() {
@//	  return new CharacterDataDisplayer();
@//	}
	
$
$
$
$
%TODO 4:<br/>@Bean<br/>public CharacterDataDisplayer characterDataDisplayer() {<br/>&#160;&#160;return new CharacterDataDisplayer();<br/>}<br/><br/>

	@Bean
	public DisplayTab characterDataTab() {
//TODO 5: Create and return a DisplayTab with a CharacterDataDisplayer
//set as the displayer and the title set to be "Character Data ". 		
$
$
$
$

@//		DisplayTab characterDataTab = new DisplayTab();
@//		characterDataTab.setDisplayer(characterDataDisplayer());
@//		characterDataTab.setTitle("Character Data ");
@//		return characterDataTab;


		return null;//Change this.
%TODO 5:<br/> DisplayTab characterDataTab = new DisplayTab();<br/>characterDataTab.setDisplayer(characterDataDisplayer());<br/>characterDataTab.setTitle("Character Data ");<br/>return characterDataTab;<br/><br/>
	}

//TODO 6: Take a look at the other methods. They follow the same pattern.	
	
	@Bean
	public DataService dataService() {
		return new FileBasedDataService(null);
	}

	@Bean
	public PrimaryHeaderDisplayer primaryHeaderDisplayer() {
		return new PrimaryHeaderDisplayer();
	}

	@Bean
	public DisplayTab primaryHeaderTab() {
		DisplayTab primaryHeaderTab = new DisplayTab();
		primaryHeaderTab.setDisplayer(primaryHeaderDisplayer());
		primaryHeaderTab.setTitle("Primary Headers ");
		return primaryHeaderTab;
	}

	@Bean
	public ProfileDataTab profileDataTab() {
		ProfileDataTab profileDataTab = new ProfileDataTab();
		profileDataTab.setDisplayer(profileDataDisplayer());
		profileDataTab.setTitle("Profile Data");
		return profileDataTab;
	}

	@Bean
	public ProfileDataDisplayer profileDataDisplayer() {
		return new ProfileDataDisplayer();
	}
	
	@Bean
	public DisplayTab secondaryHeaderTab() {
		DisplayTab secondaryHeaderTab = new DisplayTab();
		secondaryHeaderTab.setDisplayer(secondaryHeaderDisplayer());
		secondaryHeaderTab.setTitle("Secondary Headers ");
		return secondaryHeaderTab;
	}

	@Bean
	public SecondaryHeaderDisplayer secondaryHeaderDisplayer() {
		return new SecondaryHeaderDisplayer();
	}
	
	@Bean
	public DisplayTab taxaTab() {
		DisplayTab taxaTab = new DisplayTab();
		taxaTab.setDisplayer(taxaDisplayer());
		taxaTab.setTitle("Taxa Sets ");
		return taxaTab;
	}

	@Bean
	public TaxaDisplayer taxaDisplayer() {
		return new TaxaDisplayer();
	}
	
	@Bean
	public WOD wod() {
		WOD wod = new WOD("World Ocean Database Browser");
		wod.addTab(primaryHeaderTab());
		wod.addTab(characterDataTab());
		wod.addTab(secondaryHeaderTab());
		wod.addTab(biologyHeaderTab());
		wod.addTab(taxaTab());
		wod.addTab(profileDataTab());
		wod.setController(wodController(wod));
		return wod;
	}

	//No @Bean annotation because it would create a circular reference.
	//This bean is created indirectly when you create wod.
	public WODController wodController(WOD wod) {
		WODController controller = new WODController(wod);
		controller.addObserver(primaryHeaderTab());
		controller.addObserver(characterDataTab());
		controller.addObserver(secondaryHeaderTab());
		controller.addObserver(biologyHeaderTab());
		controller.addObserver(taxaTab());
		controller.addObserver(profileDataTab());
		controller.setDataService(dataService());
		return controller;
	}

}
