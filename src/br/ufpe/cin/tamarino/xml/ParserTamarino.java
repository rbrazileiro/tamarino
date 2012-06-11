package br.ufpe.cin.tamarino.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.arduino.Arduino;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Classe responsavel pelo parser xml do arquivo de entrada do sistema
 * @author Giovane Boaviagem
 * @since 29/05/2012
 */
public class ParserTamarino {
	private static ParserTamarino instance=null;
	private XStream xstream;
	
	private ParserTamarino(){
		this.xstream=new XStream(new DomDriver());
		this.xstream.autodetectAnnotations(true); //detecta as anotacoes automaticamente		
		this.initAliases();		
	}
	
	/**
	 * 
	 */
	private void initAliases(){
		this.xstream.alias("arduino", Arduino.class);
	}
	
	/**
	 * 
	 * @return A instância única de <code>Parser</code>
	 */
	public static ParserTamarino getInstance(){
		if(instance==null){
			instance=new ParserTamarino();
		}
		
		return instance;
	}
	
	/**
	 * Converte um arquivo xml para um objeto qualquer
	 * @param xmlFile Arquivo a ser convertido
	 * @return Objeto resultante da conversao xml.
	 */
	public Object toObject(InputStream xmlFile){
		return this.xstream.fromXML(xmlFile);
	}
	
	/**
	 * Converts a object in a String XML.
	 * @param arg0 Object to be converted
	 * @return A object's xml string
	 */
	public String toStringXML(Object arg0){
		return this.xstream.toXML(arg0);
	}
	
	/**
	 * 
	 * @param arg0
	 * @param pathFile
	 * @throws IOException
	 */
	public void toXMLFile(Object arg0,String pathFile) throws IOException {
		FileWriter fw=new FileWriter(new File(pathFile));
		this.xstream.toXML(arg0, fw);
	}
	
	/**
	 * Adds a new alias to the xstream object
	 * @param name
	 * @param type
	 */
	public void addAlias(String name,Class<?> type){
		this.xstream.alias(name, type);
	}
}
