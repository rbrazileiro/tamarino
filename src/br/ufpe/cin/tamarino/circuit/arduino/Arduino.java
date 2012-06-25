package br.ufpe.cin.tamarino.circuit.arduino;

import java.util.LinkedList;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;
import br.ufpe.cin.tamarino.arduinoGenerator.VarDeclaration;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.Delay;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.DigitalRead;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.DigitalWrite;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.Include;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.PinMode;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.servo.ServoAttach;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.servo.ServoDetach;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.servo.ServoRead;
import br.ufpe.cin.tamarino.arduinoGenerator.functions.servo.ServoWrite;
import br.ufpe.cin.tamarino.arduinoGenerator.structs.ForStruct;
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
	private LinkedList<Include> includes;
	private LinkedList<VarDeclaration> globals;
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
		ParserTamarino.getInstance().addAlias("servoAttach", ServoAttach.class);
		ParserTamarino.getInstance().addAlias("servoDetach", ServoDetach.class);
		ParserTamarino.getInstance().addAlias("servoRead", ServoRead.class);
		ParserTamarino.getInstance().addAlias("servoWrite", ServoWrite.class);
		
		ParserTamarino.getInstance().addAlias("include", Include.class);
		ParserTamarino.getInstance().addAlias("varDeclaration", VarDeclaration.class);
		ParserTamarino.getInstance().addAlias("forStruct", ForStruct.class);
		ParserTamarino.getInstance().addAlias("block", Block.class);
		
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
	 * @return the type
	 */
	public ArduinoType getType() {
		return type;
	}


	/**
	 * @return the includes
	 */
	public LinkedList<Include> getIncludes() {
		return includes;
	}


	/**
	 * @return the globals
	 */
	public LinkedList<VarDeclaration> getGlobals() {
		return globals;
	}	
}
