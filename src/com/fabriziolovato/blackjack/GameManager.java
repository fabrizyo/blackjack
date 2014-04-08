package com.fabriziolovato.blackjack;

/**
 * It is the main class of the game. It manages the bets for the hands and represents an entire game of player. It is part of Controller of
 * MVC paradigm. The hands are managed by the {@link HandController}.
 * 
 * @author fabrizio
 * 
 */
public class GameManager {

	private final GameView view;
	private int playerChips;
	private final int minimumBet;
	private final HandController handController;

	public GameManager(int playerChips, int minimumBet, GameView view, HandController handController) {
		this.playerChips = playerChips;
		this.minimumBet = minimumBet;
		this.handController = handController;
		this.view = view;
	}

	/**
	 * Play an entire game with bets and related hands
	 */
	public void play() {
		view.printWelcome();
		while (true) {
			view.printPlayerChips(playerChips);
			if (playerChips < minimumBet) {
				view.printPlayerNotEnoughChips();
				break;
			}

			boolean newHand = view.askNewHand();
			if (!newHand) {
				break;
			}

			int chipsBet = view.askChipsBet(minimumBet, playerChips);
			Hand hand = new Hand();
			handController.playNewHand(hand);
			manageHandResult(chipsBet, hand.getHandResult());
			view.printBreakLine();
		}
		view.printGameOver();
	}

	/**
	 * Manages the result of a hand
	 * 
	 * @param chipsBet
	 * @param handResult
	 */
	private void manageHandResult(int chipsBet, HandResult handResult) {
		if (handResult.isWin()) {
			int chipsWin = 0;
			if (handResult.equals(HandResult.PlayerWinBlackjack)) {
				// Pay 3:2
				chipsWin = chipsBet * 3 / 2; // no 0.5 chips
			} else {
				// Pay 1:1
				chipsWin = chipsBet;
			}
			playerChips += chipsWin;
			view.printPlayerWinChips(chipsWin);
		} else if (handResult.isLost()) {
			playerChips -= chipsBet;
			view.printPlayerChipsLost(chipsBet);
		} else {
			// tied score
			view.printPushChips();
		}
	}

	/**
	 * For testing purpose
	 * 
	 * @return
	 */
	protected int getPlayerChips() {
		return playerChips;
	}

}
