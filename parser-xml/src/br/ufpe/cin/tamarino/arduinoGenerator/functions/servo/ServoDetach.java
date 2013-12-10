package br.ufpe.cin.tamarino.arduinoGenerator.functions.servo;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 25/06/2012
 *
 */
public class ServoDetach extends AbstractScript{
	private String servoName;

	@Override
	public void mountScript() {
		this.setScript(servoName+".detach();\n");		
	}

	/**
	 * @param servoName
	 */
	public ServoDetach(String servoName) {
		super();
		this.servoName = servoName;
	}
	
	public ServoDetach(){}

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
	
	

}
