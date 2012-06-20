package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

public class Delay extends AbstractScript{
	
	static{
		ParserTamarino.getInstance().addAlias("delay", Delay.class);
	}
	
	private long time;

	public Delay(long time){
		super();
		this.time=time;		
		mountScript();
	}
	
	public Delay(){
		super();
	}

	@Override
	public void mountScript() {
		script = "delay("+this.time+");\n";
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
}