package br.ufpe.cin.tamarino.circuit.arduino;

import java.util.LinkedList;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.Delay;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.DigitalRead;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.DigitalWrite;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.PinMode;
import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.xml.ParserTamarino;



/**
 * Represents a arduino board
 * @author Giovane Boaviagem
 * @since 06/06/2012
 *
 */
public class Arduino extends Circuit{
	private ArduinoType type;	
	private LinkedList<AbstractScript> setup;
	private LinkedList<AbstractScript> loop;
	
	static{
		ParserTamarino.getInstance().addAlias("arduino", Arduino.class);
		
		//pacote arduinoGenerator.functions
		ParserTamarino.getInstance().addAlias("pinMode", PinMode.class);
		ParserTamarino.getInstance().addAlias("delay", Delay.class);
		ParserTamarino.getInstance().addAlias("digitalRead", DigitalRead.class);
		ParserTamarino.getInstance().addAlias("digitalWrite", DigitalWrite.class);
		ParserTamarino.getInstance().addAlias("pinMode", PinMode.class);
	}
	
	
	/**
	 * @param type Type of the arduino board (see {@link ArduinoType})
	 * @param inputs List of the input components connected to the arduino
	 * @param outputs List of the output components connected to the arduino 
	 */
	public Arduino(String name,String author,long creation,String description,ArduinoType type) {
		super(name, author,creation,description);
	}


	/**
	 * @return the setup
	 */
	public LinkedList<AbstractScript> getSetup() {
		return setup;
	}


	/**
	 * @param setup the setup to set
	 */
	public void setSetup(LinkedList<AbstractScript> setup) {
		this.setup = setup;
	}


	/**
	 * @return the loop
	 */
	public LinkedList<AbstractScript> getLoop() {
		return loop;
	}


	/**
	 * @param loop the loop to set
	 */
	public void setLoop(LinkedList<AbstractScript> loop) {
		this.loop = loop;
	}


	/**
	 * @return the type
	 */
	public ArduinoType getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(ArduinoType type) {
		this.type = type;
	}
	
	
}
