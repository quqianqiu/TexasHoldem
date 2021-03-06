package rollout;

import java.util.ArrayList;

import core.card.Card;
import core.card.Card.SIGN;
import core.card.Card.VALUE;

/**
 * Contains hand strengths at preFlop for a given number of players
 *
 */
public class PreFlop {

	private double[][] suitedPreFlop;
	private double[][] unsuitedPreFlop;

	/**
	 * Reads the hand strengths for a given number of players
	 * @param players Number of players
	 * @param pathname Path to the file with the calculated hand strengths
	 * @throws Exception
	 */
	public PreFlop(int players, String pathname) throws Exception {
		if(!pathname.endsWith("/")) {
			pathname = pathname.concat("/");
		}
		suitedPreFlop = new Reader().readFile(pathname + players
				+ "_suited.csv");
		unsuitedPreFlop = new Reader().readFile(pathname + players
				+ "_unsuited.csv");
	}

	/**
	 * 
	 * @param holeCards Hole cards
	 * @return Hand strength of the given hole cards
	 * @throws Exception
	 */
	public double getStrength(ArrayList<Card> holeCards) throws Exception {
		if (holeCards.size() != 2) {
			throw new Exception("Not correct amount of hole cards");
		}
		if (holeCards.get(0).sign == holeCards.get(1).sign) { // suited
			// in cause of a triangular matrix you have to look on the right sight
			if (holeCards.get(0).value.ordinal() < holeCards.get(1).value
					.ordinal()) {
				return suitedPreFlop[holeCards.get(0).value.ordinal()][holeCards
						.get(1).value.ordinal()];
			} else {
				return suitedPreFlop[holeCards.get(1).value.ordinal()][holeCards
						.get(0).value.ordinal()];
			}
		} else { // unsuited
			// in cause of a triangular matrix you have to look on the right sight
			if (holeCards.get(0).value.ordinal() <= holeCards.get(1).value
					.ordinal()) {
				return unsuitedPreFlop[holeCards.get(0).value.ordinal()][holeCards
						.get(1).value.ordinal()];
			} else {
				return unsuitedPreFlop[holeCards.get(1).value.ordinal()][holeCards
						.get(0).value.ordinal()];
			}
		}
	}
	
	public String toString() {
		String output = "suited: \n";
		for (int i = 0; i < suitedPreFlop.length; i++) {
			for (int j = 0; j < suitedPreFlop[i].length; j++) {
				output += "\t" + suitedPreFlop[i][j];
			}
			output += "\n";
		}
		output += "\nunsuited: \n";
		for (int i = 0; i < unsuitedPreFlop.length; i++) {
			for (int j = 0; j < unsuitedPreFlop[i].length; j++) {
				output += "\t" + unsuitedPreFlop[i][j];
			}
			output += "\n";
		}
		return output;
	}
	
	public static void main(String[] args) throws Exception {
		PreFlop preFlop2 = new PreFlop(2, "./rollouts/");
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(SIGN.CLUBS, VALUE.TWO));
		holeCards.add(new Card(SIGN.DIAMONDS, VALUE.THREE));
		System.out.println(preFlop2.getStrength(holeCards));
		ArrayList<Card> holeCards2 = new ArrayList<Card>();
		holeCards2.add(new Card(SIGN.DIAMONDS, VALUE.THREE));
		holeCards2.add(new Card(SIGN.HEARTS, VALUE.TWO));
		System.out.println(preFlop2.getStrength(holeCards2));
		
	}
}
