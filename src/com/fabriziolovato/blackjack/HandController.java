package com.fabriziolovato.blackjack;

import java.util.List;

/**
 * It manages a single Hand of a game, asking for new cards and calculating the results. The dealer must hit until a 17 is reached. Soft 17
 * and Hard 17 are managed depending on H17 option.
 * 
 * 
 * @author fabrizio
 * 
 */
public class HandController {

	private final boolean H17;
	private final GameView view;

	public HandController(GameView view, boolean H17) {
		this.H17 = H17;
		this.view = view;
	}

	/**
	 * Main method called from {@link GameManager}
	 * 
	 * @param hand
	 */
	public void playNewHand(Hand hand) {
		view.printNewHand();
		startDealerCards(hand);

		makePlayerTurn(hand);

		HandResult res;
		if (exceed21(hand.getPlayerCards())) {
			res = HandResult.PlayerLost;
			view.printPlayerExceeded21();
			view.sleep();
		} else {
			makeDealerTurn(hand);
			view.sleep();
			res = printAndGetHandResult(hand);
		}

		view.printEndOfHand();
		hand.setHandResult(res);
	}

	private void startDealerCards(Hand hand) {
		// get first two player cards
		hand.addDealerCard();
		hand.addDealerCard();
		view.printFirstDealerCard(hand);
	}

	private HandResult printAndGetHandResult(Hand hand) {
		int bestScorePlayer = getBestScore(hand.getPlayerCards());
		int bestScoreDealer = getBestScore(hand.getDealerCards());

		printScores(hand, bestScorePlayer, bestScoreDealer);
		HandResult res = getHandResult(hand, bestScorePlayer, bestScoreDealer);
		printPlayerResult(res);
		return res;
	}

	private void printPlayerResult(HandResult res) {
		if (res.isWin()) {
			view.printPlayerWin();
		} else if (res.isLost()) {
			view.printPlayerLost();
		} else {
			// tie
			view.printPush();
		}

	}

	/**
	 * Calculate the results based on Blackjack rules
	 * 
	 * @param hand
	 * @param bestScorePlayer
	 * @param bestScoreDealer
	 * @return
	 */
	private static HandResult getHandResult(Hand hand, int bestScorePlayer, int bestScoreDealer) {
		if (bestScoreDealer > 21 || bestScorePlayer > bestScoreDealer) {
			if (bestScorePlayer == 21 && hand.getPlayerCards().size() == 2) {
				return HandResult.PlayerWinBlackjack;
			}
			return HandResult.PlayerWin;
		}

		if (bestScoreDealer > bestScorePlayer) {
			return HandResult.PlayerLost;
		}

		// same score
		if (bestScorePlayer == 21) {
			if (hand.getPlayerCards().size() == 2 && hand.getDealerCards().size() != 2) {
				return HandResult.PlayerWinBlackjack;
			}
			if (hand.getDealerCards().size() == 2 && hand.getPlayerCards().size() != 2) {
				return HandResult.PlayerLost;
			}
		}

		return HandResult.Push;
	}

	private void printScores(Hand hand, int bestScorePlayer, int bestScoreDealer) {
		view.printBreakLine();
		if (bestScoreDealer > 21) {
			view.printDealerExceeded21();
		} else {
			view.printDealerScore(bestScoreDealer);
			if (bestScoreDealer == 21 && (hand.getDealerCards().size() == 2)) {
				view.printDealerBlackjack();
			}
		}

		view.printPlayerScore(bestScorePlayer);
		if (bestScorePlayer == 21 && (hand.getPlayerCards().size() == 2)) {
			view.printPlayerBlackjack();
		}
	}

	private void makeDealerTurn(Hand hand) {
		view.printDealerTurn();
		view.printDealerCards(hand);

		while (true) {
			if (!mustDealerAddNewCard(hand)) {
				break;
			}
			view.sleep();
			hand.addDealerCard();
			view.printDealerLastCard(hand);
		}
		view.printBreakLine();
	}

	private void makePlayerTurn(Hand hand) {
		// get first two player cards
		view.printPlayerTurn();
		hand.addPlayerCard();
		hand.addPlayerCard();
		view.printPlayerCards(hand);

		while (true) {
			if (!canPlayerAddNewCard(hand)) {
				view.printBreakLine();
				break;
			}

			boolean answer = view.askNewCard();
			if (!answer) {
				break;
			}
			hand.addPlayerCard();
			view.printPlayerCards(hand);
		}
	}

	private static int getBestScore(List<Card> cards) {
		int res = getMinValue(cards);
		if (res >= 21) {
			return res;
		}

		int aces = getAces(cards);
		while (aces-- > 0) {
			int newRes = res + 10;
			if (newRes > 21) {
				return res;
			}
			res = newRes;
		}
		return res;
	}

	/**
	 * It return <code>true</code> if the dealer must add a new card otherwise <code>false</code>. The dealer must hit until a 17 is
	 * reached. Soft 17 and Hard 17 are managed depending on H17 option.
	 * 
	 * See <a href="http://en.wikipedia.org/wiki/Blackjack#Rule_variations_and_their_consequences_for_the_house_edge" />
	 * 
	 * @param hand
	 * @return
	 */
	private boolean mustDealerAddNewCard(Hand hand) {
		int bestScore = getBestScore(hand.getDealerCards());
		if (bestScore < 17) {
			return true;
		}
		if (bestScore > 17) {
			return false;
		}

		// best score=17
		if (!H17) {
			// S17
			return false;
		}

		// H17: hit also if it's a Soft 17 --> if there is at least an Ace
		return getAces(hand.getDealerCards()) > 0;
	}

	private static boolean exceed21(List<Card> cards) {
		return getBestScore(cards) > 21;
	}

	private static boolean canPlayerAddNewCard(Hand hand) {
		return getMinValue(hand.getPlayerCards()) < 21 && !can21(hand.getPlayerCards());
	}

	private static boolean can21(List<Card> cards) {
		return getBestScore(cards) == 21;
	}

	private static int getAces(List<Card> cards) {
		int res = 0;
		for (Card card : cards) {
			if (card.getBlackjackValue() == 1) {
				res++;
			}
		}
		return res;
	}

	private static int getMinValue(List<Card> cards) {
		int res = 0;
		for (Card card : cards) {
			res += card.getBlackjackValue();
		}
		return res;
	}
}
