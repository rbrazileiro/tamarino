package br.ufpe.cin.tamarino.circuit.components;

import java.util.LinkedList;

/**
 * Represents a component of a circuit
 * @author Giovane Boaviagem Ribeiro
 * @since 30/05/2012
 */
public abstract class Component {
	private String label;	
	private LinkedList<Pin> listPins;
	
	public Component(){
		this.listPins=this.setPins();	
	}
	
	/**
	 * Creates a new instance of <code>Component</code>
	 * with a specified label.
	 * @param label Label of the component
	 */
	public Component(String label){		
		this();
		this.label=label;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the listPins
	 */
	public LinkedList<Pin> getListPins() {
		return listPins;
	}

	/**
	 * @param listPins the listPins to set
	 */
	public void setListPins(LinkedList<Pin> listPins) {
		this.listPins = listPins;
	}
	
	/**
	 * Estabelece a pinagem do componente
	 */
	protected abstract LinkedList<Pin> setPins();
}
