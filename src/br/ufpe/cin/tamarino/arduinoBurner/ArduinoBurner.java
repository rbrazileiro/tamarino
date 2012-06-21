package br.ufpe.cin.tamarino.arduinoBurner;

import java.io.File;
import java.io.IOException;

import br.ufpe.cin.tamarino.circuit.arduino.Arduino;

/**
 * 
 * @author Giovane Boaviagem
 * @since 20/06/2012
 *
 */
public class ArduinoBurner {

	
	/**
	 * 
	 * @param codeArduino
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void burn(File codeArduino,Arduino board) throws IOException, InterruptedException{
		HexGenerator hex=new HexGenerator(codeArduino,board);
		hex.generate();		
	}
}
