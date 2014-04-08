package com.fabriziolovato.blackjack;

/**
 * It represents all possible results of an {@link Hand}.
 * Push result means a tie.
 * 
 * @author fabrizio
 *
 */
public enum HandResult {
	Push, PlayerLost, PlayerWin, PlayerWinBlackjack;

	public boolean isWin() {
		return this.equals(PlayerWin) || this.equals(PlayerWinBlackjack);
	}

	public boolean isLost() {
		return this.equals(PlayerLost);
	}
}