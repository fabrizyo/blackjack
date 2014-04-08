package com.fabriziolovato.blackjack;

public class HandControllerManualTest {

	public static void main(String[] args) {
		HandController handController = new HandController(new GameView(), false);
		handController.playNewHand(new Hand());
	}
}
