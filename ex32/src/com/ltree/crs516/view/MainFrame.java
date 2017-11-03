package com.ltree.crs516.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.controller.TehiGame.StateName;

 @SuppressWarnings("serial")
public final class MainFrame extends JFrame implements Observer {

	private TehiPanel playerPanel;
	private TehiPanel systemPanel;
	private JButton dealButton = new JButton("Deal");
	private JButton standButton = new JButton("Stand");
	private JEditorPane gamePane;
	private CardsChart historyChart;
	private final TehiGame game;
	private JMenuItem saveItem;
	private JMenuItem loadItem;

	public MainFrame(final TehiGame game) {
		setTitle("Tehi");
		this.game = game;
		game.addObserver(this);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 5, 5));
		playerPanel = new TehiPanel(game.getPlayerHand());
		centerPanel.add(playerPanel);
		systemPanel = new TehiPanel(game.getSystemHand());
		centerPanel.add(systemPanel);
		add(centerPanel, BorderLayout.CENTER);
		JPanel leftPanel = createLeftPanel();
		add(leftPanel, BorderLayout.WEST);
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		loadItem = new JMenuItem("Load Saved Game");
		loadItem.addActionListener(	event -> {
			try {
				game.load();
				game.getDeck().notifyObservers(game.getDeck().getRanksDealt());
				historyChart.update(game.getDeck(), game.getDeck().getRanksDealt());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MainFrame.this, "There is no saved game");
			}
		});
		saveItem = new JMenuItem("Save Game");
		saveItem.addActionListener(event -> {
			if (game.getState().getStateName() == TehiGame.StateName.INIT) {
				JOptionPane.showMessageDialog(MainFrame.this,
						"No moves made yet");
				return;
			}
			game.save();
		});
		gameMenu.add(loadItem);
		gameMenu.add(saveItem);
		menuBar.add(gameMenu);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.changeState(StateName.INIT);
		pack();
		setLocation(100, 100);
		setResizable(false);
		setVisible(true);
	}

	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(250, 300));
		leftPanel.setLayout(new BorderLayout());
		gamePane = new JEditorPane("text/html", "");
		JPanel textAndHistoryPanel = new JPanel();
		textAndHistoryPanel.setLayout(new BorderLayout());
		textAndHistoryPanel.add(gamePane, BorderLayout.CENTER);
		historyChart = new CardsChart(game.getDeck());
		game.getDeck().addObserver(historyChart);
		textAndHistoryPanel.add(historyChart, BorderLayout.SOUTH);
		leftPanel.add(textAndHistoryPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1, 5, 5));
		buttonPanel.add(dealButton);
		dealButton.addActionListener(event -> game.changeState(StateName.DEAL));
		dealButton.setEnabled(false);
		standButton.addActionListener(event -> game.changeState(StateName.STAND));
		buttonPanel.add(standButton);
		leftPanel.add(buttonPanel, BorderLayout.SOUTH);
		return leftPanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		playerPanel.setHand(game.getPlayerHand());
		systemPanel.setHand(game.getSystemHand());

		playerPanel.setActive(game.isPlayerPanelActive());
		systemPanel.setActive(game.isSystemPanelActive());
		systemPanel.setVisible(game.isSystemPanelVisible());
		dealButton.setEnabled(game.isDealButtonEnabled());
		standButton.setEnabled(game.isStandButtonEnabled());
		dealButton.setText(game.getDealButtonText());
		gamePane.setText(game.getGamePaneText());
		game.getDeck().addObserver(historyChart);
	}

}
