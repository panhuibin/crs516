package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.XYLine;
import com.googlecode.charts4j.XYLineChart;

public class Charts4JAdapter implements ChartingProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(Charts4JAdapter.class);
	
	private String title;
	private Dimension size;
	private double[] xCoordinates;
	private double[] yCoordinates;

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
		xCoordinates = new double[xCoords.length];
		for (int i = 0; i < xCoords.length; i++) {
			xCoordinates[i] = xCoords[i];
		}
		yCoordinates = new double[yCoords.length];
		for (int i = 0; i < yCoords.length; i++) {
			yCoordinates[i] = yCoords[i];
		}			
	}

	@Override
	public JPanel getChartPanel() {
		Data xData = DataUtil.scale(xCoordinates);
		Data yData = DataUtil.scale(yCoordinates);
		XYLine xyLine = Plots.newXYLine(xData, yData);
		XYLineChart chart = GCharts.newXYLineChart(xyLine);
		chart.setSize(size.width, size.height);
		AxisLabels xLabels = AxisLabelsFactory.newNumericAxisLabels(xCoordinates);
		AxisLabels yLabels = AxisLabelsFactory.newNumericAxisLabels(yCoordinates);
		chart.addXAxisLabels(xLabels);
		chart.addYAxisLabels(yLabels);
		chart.setTitle(title);
		
		String chartUrl = chart.toURLString();
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = null;
		try {
			label = new JLabel(new ImageIcon(ImageIO.read(new URL(chartUrl))));
	        panel.add(label, BorderLayout.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}

        return panel;
	}

}
