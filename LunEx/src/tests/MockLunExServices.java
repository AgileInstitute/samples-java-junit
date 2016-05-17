package tests;

import lunEx.LunExServices;

public class MockLunExServices extends LunExServices {

	private int valueToReturn;
	private int callCount = 0;

	public MockLunExServices(int valueToReturn) {
		this.valueToReturn = valueToReturn;
	}
	
	@Override
	public int currentPrice(String symbol) {
		callCount++;
		return valueToReturn;
	}

	public boolean wasCalledOnceAndOnlyOnce() {
		return callCount == 1;
	}

}
