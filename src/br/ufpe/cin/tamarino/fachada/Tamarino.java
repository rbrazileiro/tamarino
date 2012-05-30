package br.ufpe.cin.tamarino.fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

/**
 * Fachada do sistema.
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Tamarino {
	
	private Circuit circ;
	
	
	public Tamarino(File arqXML) throws FileNotFoundException{
		if(arqXML==null){
			throw new NullPointerException("XML file can't be null!!");
		}
		
		//adicionando os aliases para o xstream
		initAliases();
		
		FileInputStream fis=new FileInputStream(arqXML);		
		this.circ=(Circuit) ParserTamarino.getInstance().toObject(fis);		
	}
	
	/*
	 * Adiciona os aliases 
	 */
	private void initAliases(){
		ParserTamarino p=ParserTamarino.getInstance();
		
		//adicionando os aliases referentes a classe Circuit
		p.addAlias("circuit", Circuit.class);
		p.addAlias("components", LinkedList.class);
		p.addAlias("name", String.class);
	}
	
	public void testFile(){
		System.out.println("Nome do circuito: "+this.circ.getName());
		Iterator<Component> it=this.circ.getComponentes().iterator();		
		while(it.hasNext()){
//			Component c=it.next();			
		}
	}
	
	
}
