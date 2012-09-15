package core;

import java.util.ArrayList;

import player.ComputerPlayer;
import player.HandStrengthStrategy;
import player.IStrategy.AGGRESSIVITY;
import player.ImprovedHandStrengthStrategy;
import player.RandomStrategy;
import player.SimplePowerRankingStrategy;
import rollout.PreFlop;

public class Main {	

	public static void main(String[] args) throws Exception {
		
		Logger.DEBUG = true;
		
		int bettingRounds = 3;
		int bigBlindSize = 2;
		int hands = 10;
		int initialMoney = 0;
		int iterationsOfRollouts = 20; // for ImprovedHandStrenghStrategy
		
		int randomPlayers = 0;
		int simplePowerRankingPlayersRisky = 1;
		int simplePowerRankingPlayersModerate = 0;
		int simplePowerRankingPlayersConservative = 1;
		int handStrengthPlayersRisky = 1;
		int handStrengthPlayersModerate = 0;
		int handStrengthPlayersConservative = 1;
		int improvedHandStrengthPlayersRisky = 1;
		int improvedHandStrengthPlayersModerate = 0;
		int improvedHandStrengthPlayersConservative = 1;
		
		int playersInTotal = 10;
		String pathnameToRollout = "./rollouts";
		ArrayList<PreFlop> preFlops = new ArrayList<PreFlop>();
		for (int i = 2; i <= playersInTotal; i++) {
			preFlops.add(new PreFlop(i, pathnameToRollout));
		}
				
		// init
		Game g = new Game(bettingRounds, bigBlindSize);
		for (int i = 0; i < randomPlayers; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new RandomStrategy(), initialMoney));
		}
		for (int i = 0; i < simplePowerRankingPlayersRisky; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new SimplePowerRankingStrategy(AGGRESSIVITY.RISKY), initialMoney));
		}
		for (int i = 0; i < simplePowerRankingPlayersModerate; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new SimplePowerRankingStrategy(AGGRESSIVITY.MODERATE), initialMoney));
		}
		for (int i = 0; i < simplePowerRankingPlayersConservative; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new SimplePowerRankingStrategy(AGGRESSIVITY.CONSERVATIVE), initialMoney));
		}
		for (int i = 0; i < handStrengthPlayersRisky; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new HandStrengthStrategy(preFlops,AGGRESSIVITY.RISKY), initialMoney));
		}
		for (int i = 0; i < handStrengthPlayersModerate; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new HandStrengthStrategy(preFlops, AGGRESSIVITY.MODERATE), initialMoney));
		}
		for (int i = 0; i < handStrengthPlayersConservative; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new HandStrengthStrategy(preFlops, AGGRESSIVITY.CONSERVATIVE), initialMoney));
		}
		for (int i = 0; i < improvedHandStrengthPlayersRisky; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new ImprovedHandStrengthStrategy(preFlops,AGGRESSIVITY.RISKY, iterationsOfRollouts), initialMoney));
		}
		for (int i = 0; i < improvedHandStrengthPlayersModerate; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new ImprovedHandStrengthStrategy(preFlops,AGGRESSIVITY.MODERATE, iterationsOfRollouts), initialMoney));
		}
		for (int i = 0; i < improvedHandStrengthPlayersConservative; i++) {
			g.addPlayer(new ComputerPlayer(g.getState(), new ImprovedHandStrengthStrategy(preFlops,AGGRESSIVITY.CONSERVATIVE, iterationsOfRollouts), initialMoney));
		}
		
		// play
		for (int i = 0; i < hands; i++) {
			if(i % 100 == 0 && i > 0) {
				Logger.logInfo("Hand " + i + " of " + hands);
			}
			g.playHand(i);
		}
		
		// print
		g.printCredits();
	}
}
