package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.PinLevels;

public class DigitalRead extends AbstractScript {

	private int pin;
	private String variableName;

	public DigitalRead(int pin, String variableName) {
		super();
		this.pin = pin;
		this.variableName = variableName;

		mountScript();
	}
	
	public DigitalRead(){
		super();
	}

	@Override
	public void mountScript() {
		addTabs();
		script += variableName + " = digitalRead(" + pin + ");\n";
	}

	/**
	 * @return the pin
	 */
	public int getPin() {
		return pin;
	}

	/**
	 * @param pin
	 *            the pin to set
	 */
	public void setPin(int pin) {
		this.pin = pin;
	}

	/**
	 * @return the variableName	public void mountScript() {
		addTabs();
		script += "delay("+data[0]+");\n";
	}
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName
	 *            the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

}