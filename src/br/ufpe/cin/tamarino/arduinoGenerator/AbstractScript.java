package br.ufpe.cin.tamarino.arduinoGenerator;

public abstract class AbstractScript {

	protected String script = "";

	private static int degree = 0;

	public abstract void mountScript();
	
	public String getScript() {
		return script;
	}

	public void addTabs() {
		degree++;
	}
	
	public void remTabs(){
		degree--;
	}
}
