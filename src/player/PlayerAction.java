package player;

public class PlayerAction {

	public enum ACTION {
		FOLD, CALL, RAISE;
	}

	public ACTION action;
	public int oldStake;
	public int toPay;
	public double potOdd;

	// for opponent model
	public double getPotOdd() {
		return potOdd;
	}

	@Override
	/**
	 * Defines thresholds for considering player actions being equal
	 * E.g. two actions where 20 and 22 units are bidden should be considered equal
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;

		if (!(o instanceof PlayerAction))
			return false;
		PlayerAction that = (PlayerAction) o;

		// action
		if (this.action != that.action) {
			return false;
		}

		// to pay
		int[] toPayThresholds = { 0, 500 };
		for (int i = 1; i < toPayThresholds.length; i++) {
			if (((this.toPay < toPayThresholds[i] && this.toPay >= toPayThresholds[i - 1]) && !(that.toPay < toPayThresholds[i] && that.toPay >= toPayThresholds[i - 1]))) {
				return false;
			}
		}
		if ((this.toPay >= toPayThresholds[toPayThresholds.length - 1])
				&& !(that.toPay >= toPayThresholds[toPayThresholds.length - 1])) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		if (action == ACTION.FOLD) {
			return "" + action;
		}
		return "" + action + " | bet: " + (toPay + oldStake);
	}
}
