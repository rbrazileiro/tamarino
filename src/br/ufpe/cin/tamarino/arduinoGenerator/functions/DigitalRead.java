package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;

public class DigitalRead extends AbstractFunction {
	
	private int pin;
	private String variableName;
	
	public DigitalRead(int pin, String variableName){
		super();
		this.pin=pin;
		this.variableName=variableName;	
		mountScript();
	}
	
	@Override
	public void mountScript() {
		script = this.pin +" = digitalRead("+this.variableName+");\n";
	}

	/**
	 * @return the pin
	 */
	public int getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(int pin) {
		this.pin = pin;
	}

	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	

}
