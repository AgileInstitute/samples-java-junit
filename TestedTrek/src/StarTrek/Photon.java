package StarTrek;

public class Photon extends Weapon {

	public Photon(Game game) {
		super(game);
	}
	
	protected int extractAmountToFire(ProxyWebGadget wg) {
		return 1;
	}
	
	protected String hitMessage(int distance, int damage) {
		return "Photons hit Klingon at " + distance + " sectors with " + damage + " units";
	}
	
	protected String missedMessage(int distance) {
		return "Torpedo missed Klingon at " + distance + " sectors...";
	}

	protected boolean hasSufficientResourceToFire(int amountIgnoredForNow) {
		return game.getTorpedoes()  > 0;
	}

	protected String insufficientResourceMessage() {
		return "No more photon torpedoes!";
	}

	protected boolean missed(int distance) {
		return game.nextRandom(4) + ((distance / 500) + 1) > 7;
	}

	protected void subtractExpendedResource(int amountIgnoredThoughItWouldWorkButNeedsTesting) {
		game.setTorpedoes(game.getTorpedoes() - 1);
	}

	protected int calculateDamage(int amountIgnored, int distanceIgnored) {
		return 800 + game.nextRandom(50);
	}


}
