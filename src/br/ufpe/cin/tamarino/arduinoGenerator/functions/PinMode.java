package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.arduinoGenerator.PinFunctions;

public class PinMode extends AbstractFunction {

	public PinMode(int pinNumber, PinFunctions function){
		super();
		String[] data = {pinNumber +"", function.toString()};
		mountScript(data);
	}
	
	@Override
	protected void mountScript(String[] data) {
		script = "pinmode("+data[0]+","+data[1]+");\n";
	}

}
