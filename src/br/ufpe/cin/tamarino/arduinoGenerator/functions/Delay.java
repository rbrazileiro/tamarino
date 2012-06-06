package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;

public class Delay extends AbstractFunction{

	public Delay(long time){
		super();
		String[] data = {time + ""};
		mountScript(data);
	}

	@Override
	protected void mountScript(String[] data) {
		script = "delay("+data[0]+");\n";
	}
	
}
