package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.Level;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Variable;

/**
 * A JDialog to hold scatter plots for the profile data currently being read.
 * 
 * @author crs516 development team
 * 
 */
 @SuppressWarnings("serial")
public final class ScatterDialog extends JDialog {
	
	private static Logger logger = LoggerFactory.getLogger(ScatterDialog.class);

	/**
	 * Holds the scatter plots.
	 */
	private JPanel middlePanel = new JPanel();

	private JScrollPane scroller = new JScrollPane(middlePanel);

	/**
	 * The station to display
	 */
	private Station station = null;
	
	/**
	 * The ChartProvider
	 */
	private ChartingProvider chartingProvider = new JFreeChartAdapter();
//	private ChartingProvider chartProvider = new JChartsAdapter();
	
	public ScatterDialog(Station s) {
		logger.debug("Creating ScatterDialog");
		station = s;
		setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		if (station.isProfilePresent()) {
			Double[][] tableData = extractTableData();
			Variable[] variables = station.getVariables();
			for (int i = 1; i < tableData.length; i++) {
				String codeUnit = getCodeUnitsForVariable(variables, i);
				String title = variables[i - 1].getCodeString() + "  Units: " + codeUnit;
				chartingProvider.setTitle(title);
//TODO 1: call methods of the ChartProvider interface on chartProdiver to 
//				-- set the size on chartingProvider to new Dimension(500,300)
//				-- set the data on chartingProvider to (tableData[0],tableData[i])


//TODO 2: call the getChartPanel() method of chartProvider to get a JPanel with the plot				
				JPanel plotPanel = null;
				middlePanel.add(plotPanel);
			}
		}
	}

	private String getCodeUnitsForVariable(Variable[] variables, int i) {
		String codeUnit = variables[i - 1].getCodeUnit();
		//CodeUnit has XML entities that Charting software does not handle
		//like Degrees Celsius (&#0176;C)
		//See Depth-dependent_variables.txt
		//Need to elide the material in ()
		if(codeUnit==null){codeUnit = "";}
		int entityStart = codeUnit.indexOf("(");
		if(entityStart!=-1){
			String front = codeUnit.substring(0,entityStart);
			String back = codeUnit.substring(entityStart);
			back = back.substring(back.indexOf(")")+1);
			codeUnit = front + back;
		}
		return codeUnit;
	}

	private Double[][] extractTableData() {
		Level[] profile = station.getProfile();
		int numOfLevels = profile.length;
		int numOfVars = station.getVariablesInProfile();
		Double[][] tableData = new Double[numOfVars + 1][numOfLevels];
		/*
		 * Depth of level i is item [0][i]
		 */
		for (int i = 0; i < profile.length; i++) {
			Level level = profile[i];
			tableData[0][i] = new Double(level.getDepthString());
			if (level.isDataPresent()) {
				Datum[] observations = level.getData();
				int obsCounter = 1;
				for (Datum obs : observations) {

					if (obs == null) {
						tableData[obsCounter][i] = null;
						continue;
					}
					tableData[obsCounter][i] = new Double(obs
							.getValueString());
					obsCounter++;
				}
			}
		}
		return tableData;
	}

}
