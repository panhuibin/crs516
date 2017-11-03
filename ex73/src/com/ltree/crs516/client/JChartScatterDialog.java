package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jCharts.axisChart.ScatterPlotAxisChart;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.ScatterPlotDataSeries;
import org.jCharts.chartData.ScatterPlotDataSet;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.DataAxisProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PointChartProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.ScatterPlotProperties;
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
public final class JChartScatterDialog extends JDialog {
	
	private static final Logger logger = LoggerFactory.getLogger(JChartScatterDialog.class);

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
//	private static JFreeChart createChart(XYDataset dataset) {
//		logger.info("Creating Chart");
//		JFreeChart chart = ChartFactory.createScatterPlot("Title", "Depth",
//				null, dataset, PlotOrientation.VERTICAL, false, false, false);
//		chart.setBackgroundPaint(Color.white);
//
//		XYPlot plot = (XYPlot) chart.getPlot();
//		plot.setBackgroundPaint(Color.lightGray);
//		plot.setDomainGridlinePaint(Color.white);
//		plot.setRangeGridlinePaint(Color.white);
//		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
//		plot.setDomainCrosshairVisible(true);
//		plot.setRangeCrosshairVisible(true);
//
//		XYItemRenderer r = plot.getRenderer();
//		if (r instanceof XYLineAndShapeRenderer) {
//			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
//			renderer.setBaseShapesVisible(true);
//			renderer.setBaseShapesFilled(true);
//		}
//		return chart;
//	}

		
//	/**
//	 * Creates a dataset, consisting of two series of data.
//	 * 
//	 * @return The dataset.
//	 */
//	private static XYDataset createDataset(Double[] x, Double[] y) {
//		logger.info("Creating Dataset");
//		XYSeries s1 = new XYSeries(new Double(1));
//		if (x.length != y.length) {
//			logger.error("Error in createDataset of ScatterDialog. " +
//					"Could not create a dataset for the scatter plot -- missing data");
//			return null;
//		}
//		for (int i = 0; i < x.length; i++) {
//			if (y[i] != null) {
//				s1.add(x[i], y[i]);
//			}
//		}
//		XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(s1);
//		return dataset;
//	}

	/**
	 * Holds the scatter plots.
	 */
	private JPanel middlePanel = new JPanel();

	private JScrollPane scroller = new JScrollPane(middlePanel);

	/**
	 * The station to display
	 */
	private Station station = null;

	public JChartScatterDialog(Station s) {
		logger.debug("Creating ScatterDialog");
		station = s;
		setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		if (station.isProfilePresent()) {
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
			
			Variable[] variables = station.getVariables();
			
			//===============
			double[] xCoords = new double[tableData[0].length];
			for(int i=0; i<xCoords.length;i++){
				xCoords[i]=tableData[0][i];
			}
			for (int i = 1; i < tableData.length; i++) {
				double[] yCoords = new double[tableData[i].length];
				for(int j=0; j<yCoords.length;j++){
					try {
						yCoords[j]=tableData[i][j];
					} catch (Exception e) {
						yCoords[j]=Double.NaN;
						logger.warn("Some data is missing");
					}
				}
				
//				double[][] data = new double[2][];
//				data[0]=xCoords;
//				data[1]=yCoords;
				
//				DataAxisProperties xAxisProperties= (DataAxisProperties) axisProperties.getXAxisProperties();
//				DataAxisProperties yAxisProperties= (DataAxisProperties) axisProperties.getYAxisProperties();

//				DataSeries dataSeries = new DataSeries( null, xAxisTitle, yAxisTitle, title );

				
//				JFreeChart chart = createChart(createDataset(tableData[0],
//						tableData[i]));
				String codeUnit = variables[i - 1].getCodeUnit();
				//CodeUnit has entities that JFreeChart does not handle
				//like Degrees Celsius (&#0176;C)
				//See Depth-dependent_variables.txt
				//Need to elide the material in ()
				int entityStart = codeUnit.indexOf("(");
				if(entityStart!=-1){
					String front = codeUnit.substring(0,entityStart);
					String back = codeUnit.substring(entityStart);
					back = back.substring(back.indexOf(")")+1);
					codeUnit = front + back;
				}
				String title = (variables[i - 1].getCodeString() + "  Units: "
						//+ variables[i - 1].getCodeUnit());
						+ codeUnit);

				//DataSeries dataSeries = new DataSeries( null, null, null, title );

				Stroke[] strokes = new Stroke[]{ScatterPlotProperties.DEFAULT_LINE_STROKE};
				Shape[] shapes = new Shape[]{PointChartProperties.SHAPE_TRIANGLE};
				ScatterPlotProperties scatterPlotProperties = new ScatterPlotProperties(strokes, shapes);
				ScatterPlotDataSet iScatterPlotDataSet = new ScatterPlotDataSet(scatterPlotProperties);
				
				System.out.println("data length is "+xCoords.length);
				Point2D.Double[] data = new Point2D.Double[xCoords.length];
				for(int j=0; j<xCoords.length;j++){
					Point2D.Double point = new Point2D.Double(xCoords[j], yCoords[j]);
					data[j] = point;
				}
				//Paint[] outlinePaints= { Color.red };
				
				iScatterPlotDataSet.addDataPoints(data, Color.red, "Depth");
				String xAxisTitle = null;
				String yAxisTitle = null;
				String chartTitle = title;
				ScatterPlotDataSeries iScatterPlotDataSeries = new ScatterPlotDataSeries(
						iScatterPlotDataSet,
                        xAxisTitle, yAxisTitle, chartTitle);
				ChartProperties chartProperties = new ChartProperties();

				DataAxisProperties xAxisProperties= new DataAxisProperties();
				xAxisProperties.setNumItems(xCoords.length );

				DataAxisProperties yAxisProperties= new DataAxisProperties();
				yAxisProperties.setNumItems( yCoords.length );
				
				AxisProperties axisProperties  = new AxisProperties(xAxisProperties,yAxisProperties);
				//PointChartProperties axisProperties  = new PointChartProperties(shapes,true,outlinePaints);
				LegendProperties legendProperties = new LegendProperties();
				
				int pixelWidth = 500;
				int pixelHeight = 300;
				
				ScatterPlotAxisChart chart = new ScatterPlotAxisChart(
						iScatterPlotDataSeries,
                        chartProperties,
                        axisProperties,
                        legendProperties,
                        pixelWidth,
                        pixelHeight);
				
				
				JPanel plotPanel = new WODJChartpanel(chart);
				plotPanel.setPreferredSize(new Dimension(500,320));
//				System.out.println("Chart is "+chart);
//				System.out.println("Graphiscs: "+plotPanel.getGraphics());
//				chart.setGraphics2D((Graphics2D) plotPanel.getGraphics());
//				try {
//					chart.render();
//				} catch (ChartDataException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (PropertyException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				middlePanel.add(plotPanel);
			}
		}
	}

	private class WODJChartpanel extends JPanel{

		private static final long serialVersionUID = 1L;
		ScatterPlotAxisChart chart;
		
		WODJChartpanel(ScatterPlotAxisChart chart){
			this.chart = chart;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			chart.setGraphics2D((Graphics2D) g);
			try {
				chart.render();
			} catch (ChartDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
