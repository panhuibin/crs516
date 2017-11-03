package com.ltree.crs516;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
		
		private PlayingCard card;

		public void setCard(PlayingCard card) {
			if (this.card != null) {
				remove(this.card);
			}
			this.card = card;
			add(card, BorderLayout.CENTER);
			card.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (isActive()) {
						PlayingCard card = CardPanel.this.card;
						if(hand.getDeck().isEmpty()){
							JOptionPane.showMessageDialog(TehiPanel.this, "No cards left. You have to stand!");
							return;
						}
						PlayingCard newCard = hand.getDeck().deal();
						hand.remove(card);
						hand.add(newCard);
						remove(card);
						setCard(newCard);
						setActive(false);
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								observable.tellObservers();
							}
						});
					} else {
						System.out.println("Not active");
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
			add(card, BorderLayout.CENTER);
			JButton swapButton = new JButton("Swap");
			swapButton.addActionListener(new ActionListener() {
				PlayingCard card = CardPanel.this.card;

				@Override
				public void actionPerformed(ActionEvent e) {
					PlayingCard newCard = hand.getDeck().deal();
					hand.remove(card);
					hand.add(newCard);
					setCard(newCard);
				}
			});
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
