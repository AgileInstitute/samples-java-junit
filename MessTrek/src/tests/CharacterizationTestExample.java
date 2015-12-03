package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.mockito.*;
import org.junit.Test;

import StarTrek.*;
import Untouchables.WebGadget;

public class CharacterizationTestExample {

	/*
	 * samples-java-junit/MessTrek: a solution to labs-java-junit/MessTrek
	 * using Mockito.
	 * 
	 * This is not a unit test.  This is what you get when you try to
	 * do unit testing of code that has grown without using TDD.
	 * It's a characterization test, which replicates an existing scenario.
	 * 
	 * This is only one of about 9 scenarios which would need to be tested
	 * before you could safely refactor this code.
	 * 
	 * If you want to practice refactoring,
	 * see labs-java-junit/TestedTrek
	 */
	@Test
	public void testWhenPhotonsDamageKlingon() {
		// given
		Game game = new Game();
		
		Random mockRandom = Mockito.mock(Random.class);
		Mockito.when(mockRandom.nextInt(Mockito.anyInt())).thenReturn(2);
		Game.generator = mockRandom;
		
		Klingon target = Mockito.mock(Klingon.class);
		Mockito.when(target.distance())
			.thenReturn(365);
		int klingonInitialEnergy = 2000;
		int klingonEnergyRemainingAfterDamage = klingonInitialEnergy-802;
		Mockito.when(target.getEnergy())
			.thenReturn(klingonInitialEnergy)
			.thenReturn(klingonInitialEnergy)
			.thenReturn(klingonEnergyRemainingAfterDamage);

		ProxyWebGadget mockProxyWebGadget = Mockito.mock(ProxyWebGadget.class);
		Mockito.when(mockProxyWebGadget.parameter("command"))
			.thenReturn("photon");
		Mockito.when(mockProxyWebGadget.variable("target"))
			.thenReturn(target);
		
		// when
		game.fireWeapon(mockProxyWebGadget);
		
		// then
		assertEquals(7, game.torpedoesRemaining());
		Mockito.verify(target, Mockito.never()).delete();
		Mockito.verify(target, Mockito.times(1)).setEnergy(klingonEnergyRemainingAfterDamage);
		Mockito.verify(mockProxyWebGadget,
				Mockito.times(1)).writeLine(
						"Photons hit Klingon at 365 sectors with 802 units");
		Mockito.verify(mockProxyWebGadget,
				Mockito.times(1)).writeLine(
						"Klingon has 1198 remaining");
	}

}
