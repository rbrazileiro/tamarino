package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.arduinoGenerator.PinLevels;

public class DigitalRead extends AbstractFunction {

	public DigitalRead(int pin, String variableName){
		super();
		String data[] = {pin +"", variableName};
		mountScript(data);
	}
	
	@Override
	protected void mountScript(String[] data) {
		script = data[1] +" = digitalRead("+data[0]+");\n";
	}

}
