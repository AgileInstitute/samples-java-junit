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
		Weapon weapon = null;
        if (wg.parameter("command").equals("phaser")) {
        	weapon = new Phaser(this);

		} else if (wg.parameter("command").equals("photon")) {
			weapon = new Photon(this);
		}
		weapon.fire(wg, enemy);
	}


    // note we made generator public in order to mock it
    // it's ugly, but it's telling us something about our *design!* ;-)
	public static Random generator = new Random();
	public static int nextRandom(int maximum) {
		return generator.nextInt(maximum);
	}

}
