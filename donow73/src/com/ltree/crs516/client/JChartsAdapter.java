package com.ltree.crs516.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

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

public final class JChartsAdapter implements ChartingProvider {

	private final static Logger logger = LoggerFactory.getLogger(JChartsAdapter.class);
	
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
		ScatterPlotAxisChart chart = createChart();
		JPanel plotPanel = new WODJChartpanel(chart);
		plotPanel.setPreferredSize(size);
		return plotPanel;
	}

	private ScatterPlotAxisChart createChart() {
		logger.debug("Creating Chart");
		Stroke[] strokes = new Stroke[]{ScatterPlotProperties.DEFAULT_LINE_STROKE};
		Shape[] shapes = new Shape[]{PointChartProperties.SHAPE_TRIANGLE};
		ScatterPlotProperties scatterPlotProperties = new ScatterPlotProperties(strokes, shapes);
		ScatterPlotDataSet scatterPlotDataSet = new ScatterPlotDataSet(scatterPlotProperties);

		Point2D.Double[] data = new Point2D.Double[xCoordinates.length];
		for(int j=0; j<xCoordinates.length;j++){
			Point2D.Double point = new Point2D.Double(xCoordinates[j], yCoordinates[j]);
			data[j] = point;
		}

		scatterPlotDataSet.addDataPoints(data, Color.red, "Depth");
		String xAxisTitle = null;
		String yAxisTitle = null;
		String chartTitle = title;
		ScatterPlotDataSeries scatterPlotDataSeries = new ScatterPlotDataSeries(
				scatterPlotDataSet,
                xAxisTitle, yAxisTitle, chartTitle);
		ChartProperties chartProperties = new ChartProperties();

		DataAxisProperties xAxisProperties= new DataAxisProperties();
		xAxisProperties.setNumItems(xCoordinates.length );

		DataAxisProperties yAxisProperties= new DataAxisProperties();
		yAxisProperties.setNumItems( yCoordinates.length );
		
		AxisProperties axisProperties  = new AxisProperties(xAxisProperties,yAxisProperties);
		LegendProperties legendProperties = new LegendProperties();
		
		int pixelWidth = 500;
		int pixelHeight = 300;
		
		ScatterPlotAxisChart chart = new ScatterPlotAxisChart(
				scatterPlotDataSeries,
                chartProperties,
                axisProperties,
                legendProperties,
                pixelWidth,
                pixelHeight);
		return chart;
	}

	@SuppressWarnings("serial")
	class WODJChartpanel extends JPanel{

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
