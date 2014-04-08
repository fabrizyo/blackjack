package com.fabriziolovato.blackjack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameManagerTest {

	@Test
	public void testWinLost() {
		GameViewMockAskBet view = new GameViewMockAskBet(new int[] { 1, 5 });
		GameManager manager = new GameManager(100, 1, view, new HandControllerMock(view, HandResult.PlayerWin, HandResult.PlayerLost));
		manager.play();
		assertEquals(100 + 1 - 5, manager.getPlayerChips());

	}
	
	@Test
	public void testPush() {
		GameViewMockAskBet view = new GameViewMockAskBet(new int[] { 10 });
		GameManager manager = new GameManager(77, 1, view, new HandControllerMock(view, HandResult.Push));
		manager.play();
		assertEquals(77, manager.getPlayerChips());

	}
	
	@Test
	public void testLostEverything() {
		GameViewMockAskBet view = new GameViewMockAskBet(new int[] { 70 });
		GameManager manager = new GameManager(70, 1, view, new HandControllerMock(view, HandResult.PlayerLost, HandResult.PlayerLost));
		manager.play();
		assertEquals(70-70, manager.getPlayerChips());

	}

	@Test
	public void testWinBlackjack() {
		GameViewMockAskBet view = new GameViewMockAskBet(new int[] { 12, 5 });
		GameManager manager = new GameManager(80, 1, view, new HandControllerMock(view, HandResult.PlayerWinBlackjack,
				HandResult.PlayerLost));
		manager.play();
		assertEquals(80 + 12 * 3 / 2 - 5, manager.getPlayerChips());

	}
	
	@Test
	public void testNotEnoughChips() {
		GameViewMockAskBet view = new GameViewMockAskBet(new int[] { 60, 10 });
		GameManager manager = new GameManager(90, 50, view, new HandControllerMock(view, HandResult.PlayerLost,
				HandResult.PlayerLost));
		manager.play();
		assertEquals(90 - 60, manager.getPlayerChips());
		assertEquals(view.getIndexBet(), 1); //only one bet
	}

}
