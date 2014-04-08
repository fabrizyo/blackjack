package com.fabriziolovato.blackjack;

public class GameManagerManualTest {

	public static void main(String[] args) {
		GameView view = new GameView();
		GameManager g=new GameManager(100,1,view,new HandController(view, false));
		g.play();
	}
}
