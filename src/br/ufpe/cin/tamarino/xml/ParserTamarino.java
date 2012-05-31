package br.ufpe.cin.tamarino.xml;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
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
	 * Adiciona um alias ao driver xstream
	 * @param aliasName nome do alias
	 * @param clazz Nome da classe que eh representada pelo alias
	 */
	public void addAlias(String aliasName,Class<?> clazz){
		this.xstream.alias(aliasName, clazz);
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
	 * Adds a specified converter
	 * @param converter The converter to add.
	 */
	public void addConverter(Converter converter){
		this.xstream.registerConverter(converter);
	}
}
