package StarTrek;

public abstract class Weapon {

	protected Game game;

	public Weapon(Game game) {
		this.game = game;
	}

	public abstract void fire(ProxyWebGadget wg, Klingon enemy);

}
