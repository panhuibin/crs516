package com.ltree.crs516.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.Rank;

 @SuppressWarnings("serial")
public final class CardsChart extends JPanel implements Observer {

	private JPanel centerPanel = new JPanel();
	private JLabel[][] tiles = new JLabel[4][13];

	public CardsChart() {
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

	public CardsChart(CardDeck deck) {
		this();
		
		int[] ranksDealt = deck.getRanksDealt();
		updateTiles(ranksDealt);
	}

	@Override
	public void update(Observable o, Object arg) {
		int[] ranksDealt = (int[]) arg;
		updateTiles(ranksDealt);
	}
	
	private void updateTiles(int[] ranksDealt) {
		for (int j = 0; j < ranksDealt.length; j++) {
			int numberOfcardsDealt = ranksDealt[j];
			for (int i = 0; i < 4 - numberOfcardsDealt; i++) {
				tiles[i][j].setText("");
			}
			for (int i = 4 - numberOfcardsDealt; i < 4; i++) {
				tiles[i][j].setText(Rank.convertIntToRank(j + 1).getName());
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CardsChart chart = new CardsChart();
		frame.add(chart);
		frame.pack();
		frame.setVisible(true);
		chart.update(null, new int[] { 1, 2, 3, 4, 3, 2, 1, 2, 3, 4, 3, 2, 1 });
	}

}
