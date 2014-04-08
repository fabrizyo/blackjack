package com.fabriziolovato.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * It is the view game in the a paradigm of MVC. It print the result and ask for bets and cards.
 * 
 * @author fabrizio
 * 
 */
public class GameView {

	private static final int SLEEP_TIME_MS = 2000;
	private static final String ERROR_INPUT_MESSAGE = "Error from input, please try again.";

	public void printDealerTurn() {
		System.out.println("It's dealer's turn:");
	}

	private static void printCard(Card card) {
		System.out.print(" " + card.toString());
	}

	public void printPlayerTurn() {
		System.out.println("It's your turn:");
	}

	public boolean askNewCard() {
		return askYN("Do you want another card? (y/n): ");
	}

	private static boolean askYN(String question) {
		while (true) {
			try {
				String res = getInputLine(question);
				if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {
					return true;
				}
				if (res.equalsIgnoreCase("n") || res.equalsIgnoreCase("no")) {
					return false;
				}
				System.out.println("Please provide an accepted answer.");
			} catch (IOException e) {
				System.out.println(ERROR_INPUT_MESSAGE);
			}

		}
	}

	private static String getInputLine(String question) throws IOException {
		System.out.print(question);
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String res = bufferRead.readLine();
		System.out.println();
		return res.trim();
	}

	public void printPlayerCards(Hand game) {
		System.out.print("You cards: ");
		List<Card> cards = game.getPlayerCards();
		printCards(cards);
		System.out.println();
	}

	private static void printCards(List<Card> cards) {
		for (Card card : cards) {
			printCard(card);
		}
	}

	public void printDealerCards(Hand game) {
		System.out.print("Dealer's cards: ");
		List<Card> cards = game.getDealerCards();
		printCards(cards);
	}

	public void printDealerExceeded21() {
		System.out.println("Dealer exceeded 21.");
	}

	public void printDealerScore(int score) {
		System.out.println("Dealer's score: " + score);
	}

	public void printDealerBlackjack() {
		System.out.println("Blackjack of Dealer!");
	}

	public void printPlayerScore(int score) {
		System.out.println("Your score: " + score);
	}

	public void printPlayerBlackjack() {
		System.out.println("You made Blackjack!");
	}

	public void printPlayerExceeded21() {
		System.out.println("You exceeded 21.");
	}

	public void printNewHand() {
		System.out.println("*** A new hand starts ***");
	}

	public void printEndOfHand() {
		System.out.println("*** The hand is over ***");
	}

	public void printBreakLine() {
		System.out.println();
	}

	public void printPlayerWin() {
		System.out.println("You win!");
	}

	public void printPlayerLost() {
		System.out.println("You lost.");
	}

	public void printPush() {
		System.out.println("Push, it's a tied score.");
	}

	public void printPlayerChips(int playerChips) {
		System.out.println("You have " + playerChips + " chips.");
	}

	public void printPlayerNotEnoughChips() {
		System.out.println("You don't have enough chips for a new hand.");
	}

	public boolean askNewHand() {
		return askYN("Do you want to play another hand? (y/n): ");
	}

	public void printPlayerWinChips(int chips) {
		System.out.println("You won " + chips + " chips.");
	}

	public void printPlayerChipsLost(int chips) {
		System.out.println("You lost " + chips + " chips.");
	}

	public void printGameOver() {
		System.out.println();
		System.out.println("******* GAME OVER *******");
		System.out.println();
	}

	public int askChipsBet(int minimumBet, int playerChips) {
		while (true) {
			try {
				String res = getInputLine("Please provide your bet (minimum " + minimumBet + ", maximum=" + playerChips + ") :");
				try {
					int resBet = Integer.parseInt(res);
					if (resBet >= minimumBet && resBet <= playerChips) {
						return resBet;
					}
				} catch (NumberFormatException e) {
					// resBet still -1, error
				}

				System.out.println("Please provide a correct bet.");
			} catch (IOException e) {
				System.out.println(ERROR_INPUT_MESSAGE);
			}

		}
	}

	public void printWelcome() {
		System.out.println("****** Welcome to Blackjack ******");
		System.out.println("Winning hands are paid 1:1, Blackjack are paids 3:2.");
		System.out.println("One card deck is used, shuffled every hand.");
		System.out.println();
	}

	/**
	 * Sleep for 2 seconds to improve user experience
	 */
	public void sleep() {
		try {
			Thread.sleep(SLEEP_TIME_MS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void printFirstDealerCard(Hand hand) {
		System.out.println("Dealer's visible card: " + hand.getDealerCards().get(0));
	}

	public void printPushChips() {
		System.out.println("You have won or lost 0 chips");
	}

	public void printDealerLastCard(Hand hand) {
		List<Card> dealerCards = hand.getDealerCards();
		System.out.print(" " + dealerCards.get(dealerCards.size()-1));
	}

}
