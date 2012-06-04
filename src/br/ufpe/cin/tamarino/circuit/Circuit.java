package br.ufpe.cin.tamarino.circuit;

import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.Connection;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Classe que representa um circuito
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
@XStreamAlias("circuit")
public class Circuit {
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("components")
	//@XStreamConverter(value=ComponentConverter.class)
	private LinkedList<Component> components;
	@XStreamAlias("connections")
	private LinkedList<Connection> connections;
	
	
	/**
	 * @param name
	 * @param componentes
	 */
	public Circuit(String name, LinkedList<Component> componentes) {
		super();
		this.name = name;
		this.components = componentes;		
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
	 * @return the components
	 */
	public LinkedList<Component> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(LinkedList<Component> components) {
		this.components = components;
	}

	/**
	 * @return the connections
	 */
	public LinkedList<Connection> getConnections() {
		return connections;
	}

	/**
	 * @param connections the connections to set
	 */
	public void setConnections(LinkedList<Connection> connections) {
		this.connections = connections;
	}	
}