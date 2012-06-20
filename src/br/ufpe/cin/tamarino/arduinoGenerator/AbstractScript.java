package br.ufpe.cin.tamarino.arduinoGenerator;

public abstract class AbstractScript {

	protected String script = "";

	public static int degree = 0;

	public abstract void mountScript();
	
	public String getScript() {
		return script;
	}

	public void addTabs() {

		if (script == null) script = "";
		for (int i = 0; i < degree; i++) {
			if (script == null || script.equals(""))
				script = "\t";
			else
				script += "\t";
		}
	}
	
	protected void decreaseDegree(){
		degree--;
	}
	protected void increaseDegree(){
		degree++;
	}
}
