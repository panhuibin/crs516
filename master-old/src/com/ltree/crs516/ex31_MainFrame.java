package com.ltree.crs516;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

 @SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private CardPanel cardPanel;
	private CardDeck deck = new CardDeck();

	public MainFrame() {
		deck.shuffle();
		setLayout(new GridLayout(1, 2));
		add(createLeftPanel());
		cardPanel = new CardPanel(deck.deal());
		add(cardPanel);
		pack();
		setVisible(true);
	}

	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2, 1, 5, 5));
		CardsViewer pastCardsViewer = new PastCardsViewer();
		CardsViewer futureCardsViewer = new FutureCardsViewer();
//TODO 1: Register pastCardsViewer and futureCardsViewer with deck as observers of deck.
@		deck.addObserver(pastCardsViewer);
@		deck.addObserver(futureCardsViewer);
$
$
$
%TODO 1:<br/>&#160;&#160;deck.addObserver(pastCardsViewer);<br/>&#160;&#160;deck.addObserver(futureCardsViewer);<br/><br/>
		leftPanel.add(pastCardsViewer);
		leftPanel.add(futureCardsViewer);
		return leftPanel;
	}

	class CardPanel extends JPanel {
		PlayingCard card;

		public void setCard(PlayingCard card) {
			this.card = card;

			add(card, BorderLayout.CENTER);
			card.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (!deck.isEmpty()) {
						PlayingCard newCard = deck.deal();
						setCard(newCard);
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if (isActive()) {
						setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				}
			});
			validate();
		}

		public CardPanel(PlayingCard card) {
			setLayout(new BorderLayout());
			setCard(card);
		}
	}

	
}
