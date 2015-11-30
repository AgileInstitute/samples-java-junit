package StarTrek;

public class Phaser extends Weapon {
	public Phaser(Game game) {
		super(game);
	}
	
	protected int extractAmountToFire(ProxyWebGadget wg) 
			throws NumberFormatException {
		return Integer.parseInt(wg.parameter("amount"));
	}

	protected String hitMessage(int distance, int damage) {
		return "Phasers hit Klingon at " + distance + " sectors with " + damage + " units";
	}

	protected String missedMessage(int distance) {
		return "Klingon out of range of phasers at " + distance + " sectors...";
	}

	protected void subtractExpendedResource(int amount) {
		game.subtractExpendedEnergy(amount);
	}

	protected boolean hasSufficientResourceToFire(int amount) {
		return game.energyRemaining() >= amount;
	}

	protected String insufficientResourceMessage() {
		return "Insufficient energy to fire phasers!";
	}

	protected int calculateDamage(int amount, int distance) {
		int damage;
		damage = amount - (((amount /20)* distance /200) + game.nextRandom(200));
		if (damage < 1)
			damage = 1;
		return damage;
	}

	protected boolean missed(int distance) {
		return distance > 4000;
	}


}
