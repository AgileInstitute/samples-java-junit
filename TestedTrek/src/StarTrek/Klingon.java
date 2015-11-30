package StarTrek;

import java.util.Random;

public class Klingon {
	private int distance;
	private int energy;
	
	public Klingon() {
		Random x = new Random();
		distance = 100 + x.nextInt(4000);
		energy = 1000 + x.nextInt(2000);
	}

	public int distance() {
		return distance;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int e) {
		energy = e;
	}

	public void beDestroyed() {
	}

	public boolean wouldBeDestroyedBy(int damage) {
		return damage >= getEnergy();
	}

	public void receiveDamage(int damage) {
		setEnergy(getEnergy() - damage);
	}

	String damagedMessage() {
		return "Klingon has " + getEnergy() + " remaining";
	}
	
	public String destroyedMessage() {
		return "Klingon destroyed!";
	}

	void takeHit(ProxyWebGadget wg, int damage) {
		if (wouldBeDestroyedBy(damage)) {
			beDestroyed();
			wg.writeLine(destroyedMessage());
		} else {
			receiveDamage(damage);
			wg.writeLine(damagedMessage());
		}
	}



}
