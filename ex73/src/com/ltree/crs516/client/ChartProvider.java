package com.ltree.crs516.client;

import java.awt.Dimension;

import javax.swing.JPanel;

public interface ChartProvider {
	void setTitle(String title);

	void setSize(Dimension dimension);

	void setData(Double[] xCoords, Double[] yCoords);

	JPanel getChartPanel();
}
