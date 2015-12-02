package tests;

import Untouchables.WebGadget;

public class ProxyWebGadget {
	private WebGadget realOne;
	
	public String parameter(String parameterName) {
		return realOne.parameter(parameterName);
	}

	public Object variable(String variableName) {
		return realOne.variable(variableName);
	}

	public void writeLine(String message) {
		realOne.writeLine(message);
	}

	public ProxyWebGadget(WebGadget theRealOne) {
		this.realOne = theRealOne;
	}

}
