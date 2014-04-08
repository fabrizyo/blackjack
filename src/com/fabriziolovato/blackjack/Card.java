package com.fabriziolovato.blackjack;

/**
 * It represents a Card with type and value
 * @author fabrizio
 *
 */
public class Card {

	private CardType type;
	private int value;

	public Card(CardType type, int value) {
		this.type = type;
		this.value = value;
	}

	public CardType getType() {
		return type;
	}

	/**
	 * Return the value for Blackjack game
	 * @return
	 */
	public int getBlackjackValue() {
		return Math.min(value, 10);
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		if (value == 1) {
			res.append("A");
		} else if (value <= 10) {
			res.append(value);
		} else {
			switch (value) {
			case 11:
				res.append("J");
				break;

			case 12:
				res.append("Q");
				break;

			case 13:
				res.append("K");
				break;
			}
		}
		res.append(type.toString().charAt(0));
		return res.toString();
	}
}