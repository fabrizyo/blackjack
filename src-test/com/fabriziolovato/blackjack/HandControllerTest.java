package com.fabriziolovato.blackjack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HandControllerTest {

	@Test
	public void testPlayNewHandPlayerWin() {
		Hand hand = playHand(new int[] { 10, 8, 2, 10, 8, }, 1, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerWinDealerExceed21() {
		Hand hand = playHand(new int[] { 10, 5, 2, 10, 8, 10 }, 1, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
		assertEquals(3, hand.getDealerCards().size());
	}

	@Test
	public void testPlayNewHandPlayerLost() {
		Hand hand = playHand(new int[] { 10, 10, 2, 10, 7, }, 1, false);
		assertEquals(HandResult.PlayerLost, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerLostExceed21() {
		Hand hand = playHand(new int[] { 10, 10, 2, 10, 10, }, 1, false);
		assertEquals(HandResult.PlayerLost, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerWinWithAce() {
		Hand hand = playHand(new int[] { 10, 8, 2, 7, 1, }, 1, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerWinWithDoubleAce() {
		Hand hand = playHand(new int[] { 10, 8, 5, 2, 1, 1, }, 2, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerLostWithDealerAce() {
		Hand hand = playHand(new int[] { 9, 1, 2, 7, 10, }, 1, false);
		assertEquals(HandResult.PlayerLost, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPush() {
		Hand hand = playHand(new int[] { 9, 10, 2, 7, 10, }, 1, false);
		assertEquals(HandResult.Push, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPush21() {
		Hand hand = playHand(new int[] { 6, 10, 2, 9, 10, 5 }, 1, false);
		assertEquals(HandResult.Push, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPushBlackjack() {
		Hand hand = playHand(new int[] { 1, 10, 1, 10 }, 1, false);
		assertEquals(HandResult.Push, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayerWinBlackjack() {
		Hand hand = playHand(new int[] { 10, 8, 1, 10, 8, }, 1, false);
		assertEquals(HandResult.PlayerWinBlackjack, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandPlayer21DealerBlackjack() {
		Hand hand = playHand(new int[] { 1, 10, 10, 6, 3, }, 1, false);
		assertEquals(HandResult.PlayerLost, hand.getHandResult());
	}

	@Test
	public void testPlayNewHandDealerStopForS17() {
		Hand hand = playHand(new int[] { 1, 6, 10, 6, 3, 5 }, 1, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
		assertEquals(2, hand.getDealerCards().size());
	}

	@Test
	public void testPlayNewHandDealerTakesCardsBecauseExceed21WithAces() {
		Hand hand = playHand(new int[] { 1, 4, 10, 6, 3, 1, 10, 10 }, 1, false);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
		assertEquals(5, hand.getDealerCards().size());
	}

	@Test
	public void testPlayNewHandHitASoft17() {
		Hand hand = playHand(new int[] { 8, 8, 10, 6, 4, 1, 2 }, 1, true);
		assertEquals(HandResult.PlayerWin, hand.getHandResult());
		assertEquals(4, hand.getDealerCards().size());
	}

	@Test
	public void testPlayNewHandDealerStopForS17OneCard() {
		Hand hand = playHand(new int[] { 1, 6, 10, 6, 4, 4 }, 1, true);
		assertEquals(HandResult.PlayerLost, hand.getHandResult());
		assertEquals(3, hand.getDealerCards().size());
	}

	private static Hand playHand(int[] cards, int asksPlayer, boolean H17) {
		Card[] deck = getCards(cards);
		GameView view = new GameViewMockAskCards(asksPlayer);
		HandController controller = new HandController(view, H17);
		Hand hand = new Hand();
		hand.setDeck(deck);
		controller.playNewHand(hand);
		return hand;
	}

	private static Card[] getCards(int... values) {
		Card[] res = new Card[values.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = new Card(CardType.Clubs, values[i]);
		}
		return res;
	}

}
