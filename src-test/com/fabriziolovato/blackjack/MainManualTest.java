package com.fabriziolovato.blackjack;

public class MainManualTest {

	public static void main(String[] args) {
		Main.main(new String[] { "-help" });
		System.out.println();
		Main.main(new String[] { "-version" });
		System.out.println();
		Main.main(new String[] { "-chips", "1000" });
	}
}
