package br.ufpe.cin.tamarino.circuit.components;


/**
 * Representa um pino de um componente
 * @author Giovane Boaviagem
 * @since 25/05/2012 
 */
public class Pin {	
	private String label;
	private float value;
	
	private static int count=0;
	
	/**
	 * Creates a new instance of <code>Pin</code> without
	 * @param idComponent
	 */
	public Pin(){
		this.value=0;
		this.label="p"+count;
		count++;
	}
	
	public Pin(String label){
		this.value=0;
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
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;	
	}	
}
