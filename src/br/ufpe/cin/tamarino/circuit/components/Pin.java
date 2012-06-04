package br.ufpe.cin.tamarino.circuit.components;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;



/**
 * Representa um pino de um componente
 * @author Giovane Boaviagem
 * @since 25/05/2012 
 */
@XStreamAlias("pin")
public class Pin {
	@XStreamAsAttribute //setando como os atributos da tag "pin"
	private String label;
	@XStreamAsAttribute
	private String labelComponent;	
	
	private static int count=0;
	
	/**
	 * Creates a new instance of <code>Pin</code> without a specific label.
	 * @param idComponent
	 */
	public Pin(String labelComponent){		
		this.labelComponent=labelComponent;	
		this.label="p"+count;
		count++;	
	}
	
	/**
	 * 
	 * @param labelComponent
	 * @param label
	 */
	public Pin(String labelComponent, String label){		
		this.labelComponent=labelComponent;
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
}
