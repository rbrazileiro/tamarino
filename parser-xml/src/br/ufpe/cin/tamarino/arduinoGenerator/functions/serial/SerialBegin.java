package br.ufpe.cin.tamarino.arduinoGenerator.functions.serial;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

/**
 * 
 * @author Giovane Boaviagem
 * @since 26/06/2012
 *
 */
public class SerialBegin extends AbstractScript {
	private int boud;

	@Override
	public void mountScript() {
		this.setScript("Serial.begin("+boud+");\n");
	}

	/**
	 * @param boud
	 */
	public SerialBegin(int boud) {
		super();
		this.boud = boud;
	}

	/**
	 * @return the boud
	 */
	public int getBoud() {
		return boud;
	}

	/**
	 * @param boud the boud to set
	 */
	public void setBoud(int boud) {
		this.boud = boud;
	}
	
	

}
