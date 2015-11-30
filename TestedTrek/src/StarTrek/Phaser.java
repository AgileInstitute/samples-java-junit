package StarTrek;

public class Phaser extends Weapon {
	public Phaser(Game game) {
		super(game);
	}
	
	public void firePhaser(ProxyWebGadget wg, Klingon enemy)
			throws NumberFormatException {
		int amount = Integer.parseInt(wg.parameter("amount"));
		if (hasEnoughEnergy(amount)) {
			int distance = enemy.distance();
			if (phasersMissed(distance)) {
				wg.writeLine("Klingon out of range of phasers at " + distance + " sectors...");
			} else {
				int damage = calculatePhaserDamage(amount, distance);
				wg.writeLine("Phasers hit Klingon at " + distance + " sectors with " + damage + " units");
				if (enemy.wouldBeDestroyedBy(damage)) {
					wg.writeLine(enemy.destroyedMessage());
					enemy.beDestroyed();
				} else {
					enemy.receiveDamage(damage);
					wg.writeLine(enemy.damagedMessage());
				}
			}
			game.subtractExpendedEnergy(amount);

		} else {
			wg.writeLine(insufficientEnergyMessage());
		}
	}

	private String insufficientEnergyMessage() {
		return "Insufficient energy to fire phasers!";
	}

	private int calculatePhaserDamage(int amount, int distance) {
		int damage;
		damage = amount - (((amount /20)* distance /200) + game.nextRandom(200));
		if (damage < 1)
			damage = 1;
		return damage;
	}

	private boolean phasersMissed(int distance) {
		return distance > 4000;
	}

	private boolean hasEnoughEnergy(int amount) {
		return game.energyRemaining() >= amount;
	}

}
