package com.fabriziolovato.blackjack;

public class GameViewMockAskBet extends GameView{

	
	private final int[] bets;
	private int indexBet;
	
	

	public GameViewMockAskBet(int[] bets) {
		this.bets = bets;
	}
	
	@Override
	public boolean askNewHand() {
		boolean res = this.indexBet<bets.length;
		System.out.println("New hand: " + res);
		return res;
	}



	@Override
	public int askChipsBet(int minimumBet, int playerChips) {
		return this.bets[indexBet++];
	}

	public int[] getBets() {
		return bets;
	}

	public int getIndexBet() {
		return indexBet;
	}
	
	@Override
	public void sleep() {
		
	}
	
}
