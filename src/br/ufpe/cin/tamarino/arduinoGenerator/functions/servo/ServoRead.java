package br.ufpe.cin.tamarino.arduinoGenerator.functions.servo;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 25/06/2012
 *
 */
public class ServoRead extends AbstractScript{
	private String servoName;
	private String varName;
	
	@Override
	public void mountScript() {
		this.setScript(varName + " = "+ servoName+".read();\n");		
	}

	/**
	 * @param servoName
	 * @param varName
	 */
	public ServoRead(String servoName, String varName) {
		super();
		this.servoName = servoName;
		this.varName = varName;
	}
	
	public ServoRead(){}

	/**
	 * @return the servoName
	 */
	public String getServoName() {
		return servoName;
	}

	/**
	 * @param servoName the servoName to set
	 */
	public void setServoName(String servoName) {
		this.servoName = servoName;
	}

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
