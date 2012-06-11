package br.ufpe.cin.tamarino.circuit.arduino;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.xml.ParserTamarino;



/**
 * Represents a arduino board
 * @author Giovane Boaviagem
 * @since 06/06/2012
 *
 */
public class Arduino extends Circuit{	
	
	static{
		ParserTamarino.getInstance().addAlias("arduino", Arduino.class);
	}
	
	
	/**
	 * @param type Type of the arduino board (see {@link ArduinoType})
	 * @param inputs List of the input components connected to the arduino
	 * @param outputs List of the output components connected to the arduino 
	 */
	public Arduino(String name,String author) {
		super(name, author);
	}
}
