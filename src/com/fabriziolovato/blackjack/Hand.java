package com.fabriziolovato.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It represents and contains information of a single Hand of the game.
 * @author fabrizio
 *
 */
public class Hand {

	private final Card[] deck = new Card[52];
	private int indexCard = 0;
	private List<Card> playerCards = new ArrayList<Card>();
	private List<Card> dealerCards = new ArrayList<Card>();
	private HandResult handResult;

	public Hand() {
		initDeck();
		shuffleDeck();
	}

	/**
	 * Shuffle the deck based on <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle" />
	 */
	private void shuffleDeck() {
		Random rnd = new Random();
		for (int i = deck.length - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			Card a = deck[j];
			deck[j] = deck[i];
			deck[i] = a;
		}
	}

	/**
	 * Initialize the cards of deck, sorted.
	 */
	private void initDeck() {
		// init sorted deck
		int index = 0;
		for (CardType type : CardType.values()) {
			for (int value = 1; value <= 13; value++) {
				deck[index++] = new Card(type, value);
			}
		}
	}

	public Card addPlayerCard() {
		Card res = deck[indexCard++];
		playerCards.add(res);
		return res;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public List<Card> getDealerCards() {
		return dealerCards;
	}

	public Card addDealerCard() {
		Card res = deck[indexCard++];
		dealerCards.add(res);
		return res;
	}

	public HandResult getHandResult() {
		return handResult;
	}

	public void setHandResult(HandResult handResult) {
		this.handResult = handResult;
	}
	
	/**
	 * For testing purpose
	 * @param deck
	 */
	protected void setDeck(Card[]deck)  {
		System.arraycopy(deck, 0, this.deck, 0, deck.length);
	}

}
