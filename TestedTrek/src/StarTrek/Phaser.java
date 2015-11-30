package StarTrek;

public class Phaser extends Weapon {
	public Phaser(Game game) {
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
			wg.writeLine(insufficientResoureMessage());
		}
	}

	private int extractAmountToFire(ProxyWebGadget wg) 
			throws NumberFormatException {
		return Integer.parseInt(wg.parameter("amount"));
	}

	private String hitMessage(int distance, int damage) {
		return "Phasers hit Klingon at " + distance + " sectors with " + damage + " units";
	}

	private String missedMessage(int distance) {
		return "Klingon out of range of phasers at " + distance + " sectors...";
	}

	private void subtractExpendedResource(int amount) {
		game.subtractExpendedEnergy(amount);
	}

	private String insufficientResoureMessage() {
		return "Insufficient energy to fire phasers!";
	}

	private int calculateDamage(int amount, int distance) {
		int damage;
		damage = amount - (((amount /20)* distance /200) + game.nextRandom(200));
		if (damage < 1)
			damage = 1;
		return damage;
	}

	private boolean missed(int distance) {
		return distance > 4000;
	}

	private boolean hasSufficientResourceToFire(int amount) {
		return game.energyRemaining() >= amount;
	}

}
