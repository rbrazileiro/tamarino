package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

public class Delay extends AbstractScript{
	
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
		this.setScript("delay("+this.time+");\n");
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