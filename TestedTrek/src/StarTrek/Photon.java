package StarTrek;

public class Photon extends Weapon {

	public Photon(Game game) {
		super(game);
	}
	public void fire(ProxyWebGadget wg, Klingon enemy) {
		int amount = extractAmountToFire(wg);
		if (hasSufficientResourceToFire(amount)) {
			int distance = enemy.distance();
			if (missed(distance)) {
				wg.writeLine(missedMessage(distance));
			} else {
				int damage = calculateDamage(amount, distance);
				wg.writeLine(hitMessage(distance, damage));
				if (enemy.wouldBeDestroyedBy(damage)) {
					wg.writeLine(enemy.destroyedMessage());
					enemy.beDestroyed();
				} else {
					enemy.receiveDamage(damage);
					wg.writeLine(enemy.damagedMessage());
				}
			}
			subtractExpendedResource(amount);

		} else {
			wg.writeLine(insufficientResourceMessage());
		}
	}
	
	private int extractAmountToFire(ProxyWebGadget wg) {
		return 1;
	}
	private String hitMessage(int distance, int damage) {
		return "Photons hit Klingon at " + distance + " sectors with " + damage + " units";
	}
	private String missedMessage(int distance) {
		return "Torpedo missed Klingon at " + distance + " sectors...";
	}

	private boolean hasSufficientResourceToFire(int amountIgnoredForNow) {
		return game.getTorpedoes()  > 0;
	}

	private String insufficientResourceMessage() {
		return "No more photon torpedoes!";
	}

	private boolean missed(int distance) {
		return game.nextRandom(4) + ((distance / 500) + 1) > 7;
	}

	private void subtractExpendedResource(int amountIgnoredThoughItWouldWorkButNeedsTesting) {
		game.setTorpedoes(game.getTorpedoes() - 1);
	}

	private int calculateDamage(int amountIgnored, int distanceIgnored) {
		return 800 + game.nextRandom(50);
	}


}
