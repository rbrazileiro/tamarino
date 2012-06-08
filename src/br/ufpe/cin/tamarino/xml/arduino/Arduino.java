package br.ufpe.cin.tamarino.xml.arduino;

import java.util.LinkedList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.CircuitExport;
import br.ufpe.cin.tamarino.circuit.Input;
import br.ufpe.cin.tamarino.circuit.Output;
import br.ufpe.cin.tamarino.xml.ParserTamarino;



/**
 * Represents a arduino board
 * @author Giovane Boaviagem
 * @since 06/06/2012
 *
 */
public class Arduino extends Circuit{
	private ArduinoType type;
	
	static{
		ParserTamarino.getInstance().addAlias("arduino", Arduino.class);
	}
	
	
	/**
	 * @param type Type of the arduino board (see {@link ArduinoType})
	 * @param inputs List of the input components connected to the arduino
	 * @param outputs List of the output components connected to the arduino 
	 */
	public Arduino(String name,CircuitExport exportTo,ArduinoType type, LinkedList<Input> inputs,
			LinkedList<Output> outputs) {
		super(name,exportTo,inputs,outputs);
		this.type = type;
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
