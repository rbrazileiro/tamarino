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
	 */
	public static void burn(File codeArduino,Arduino board) throws IOException{
		HexGenerator hex=new HexGenerator(codeArduino,board);
		hex.generate();		
	}
	
//	public static void main(String[] args){
//		try {
//			ArduinoBurner.burn(new File("arduinoCode.ino"),board);
//			System.out.println("acabou.");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
