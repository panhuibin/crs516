package com.ltree.crs516;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

 @SuppressWarnings("serial")
public final class MainFrame extends JFrame implements Observer {


	public static void main(String[] args) {
		new MainFrame();
	}

	private CardDeck deck = new CardDeck();
	private TehiPanel playerPanel;
	private TehiHand playerHand;
	private TehiPanel systemPanel;
	private TehiHand systemHand;
	private JButton dealButton = new JButton("Deal");
	private JButton standButton = new JButton("Stand");
	private JButton newGameButton = new JButton("New Game");
	private JEditorPane gamePane;
	private JPanel centerPanel;
	private int cumulativePlayerScore;
	private CardsViewer historyViewer;
	private int cumulativeSytemScore;

	private final String rules = "<center><h2>Welcome to Tehi!</h2></center>"
			+ "Essential Rules:<br/>"
			+ "Hands of five playing cards are dealt to both you and the system. "
			+ "Hand values are determined by multiplying twice the number of face cards "
			+ "by the total of rank values of the non-face cards. You stand on "
			+ "the hand as dealt or draw (swap) one card by clicking on it. In our "
			+ "deck, all the face cards have two eyes.<p/>"
			+ "The viewer below displays the ranks that have been dealt. This allows you "
			+ "to figure out what is left on the deck and what your opponent has.";

	private String swapInstruction(){
		if(deck.isEmpty()){
			return "</p>"
					+ "The deck is empty so you have to click the 'Stand' button to stand on the hand you have.";
		}
		return "</p>"
			+ "Click on a card to swap or click the 'Stand' button to stand on the hand you have.";
	}
	
	public MainFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				historyViewer = new CardsViewer();
				deck.addObserver(historyViewer);
				centerPanel = new JPanel();
				centerPanel.setLayout(new GridLayout(2, 1, 5, 5));
				deck.shuffle();
				playerHand = new TehiHand(deck);
				playerPanel = new TehiPanel(playerHand);
				playerPanel.setHand(playerHand);
				playerPanel.addObserver(MainFrame.this);
				playerPanel.setActive(true);
				centerPanel.add(playerPanel);
				systemHand = new TehiHand(deck);
				systemPanel = new TehiPanel(systemHand);
				systemPanel.setHand(systemHand);
				centerPanel.add(systemPanel);
				add(centerPanel, BorderLayout.CENTER);
				JPanel leftPanel = createLeftPanel();
				add(leftPanel, BorderLayout.WEST);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pack();
				setLocation(100, 100);
				setResizable(false);
				setVisible(true);
				systemPanel.setVisible(false);
			}
		});
	}

	private JPanel createLeftPanel() {

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(250, 300));
		leftPanel.setLayout(new BorderLayout());
		gamePane = new JEditorPane("text/html", rules);
		JPanel textAndHistoryPanel = new JPanel();
		textAndHistoryPanel.setLayout(new BorderLayout());
		textAndHistoryPanel.add(new JScrollPane(gamePane), BorderLayout.CENTER);
		textAndHistoryPanel.add(historyViewer, BorderLayout.SOUTH);
		leftPanel.add(textAndHistoryPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1, 5, 5));
		buttonPanel.add(dealButton);
		
		dealButton.addActionListener(event -> {
			if (deck.size() < 10) {
				// not enough cards
				endGame();
				return;
			}
			deck.shuffle();
			playerHand = new TehiHand(deck);
			playerPanel.setHand(playerHand);
			systemHand = new TehiHand(deck);
			systemPanel.setHand(systemHand);
			playerPanel.setActive(true);
			systemPanel.setActive(true);
			dealButton.setEnabled(false);
			standButton.setEnabled(true);
			systemPanel.setVisible(false);
			gamePane.setText(swapInstruction());
		});
		dealButton.setEnabled(false);
		standButton.addActionListener(event -> {
			systemPanel.setVisible(true);
			dealButton.setEnabled(true);
			standButton.setEnabled(false);
			int playerScore = playerHand.getScore();
			int systemScore = systemHand.getScore();
			cumulativePlayerScore += playerScore;
			cumulativeSytemScore += systemScore;
			playerPanel.setActive(false);
			systemPanel.setActive(false);
			gamePane.setText(scoreText());
		});
		buttonPanel.add(standButton);
		newGameButton.addActionListener(event -> {
			deck = new CardDeck();
			deck.addObserver(historyViewer);
			deck.shuffle();
			playerHand = new TehiHand(deck);
			playerPanel.setHand(playerHand);
			playerPanel.addObserver(MainFrame.this);
			playerPanel.setActive(true);
			systemHand = new TehiHand(deck);
			systemPanel.setHand(systemHand);
			systemPanel.setActive(false);
			systemPanel.setVisible(false);
			cumulativePlayerScore=0;
			cumulativeSytemScore=0;
			gamePane.setText(swapInstruction());

		});

		buttonPanel.add(newGameButton);
		leftPanel.add(buttonPanel, BorderLayout.SOUTH);
		return leftPanel;
	}

	private String scoreText() {
		int playerScore = playerHand.getScore();
		int systemScore = systemHand.getScore();
		StringBuilder builder = new StringBuilder("<html><ul>");
		builder.append("<li>Your score: " + playerScore + "</li>");
		builder.append("<li>System's score: " + systemScore + "</li></ul>");
		if (playerScore > systemScore) {
			builder.append("<center><h2>You won this round!</h2></center>");
		} else {
			builder.append("<p>The system won this round!</p>");
		}
		builder.append("<ul><li>Your cumulative score: "
				+ cumulativePlayerScore + "</li>");
		builder.append("<li>System's cumulative score: " + cumulativeSytemScore
				+ "</li></ul>");
		if(deck.size()>9){
			builder.append("<p>Click the 'Deal' button for a new round!");
			}
			else{
				builder.append(endGameText());
				dealButton.setEnabled(false);			}
		return builder.toString();
	}

	@Override
	public void update(Observable o, Object arg) {
		systemPanel.setActive(false);
		systemPanel.setVisible(true);
		dealButton.setEnabled(true);
		standButton.setEnabled(false);
		int playerScore = playerHand.getScore();
		int systemScore = systemHand.getScore();
		this.cumulativePlayerScore += playerScore;
		this.cumulativeSytemScore += systemScore;
		gamePane.setText(scoreText());
	}

	private void endGame() {
		gamePane.setText(endGameText());

	}

	private String endGameText() {
		StringBuilder builder = new StringBuilder("<html><ul>");
		builder.append("<li>Your cumulative score: " + cumulativePlayerScore + "</li>");
		builder.append("<li>System's cumulative score: " + cumulativeSytemScore	+ "</li></ul>");
		if (cumulativePlayerScore > cumulativeSytemScore) {
			builder.append("<center><h2>You won the game!</h2></center>");
		} else {
			builder.append("<center><h2>The system won the game!</h2></center>");
		}
		return builder.toString();
	}
	
}
