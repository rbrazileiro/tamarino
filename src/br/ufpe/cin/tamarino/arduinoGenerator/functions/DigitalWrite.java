package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.PinLevels;

public class DigitalWrite extends AbstractScript {

	private int pin;
	private PinLevels level;

	public DigitalWrite(int pin, PinLevels level){
		super();
		this.pin=pin;
		this.level=level;		
		mountScript();
	}
	
	public DigitalWrite(){
		super();
	}
	
	@Override
	public void mountScript() {
		this.setScript("digitalWrite("+pin+","+level+");\n");
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
	 * @return the level
	 */
	public PinLevels getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(PinLevels level) {
		this.level = level;
	}
	
	

}