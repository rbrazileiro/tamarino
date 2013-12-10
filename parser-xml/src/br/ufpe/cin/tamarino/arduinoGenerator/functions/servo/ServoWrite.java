package br.ufpe.cin.tamarino.arduinoGenerator.functions.servo;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 25/06/2012
 *
 */
public class ServoWrite extends AbstractScript{
	private String servoName;
	private String value;

	@Override
	public void mountScript() {
		String script=servoName+".write("+value+");\n";
		this.setScript(script);
	}

	/**
	 * @param servoName
	 * @param value
	 */
	public ServoWrite(String servoName, String value) {
		super();
		this.servoName = servoName;
		this.value = value;
	}
	
	public ServoWrite(){}

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
