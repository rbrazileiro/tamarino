package br.cin.ufpe.tamarino.core.components;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Super classe de componentes suportados pelo sistema
 * @author gbr2
 *
 */
public class Component {
	private String label;
	private HashMap<String,Pin> pinagem;	
	
	
	public Component(){}

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
	 * @return the pinagem
	 */
	public HashMap<String,Pin> getPinagem() {
		return pinagem;
	}

	/**
	 * @param pinagem the pinagem to set
	 */
	public void setPinagem(HashMap<String,Pin> pinagem) {
		this.pinagem = pinagem;
	}	
}
