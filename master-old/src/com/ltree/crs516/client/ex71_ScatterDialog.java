package com.ltree.crs516.client;

import java.awt.BorderLayout;
$import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

$import org.jfree.chart.ChartFactory;
$import org.jfree.chart.ChartPanel;
$import org.jfree.chart.JFreeChart;
$import org.jfree.chart.plot.PlotOrientation;
$import org.jfree.chart.plot.XYPlot;
$import org.jfree.chart.renderer.xy.XYItemRenderer;
$import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
$import org.jfree.data.xy.XYDataset;
$import org.jfree.data.xy.XYSeries;
$import org.jfree.data.xy.XYSeriesCollection;
$import org.jfree.ui.RectangleInsets;

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

$	/**
$	 * Creates a chart.
$	 * 
$	 * @param dataset
$	 *            a dataset.
$	 * 
$	 * @return A chart.
$	 */
$	
$//TODO 1: Note that this method is JFreeChart specific. We will have to move
$//this logic out of the class.
$
$	
$	private static JFreeChart createChart(XYDataset dataset) {
$		logger.info("Creating Chart");
$		JFreeChart chart = ChartFactory.createScatterPlot("Title", "Depth",
$				null, dataset, PlotOrientation.VERTICAL, false, false, false);
$		chart.setBackgroundPaint(Color.white);
$
$		XYPlot plot = (XYPlot) chart.getPlot();
$		plot.setBackgroundPaint(Color.lightGray);
$		plot.setDomainGridlinePaint(Color.white);
$		plot.setRangeGridlinePaint(Color.white);
$		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
$		plot.setDomainCrosshairVisible(true);
$		plot.setRangeCrosshairVisible(true);
$
$		XYItemRenderer r = plot.getRenderer();
$		if (r instanceof XYLineAndShapeRenderer) {
$			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
$			renderer.setBaseShapesVisible(true);
$			renderer.setBaseShapesFilled(true);
$		}
$		return chart;
$	}
$
$	/**
$	 * Creates a dataset, consisting of two series of data.
$	 * 
$	 * @return The dataset.
$	 */
$	
$//TODO 2: Note that this method is a helper method for the JFreeChart specific code. 
$//We will have to move this logic out of the class.
$	
$	private static XYDataset createDataset(Double[] x, Double[] y) {
$		logger.info("Creating Dataset");
$		XYSeries s1 = new XYSeries(new Double(1));
$		if (x.length != y.length) {
$			logger.error("Error in createDataset of ScatterDialog. " +
$					"Could not create a dataset for the scatter plot -- missing data");
$			return null;
$		}
$		for (int i = 0; i < x.length; i++) {
$			if (y[i] != null) {
$				s1.add(x[i], y[i]);
$			}
$		}
$		XYSeriesCollection dataset = new XYSeriesCollection();
$		dataset.addSeries(s1);
$		return dataset;
$	}

	/**
	 * Holds the scatter plots.
	 */
	private JPanel middlePanel = new JPanel();

	private JScrollPane scroller = new JScrollPane(middlePanel);

	/**
	 * The station to display
	 */
	private Station station = null;
@	private ChartProvider chartProvider = new JChartsAdapter();
@//	private ChartProvider chartProvider = new JFreeChartAdapter();
	
$//TODO 3: This constructor has some JFreeChart specific code. 
$//and some WOD Browser code We will have to make calls to the 
$//adapter to get the charting software to provide what we need

	public ScatterDialog(Station s) {
		logger.debug("Creating ScatterDialog");
		station = s;
		setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		if (station.isProfilePresent()) {
			//There is profile data so extract is to get ready to plot.
			Double[][] tableData = extractTableData();
			//zeroth row is depths
			//row i is data for variable i at the various depths
			
			Variable[] variables = station.getVariables();
			
			for (int i = 1; i < tableData.length; i++) {
				String codeUnit = getCodeUnitsForVariable(variables, i);

				//TODO 3a: Note: Code between the markers will be replaced by calls to the adapter
				
				//{{Marker 1
				//The x-coordinates are in tableData[0] while the y-coordinates are in tableData[i].
$				JFreeChart chart 
$						= createChart(createDataset(tableData[0],tableData[i]));
$				chart.setTitle(variables[i - 1].getCodeString() + "  Units: " + codeUnit);
$				JPanel plotPanel = new ChartPanel(chart);
$				plotPanel.setPreferredSize(new Dimension(500,300));
$
$
$
$

@				String title = variables[i - 1].getCodeString() + "  Units: " + codeUnit;
@				chartProvider.setTitle(title);
@				chartProvider.setSize(new Dimension(500,300));
@				chartProvider.setData(tableData[0],tableData[i]);
@				JPanel plotPanel = chartProvider.getChartPanel();

%TODO 3a: <br/>	String title = variables[i - 1].getCodeString() + "  Units: " + codeUnit;<br/>chartProvider.setTitle(title);<br/>chartProvider.setSize(new Dimension(500,300));<br/>chartProvider.setData(tableData[0],tableData[i]);<br/>JPanel plotPanel = chartProvider.getChartPanel();


				//}} end Marker 1
				
				middlePanel.add(plotPanel);
			}
		}
	}

$//TODO 4: Note: the rest of the code has nothing to do with JFreeChart
	
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
