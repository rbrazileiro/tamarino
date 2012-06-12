package br.ufpe.cin.tamarino.fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractFunction;
import br.ufpe.cin.tamarino.arduinoGenerator.ArduinoCodeBuild;
import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.arduino.Arduino;
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
		
		this.circ=(Circuit) ParserTamarino.getInstance().toObject(new FileInputStream(arqXML));
	}
	
	/**
	 * Executes the system
	 */
	public void exec(){
		if(this.circ instanceof Arduino){				
			Arduino ard=(Arduino) this.circ;
			
			ArduinoCodeBuild acb=new ArduinoCodeBuild();
			
			//adicionando as funções do setup
			LinkedList<AbstractFunction> setup=ard.getSetup();
			for(int i=0;i<setup.size();i++){
				AbstractFunction af=setup.get(i);
				af.mountScript();
				acb.addSetupFunction(af);
			}
			
			//adicionando as funções do loop
			LinkedList<AbstractFunction> loop=ard.getLoop();
			for(int i=0;i<loop.size();i++){
				AbstractFunction af=loop.get(i);
				af.mountScript();
				acb.addLoopFunction(loop.get(i));
			}
			
			StringBuffer description=new StringBuffer(ard.getDescription());
			acb.processCode(description); 

			System.out.println("Arquivo criado com sucesso.");
		}		
	}
	
	
}
