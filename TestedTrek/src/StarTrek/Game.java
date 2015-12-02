package StarTrek;

import java.security.InvalidParameterException;
import java.util.Random;

import Untouchables.WebGadget;

public class Game {
	
	public Game() {
		this(new Random());
	}
	
	public Game(Random randomGenerator) {
		this.generator = randomGenerator;
	}

	private Random generator;
	public int nextRandom(int maximum) {
		return generator.nextInt(maximum);
	}

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
        Weapon weapon = selectWeapon(wg.parameter("command"));
		weapon.fire(wg, enemy);
	}

	private Weapon selectWeapon(String weaponType) {
		if (weaponType.equals("phaser")) {
        	return new Phaser(this);
		} else if (weaponType.equals("photon")) {
			return new Photon(this);
		}
		// TODO: behavior was no-op, but untested; now it throws, but untested. Which is worse?
		throw new InvalidParameterException("No such weapon " + weaponType);
	}

}
