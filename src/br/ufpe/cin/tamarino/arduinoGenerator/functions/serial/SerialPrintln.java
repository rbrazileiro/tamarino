package br.ufpe.cin.tamarino.arduinoGenerator.functions.serial;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

public class SerialPrintln extends AbstractScript {
	private String value;
	

	@Override
	public void mountScript() {
		this.setScript("Serial.println("+value+");\n");
	}


	/**
	 * @param value
	 */
	public SerialPrintln(String value) {
		super();
		this.value = value;
	}
	
	public SerialPrintln(){}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
