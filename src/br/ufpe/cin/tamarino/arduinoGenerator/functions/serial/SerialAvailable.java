package br.ufpe.cin.tamarino.arduinoGenerator.functions.serial;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 26/06/2012
 *
 */
public class SerialAvailable extends AbstractScript {
	private String varName;
	
	@Override
	public void mountScript() {
		this.setScript(varName+" = Serial.available();\n");
	}

	/**
	 * @param varName
	 */
	public SerialAvailable(String varName) {
		super();
		this.varName = varName;
	}
	
	public SerialAvailable(){}

	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	

}
