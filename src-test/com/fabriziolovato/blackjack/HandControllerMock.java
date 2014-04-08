package com.fabriziolovato.blackjack;

public class HandControllerMock extends HandController{

	private final HandResult[] handResult;
	private int index;

	public HandControllerMock(GameView view,  HandResult... handResult) {
		super(view, false);
		this.handResult = handResult;
	}

	@Override
	public void playNewHand(Hand hand) {
		hand.setHandResult(this.handResult[index++]);
	}
	
}
