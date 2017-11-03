package com.ltree.crs516.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ltree.crs516.controller.TehiGame.StateName;
import com.ltree.crs516.domain.PlayingCard;
import com.ltree.crs516.domain.TehiHand;

 @SuppressWarnings("serial")
public final class TehiPanel extends JPanel {

	private TehiHand hand;
	private CardPanel[] cardPanel = new CardPanel[5];
	private boolean active;
	private TehiObservable observable = new TehiObservable();

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setHand(TehiHand hand) {
		this.hand = hand;
		for (int i = 0; i < hand.size(); i++) {
			cardPanel[i].setCard(hand.get(i));
		}
		validate();
	}

	public TehiPanel(TehiHand hand) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5, 5, 5));
		for (int i = 0; i < cardPanel.length; i++) {
			cardPanel[i] = new CardPanel(hand.get(i));
			panel.add(cardPanel[i]);
		}
		add(panel, BorderLayout.CENTER);
	}

	class CardPanel extends JPanel {
		PlayingCard card;

		public void setCard(PlayingCard card) {
			if (this.card != null) {
				// remove(this.card);
			}
			// System.out.println("Setcard");
			this.card = card;
			repaint();

		}

		public CardPanel(PlayingCard card) {
			this.card = card;
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (isActive()) {
						PlayingCard card = CardPanel.this.card;
						if (hand.getDeck().isEmpty()) {
							JOptionPane.showMessageDialog(TehiPanel.this,
									"No cards left. You have to stand!");
							return;
						}
						PlayingCard newCard = hand.getDeck().deal();
						hand.remove(card);
						hand.add(newCard);
						setCard(newCard);
						hand.getGame().changeState(StateName.SWAP);
					} else {
						System.out.println(getClass()
								+ "CardPanel is Not active");
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
			setPreferredSize(new Dimension(200, 250));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(card.getImage(), 0, 0, null);
		}
	}

	class TehiObservable extends Observable {
		public void tellObservers() {
			setChanged();
			notifyObservers(null);
		}
	}

	public void addObserver(Observer obs) {
		observable.addObserver(obs);
	}
}
