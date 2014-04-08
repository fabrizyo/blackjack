package com.fabriziolovato.blackjack;

public class GameViewMockAskCards extends GameView {

	private int askCards;

	public GameViewMockAskCards(int askCards) {
		this.askCards = askCards;
	}

	@Override
	public boolean askNewCard() {
		boolean res = askCards-- > 0;
		System.out.println("New card: " + res);
		return res;
	}

	@Override
	public void sleep() {

	}
}