package br.ufpe.cin.tamarino.fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.Connection;
import br.ufpe.cin.tamarino.circuit.components.Pin;
import br.ufpe.cin.tamarino.xml.ParserTamarino;
import br.ufpe.cin.tamarino.xml.arduino.Arduino;

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
			
			System.out.println("Nome: "+ard.getName());
			System.out.println("Exportar para: "+ard.getExportTo());
			System.out.println("Tipo da placa: "+ard.getType());
		}
	}
	
	
}
