package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.arduinoGenerator.PinFunctions;

public class PinMode extends AbstractFunction {
	private int pinNumber;
	private PinFunctions function;
	
	public PinMode(int pinNumber, PinFunctions function){
		super();
		this.pinNumber=pinNumber;
		this.function=function;		
		mountScript();
	}
	
	@Override
	public void mountScript() {
		script = "pinmode("+pinNumber+","+function.toString()+");\n";
	}

	/**
	 * @return the pinNumber
	 */
	public int getPinNumber() {
		return pinNumber;
	}

	/**
	 * @param pinNumber the pinNumber to set
	 */
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	/**
	 * @return the function
	 */
	public PinFunctions getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(PinFunctions function) {
		this.function = function;
	}
	
	

}
