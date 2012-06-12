package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

public class Delay extends AbstractFunction{
	
	static{
		ParserTamarino.getInstance().addAlias("delay", Delay.class);
	}
	
	private long time;

	public Delay(long time){
		super();
		this.time=time;		
		mountScript();
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
