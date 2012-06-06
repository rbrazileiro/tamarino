package br.ufpe.cin.tamarino.arduinoGenerator;

public abstract class AbstractFunction {

	protected String script;
	
	protected abstract void mountScript(String[] data);
}
