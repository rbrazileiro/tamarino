package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.arduinoGenerator.PinLevels;

public class DigitalWrite extends AbstractFunction {

	public DigitalWrite(int pin, PinLevels level){
		super();
		String data[] = {pin +"", level.toString()};
		mountScript(data);
	}
	
	@Override
	protected void mountScript(String[] data) {
		script = "digitalWrite("+data[0]+","+data[1]+");\n";
	}

}
