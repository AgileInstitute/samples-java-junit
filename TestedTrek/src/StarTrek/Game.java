package StarTrek;

import java.util.Random;

import Untouchables.WebGadget;

public class Game {

	private int energy = 10000;
	private int torpedoes = 8;

    public int EnergyRemaining() {
        return energy;
    }

    public void setTorpedoes(int value) {
            torpedoes = value;
    }
    public int getTorpedoes() {
            return torpedoes;
        
    }

    public void fireWeapon(WebGadget wg) {
        fireWeapon(new Galaxy(wg));
    }

    public void fireWeapon(Galaxy wg) {
		Klingon enemy = (Klingon) wg.variable("target");
        if (wg.parameter("command").equals("phaser")) {
        	Phaser phaser = new Phaser();
			firePhaser(wg, enemy);

		} else if (wg.parameter("command").equals("photon")) {
			firePhoton(wg, enemy);
		}
	}

	private void firePhoton(Galaxy wg, Klingon enemy) {
		if (hasATorpedo()) {
			int distance = enemy.distance();
			if (torpedoMissed(distance)) {
				wg.writeLine("Torpedo missed Klingon at " + distance + " sectors...");
			} else {
				int damage = calculatePhotonDamage();
				wg.writeLine("Photons hit Klingon at " + distance + " sectors with " + damage + " units");
				if (enemyDestroyed(enemy, damage)) {
					wg.writeLine(enemyDestroyedMessage());
					enemy.delete();
				} else {
					damageEnemy(enemy, damage);
					wg.writeLine(enemyDamagedMessage(enemy));
				}
			}
			subtractExpendedTorpedo();

		} else {
			wg.writeLine(insufficientResourceMessage());
		}
	}

	private void firePhaser(Galaxy wg, Klingon enemy)
			throws NumberFormatException {
		int amount = Integer.parseInt(wg.parameter("amount"));
		if (hasEnoughEnergy(amount)) {
			int distance = enemy.distance();
			if (phasersMissed(distance)) {
				wg.writeLine("Klingon out of range of phasers at " + distance + " sectors...");
			} else {
				int damage = calculatePhaserDamage(amount, distance);
				wg.writeLine("Phasers hit Klingon at " + distance + " sectors with " + damage + " units");
				if (enemyDestroyed(enemy, damage)) {
					wg.writeLine(enemyDestroyedMessage());
					enemy.delete();
				} else {
					damageEnemy(enemy, damage);
					wg.writeLine(enemyDamagedMessage(enemy));
				}
			}
			subtractExpendedEnergy(amount);

		} else {
			wg.writeLine(insufficientEnergyMessage());
		}
	}

	private String insufficientResourceMessage() {
		return "No more photon torpedoes!";
	}

	private int subtractExpendedTorpedo() {
		return torpedoes -= 1;
	}

	private String enemyDamagedMessage(Klingon enemy) {
		return "Klingon has " + enemy.getEnergy() + " remaining";
	}

	private String enemyDestroyedMessage() {
		return "Klingon destroyed!";
	}

	private void damageEnemy(Klingon enemy, int damage) {
		enemy.setEnergy(enemy.getEnergy() - damage);
	}

	private int calculatePhotonDamage() {
		return 800 + rnd(50);
	}

	private String insufficientEnergyMessage() {
		return "Insufficient energy to fire phasers!";
	}

	private boolean torpedoMissed(int distance) {
		return rnd(4) + ((distance / 500) + 1) > 7;
	}

	private boolean hasATorpedo() {
		return torpedoes  > 0;
	}

	private int subtractExpendedEnergy(int amount) {
		return energy -= amount;
	}

	private boolean enemyDestroyed(Klingon enemy, int damage) {
		return damage >= enemy.getEnergy();
	}

	private int calculatePhaserDamage(int amount, int distance) {
		int damage;
		damage = amount - (((amount /20)* distance /200) + rnd(200));
		if (damage < 1)
			damage = 1;
		return damage;
	}

	private boolean phasersMissed(int distance) {
		return distance > 4000;
	}

	private boolean hasEnoughEnergy(int amount) {
		return energy >= amount;
	}

    // note we made generator public in order to mock it
    // it's ugly, but it's telling us something about our *design!* ;-)
	public static Random generator = new Random();
	private static int rnd(int maximum) {
		return generator.nextInt(maximum);
	}


}
