package br.ufpe.cin.tamarino.circuit;

import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

/**
 * Classe que representa um circuito
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Circuit {
	private String name;
	private LinkedList<Component> componentes;
	
	/**
	 * @param name
	 * @param componentes
	 */
	public Circuit(String name, LinkedList<Component> componentes) {
		super();
		this.name = name;
		this.componentes = componentes;		
	}
	
	public Circuit(){}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the componentes
	 */
	public LinkedList<Component> getComponentes() {
		return componentes;
	}

	/**
	 * @param componentes the componentes to set
	 */
	public void setComponentes(LinkedList<Component> componentes) {
		this.componentes = componentes;
	}
}