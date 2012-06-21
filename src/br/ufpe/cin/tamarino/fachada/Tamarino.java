package br.ufpe.cin.tamarino.fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.ufpe.cin.tamarino.arduinoBurner.ArduinoBurner;
import br.ufpe.cin.tamarino.arduinoGenerator.ArduinoGenerator;
import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.arduino.Arduino;
import br.ufpe.cin.tamarino.conf.Conf;
import br.ufpe.cin.tamarino.conf.Conf.ConfKeys;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

/**
 * Fachada do sistema.
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Tamarino {
	
	private Circuit circ;

	
	/**
	 * Creates a new instance of <code>Tamarino</code>
	 * @param arqXML
	 * @throws FileNotFoundException
	 */
	public Tamarino(File arqXML) throws FileNotFoundException{
		if(arqXML==null){
			throw new NullPointerException("XML file can't be null!!");
		}
		
		//se a pasta temporaria nao existir, crie-a
		File tempFolder=new File(Conf.getInstance().getProperty(ConfKeys.PATH_TEMP));
		if(!tempFolder.exists()){
			tempFolder.mkdirs();
		}
		
		File[] list=tempFolder.listFiles();
		if(list.length>0){
			for (int i = 0; i < list.length; i++) {
				list[i].delete();
			}
		}
		
		this.circ=(Circuit) ParserTamarino.getInstance().toObject(new FileInputStream(arqXML));
	}
	
	/**
	 * Executes the system
	 */
	public void exec(){
		try {
			if(this.circ instanceof Arduino){
				Arduino ard=(Arduino) this.circ;
				
				System.out.println("Criando arquivo correspondente ao xml gerado.");
				File fileIno=ArduinoGenerator.generate(ard);

				System.out.println("Gravando o arquivo na placa.");				
				ArduinoBurner.burn(fileIno, ard);				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
}
