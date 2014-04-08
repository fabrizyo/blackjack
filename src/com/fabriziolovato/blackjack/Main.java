package com.fabriziolovato.blackjack;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * It's the start class of game which contains the main method.
 * Some options are provided to configure the game but none of them are strictly necessary.
 * See getOptions method for an explanation of these options.
 * 
 * @author fabrizio
 *
 */
public class Main {

	private static final String VERSION_OPTION_NAME = "version";
	private static final String CHIPS_OPTION_NAME = "chips";
	private static final String MIN_BET_OPTION_NAME = "minBet";
	private static final String H17_OPTION_NAME = "H17";
	private static final String HELP_OPTION_NAME = "help";
	
	private static final int DEFAULT_MIN_BET = 1;
	private static final int DEFAULT_PLAYER_CHIPS = 100;

	public static void main(String[] args) {
		Options options = getOptions();

		// create the parser
		CommandLineParser parser = new BasicParser();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			if (line.hasOption(HELP_OPTION_NAME)) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -jar blackjack.jar [options]", options);
				return;
			}

			if (line.hasOption(VERSION_OPTION_NAME)) {
				System.out.println("Blackjack v1.0, developed by Fabrizio Lovato - fabrizio.lovato@gmail.com");
				return;
			}

			boolean H17 = line.hasOption(H17_OPTION_NAME); //default false

			Integer minBet = getMinBet(line);
			if (minBet == null) {
				return;
			}

			Integer playerChips = getPlayerChips(line, minBet);
			if (playerChips == null) {
				return;
			}
			
			GameView view = new GameView();
			new GameManager(playerChips, minBet,view,new HandController(view, H17)).play();

		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing of command arguments failed.  Reason: " + exp.getMessage());
		}
	}

	private static Integer getPlayerChips(CommandLine line, Integer minBet) {
		try {
			int playerchips = line.hasOption(CHIPS_OPTION_NAME) ? Integer.parseInt(line.getOptionValue(CHIPS_OPTION_NAME))
					: DEFAULT_PLAYER_CHIPS;
			if (playerchips > minBet) {
				return playerchips;
			}
			System.err.println("Player chips must be greater or equal minimum bet.");
		} catch (NumberFormatException e) {
			System.err.println("Plese provide a number as player chips.");
		}
		return null;
	}

	private static Integer getMinBet(CommandLine line) {
		try {
			int minimumBet = line.hasOption(MIN_BET_OPTION_NAME) ? Integer.parseInt(line.getOptionValue(MIN_BET_OPTION_NAME))
					: DEFAULT_MIN_BET;
			if (minimumBet >= 1) {
				return minimumBet;
			}
			System.err.println("Minimum value of minimum bet is 1");
		} catch (NumberFormatException e) {
			System.err.println("Plese provide a number as minimum bet.");
		}
		return null;
	}

	private static Options getOptions() {
		Options options = new Options();
		options.addOption(HELP_OPTION_NAME, false, "print this message");
		options.addOption(H17_OPTION_NAME, false, "use H17 for dealer strategy, default is S17 which means dealer doesn't hit a S17");
		options.addOption(MIN_BET_OPTION_NAME, true, "minimum bet of player, default 1");
		options.addOption(CHIPS_OPTION_NAME, true, "start chips of player, default 100, must be greater or equal minimum bet");
		options.addOption(VERSION_OPTION_NAME, false, "print the version");
		return options;
	}
}
