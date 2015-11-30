package StarTrek;

public abstract class Weapon {

	protected Game game;

	public Weapon(Game game) {
		this.game = game;
	}

	public void fire(ProxyWebGadget wg, Klingon enemy) {
		int amount = extractAmountToFire(wg);
		if (hasSufficientResourceToFire(amount)) {
			int distance = enemy.distance();
			if (missed(distance)) {
				wg.writeLine(missedMessage(distance));
			} else {
				hit(wg, enemy, amount, distance);
			}
			subtractExpendedResource(amount);

		} else {
			wg.writeLine(insufficientResourceMessage());
		}
	}

	private void hit(ProxyWebGadget wg, Klingon enemy, int amount, int distance) {
		int damage = calculateDamage(amount, distance);
		wg.writeLine(hitMessage(distance, damage));
		enemy.takeHit(wg, damage);
	}

	protected abstract int extractAmountToFire(ProxyWebGadget wg);
	protected abstract boolean hasSufficientResourceToFire(int amount);
	protected abstract String hitMessage(int distance, int damage);
	protected abstract int calculateDamage(int amount, int distance);
	protected abstract boolean missed(int distance);
	protected abstract String missedMessage(int distance);
	protected abstract void subtractExpendedResource(int amount);
	protected abstract String insufficientResourceMessage();
	

}
