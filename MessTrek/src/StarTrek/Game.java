package StarTrek;

import java.util.Random;

import tests.ProxyWebGadget;
import Untouchables.WebGadget;

public class Game {

	private int e = 10000;
	private int t = 8;
	
	/**
	 * This is the public-facing method signature that existed in the original.
	 * @param wg
	 */
	public void fireWeapon(WebGadget wg) {
		fireWeapon(new ProxyWebGadget(wg));
	}
	
	/**
	 * This is the new, testable method that contains all the *unchanged* code.
	 * @param wg
	 */
	public void fireWeapon(ProxyWebGadget wg) {
		if (wg.parameter("command").equals("phaser")) {
			int amount = Integer.parseInt(wg.parameter("amount"));
			Klingon enemy = (Klingon) wg.variable("target");
			if (e >= amount) {
				int distance = enemy.distance();
				if (distance > 4000) {
					wg.writeLine("Klingon out of range of phasers at " + distance + " sectors...");
				} else {
					int damage = amount - (((amount /20)* distance /200) + rnd(200));
					if (damage < 1)
						damage = 1;
					wg.writeLine("Phasers hit Klingon at " + distance + " sectors with " + damage + " units");
					if (damage < enemy.getEnergy()) {
						enemy.setEnergy(enemy.getEnergy() - damage);
						wg.writeLine("Klingon has " + enemy.getEnergy() + " remaining");
					} else {
						wg.writeLine("Klingon destroyed!");
						enemy.delete();
					}
				}
				e -= amount;

			} else {
				wg.writeLine("Insufficient energy to fire phasers!");
			}

		} else if (wg.parameter("command").equals("photon")) {
			Klingon enemy = (Klingon) wg.variable("target");
			if (t  > 0) {
				int distance = enemy.distance();
				if ((rnd(4) + ((distance / 500) + 1) > 7)) {
					wg.writeLine("Torpedo missed Klingon at " + distance + " sectors...");
				} else {
					int damage = 800 + rnd(50);
					wg.writeLine("Photons hit Klingon at " + distance + " sectors with " + damage + " units");
					if (damage < enemy.getEnergy()) {
						enemy.setEnergy(enemy.getEnergy() - damage);
						wg.writeLine("Klingon has " + enemy.getEnergy() + " remaining");
					} else {
						wg.writeLine("Klingon destroyed!");
						enemy.delete();
					}
				}
				t -= 1;

			} else {
				wg.writeLine("No more photon torpedoes!");
			}
		}
	}

	public static Random generator = new Random();
	private static int rnd(int maximum) {
		return generator.nextInt(maximum);
	}

	public int torpedoesRemaining() {
		return t;
	}

}
