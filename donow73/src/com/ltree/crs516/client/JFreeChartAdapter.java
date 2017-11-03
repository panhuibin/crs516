package com.ltree.crs516.client;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JFreeChartAdapter implements ChartingProvider {

	private static final Logger logger = LoggerFactory.getLogger(JFreeChartAdapter.class);
	
	private String title;
	private Dimension size;
	private Double[] xCoordinates;
	private Double[] yCoordinates;
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setSize(Dimension dimension) {
		this.size = dimension;

	}

	@Override
	public void setData(Double[] xCoords, Double[] yCoords) {
		xCoordinates = xCoords;
		yCoordinates=yCoords;
	}

	@Override
	public JPanel getChartPanel() {
		JFreeChart chart = createChart(createDataset(xCoordinates,	yCoordinates));
		chart.setTitle(title);
		JPanel plotPanel = new ChartPanel(chart);
		plotPanel.setPreferredSize(size);
		return plotPanel;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private JFreeChart createChart(XYDataset dataset) {
		logger.info("Creating Chart");
		JFreeChart chart = ChartFactory.createScatterPlot("Title", "Depth",
				null, dataset, PlotOrientation.VERTICAL, false, false, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		return chart;
	}
	
	/**
	 * Creates a dataset, consisting of two series of data.
	 * 
	 * @return The dataset.
	 */
	private XYDataset createDataset(Double[] x, Double[] y) {
		logger.info("Creating Dataset");
		XYSeries s1 = new XYSeries(new Double(1));
		if (x.length != y.length) {
			logger.error("Error in createDataset of ScatterDialog. " +
					"Could not create a dataset for the scatter plot -- missing data");
			return null;
		}
		for (int i = 0; i < x.length; i++) {
			if (y[i] != null) {
				s1.add(x[i], y[i]);
			}
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(s1);
		return dataset;
	}
	
}
