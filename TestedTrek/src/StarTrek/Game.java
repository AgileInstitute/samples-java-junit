package StarTrek;

import java.util.Random;

import Untouchables.WebGadget;

public class Game {

	private int energy = 10000;
	private int torpedoes = 8;

    public int energyRemaining() {
        return energy;
    }
    
	public void subtractExpendedEnergy(int amount) {
		energy -= amount;
	}

    public void setTorpedoes(int value) {
            torpedoes = value;
    }
    public int getTorpedoes() {
            return torpedoes;
        
    }

    public void fireWeapon(WebGadget wg) {
        fireWeapon(new ProxyWebGadget(wg));
    }

    public void fireWeapon(ProxyWebGadget wg) {
		Klingon enemy = (Klingon) wg.variable("target");
        if (wg.parameter("command").equals("phaser")) {
        	Phaser phaser = new Phaser(this);
			phaser.firePhaser(wg, enemy);

		} else if (wg.parameter("command").equals("photon")) {
			Photon photon = new Photon(this);
			firePhoton(wg, enemy);
		}
	}

	public void firePhoton(ProxyWebGadget wg, Klingon enemy) {
		if (hasATorpedo()) {
			int distance = enemy.distance();
			if (torpedoMissed(distance)) {
				wg.writeLine("Torpedo missed Klingon at " + distance + " sectors...");
			} else {
				int damage = calculatePhotonDamage();
				wg.writeLine("Photons hit Klingon at " + distance + " sectors with " + damage + " units");
				if (enemy.wouldBeDestroyedBy(damage)) {
					wg.writeLine(enemy.destroyedMessage());
					enemy.beDestroyed();
				} else {
					enemy.receiveDamage(damage);
					wg.writeLine(enemy.damagedMessage());
				}
			}
			subtractExpendedTorpedo();

		} else {
			wg.writeLine(insufficientResourceMessage());
		}
	}

	private String insufficientResourceMessage() {
		return "No more photon torpedoes!";
	}

	private boolean torpedoMissed(int distance) {
		return nextRandom(4) + ((distance / 500) + 1) > 7;
	}

	private boolean hasATorpedo() {
		return torpedoes  > 0;
	}

	private void subtractExpendedTorpedo() {
		setTorpedoes(getTorpedoes() - 1);
	}

	private int calculatePhotonDamage() {
		return 800 + nextRandom(50);
	}


    // note we made generator public in order to mock it
    // it's ugly, but it's telling us something about our *design!* ;-)
	public static Random generator = new Random();
	public static int nextRandom(int maximum) {
		return generator.nextInt(maximum);
	}

}
