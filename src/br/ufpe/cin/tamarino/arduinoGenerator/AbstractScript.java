package br.ufpe.cin.tamarino.arduinoGenerator;

public abstract class AbstractScript {	
	private static int degree = 0;
	
	private String script;
	
	public AbstractScript(){
		this.script="";
	}

	public abstract void mountScript();
	
	public String getScript() {
		StringBuffer tabs=new StringBuffer("");
		for(int i=0;i<degree;i++){
			tabs.append("\t");
		}
		return tabs.toString()+script;
	}
	
	protected void setScript(String script){
		this.script=script;
	}

	protected static void addTabs() {
		degree++;
	}
	
	protected static void remTabs(){
		degree--;
	}
}
