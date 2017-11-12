package com.ltree.crs516;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

//TODO 1: Change the declaration so that this class implements Observer.
 @SuppressWarnings("serial")
public abstract class CardsViewer extends JPanel implements Observer {
	private JPanel centerPanel = new JPanel();
	protected JLabel[][] tiles = new JLabel[4][13];

	public CardsViewer() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(4, 13));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				tiles[i][j] = new JLabel("", JLabel.CENTER);
				tiles[i][j].setText(Rank.convertIntToRank(j + 1).getName());
				centerPanel.add(tiles[i][j]);
			}
		}
		add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Updates the GUI.
	 * @param data an int[] with ranks of cards.
	 */
	protected abstract void updateChart(int[] data);
}
