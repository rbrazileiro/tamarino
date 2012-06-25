package br.ufpe.cin.tamarino.arduinoGenerator.functions.servo;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 25/06/2012
 *
 */
public class ServoAttach extends AbstractScript{
	private String servoName;
	private int pin;

	@Override
	public void mountScript() {		
		this.setScript(this.servoName+".attach("+this.pin+");\n");
	}

	/**
	 * @param servoName
	 * @param value
	 */
	public ServoAttach(String servoName, int value) {
		super();
		this.servoName = servoName;
		this.pin = value;
	}	
	
	public ServoAttach(){}

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
	 * @return the value
	 */
	public int getPin() {
		return pin;
	}

	/**
	 * @param value the value to set
	 */
	public void setPin(int value) {
		this.pin = value;
	}
	
	
}
