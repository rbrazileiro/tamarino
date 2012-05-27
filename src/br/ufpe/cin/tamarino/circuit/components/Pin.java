package br.ufpe.cin.tamarino.circuit.components;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Representa um pino de um componente
 * @author Giovane Boaviagem
 * @since 25/05/2012
 * TODO Colocar as restricoes referentes a tensao do pino.
 */
public class Pin {
	private String labelComponent;
	private String label;
	private float value;
	
	/*
	 * Mapeia o Nome do componente que contem 
	 * o pino de destino e o pino propriamente dito.
	 */
	private HashMap<String,Pin> connections;
	
	/**
	 * @param label
	 * @param level
	 * @param maxVolt 
	 */
	public Pin(String labelComponent, String label) {		
		this.label = label;
		this.value=0;
		this.connections=new HashMap<String,Pin>();
		this.labelComponent = labelComponent;
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
	 * @return the labelComponent
	 */
	public String getLabelComponent() {
		return labelComponent;
	}

	/**
	 * @param labelComponent the labelComponent to set
	 */
	public void setLabelComponent(String labelComponent) {
		this.labelComponent = labelComponent;
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 * Quando um valor muda, TODOS os pinos conectados a este deverao ter o mesmo valor.
	 */
	public void setValue(float value) {
		this.value = value;
		
		Iterator<String> keySet=this.connections.keySet().iterator();
		while(keySet.hasNext()){
			String key=keySet.next();
			Pin pin=this.connections.get(key);
			pin.setValue(value);
			this.connections.put(key, pin);
		}
	}
	
	/**
	 * Conecta este pino ao pino passado como parametro.
	 * A conexao eh estabelecida armazenando o pino passado
	 * como parametro no hashmap. 
	 * @param dest
	 */
	public void addConnection(Pin dest){
		this.connections.put(dest.getLabelComponent()+"."+dest.getLabel(), dest);
	}
	
	/**
	 * Elimina a conexao com o pino passado como parametro.
	 * @param dest
	 */
	public void deleteConnection(Pin dest){
		this.connections.remove(dest.getLabelComponent()+"."+dest.getLabel());
	}
}
