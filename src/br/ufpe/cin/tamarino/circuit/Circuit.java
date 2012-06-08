package br.ufpe.cin.tamarino.circuit;

import java.util.LinkedList;



/**
 * Represents a circuit.  
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Circuit {	
	private String name;
	private CircuitExport exportTo;
	private LinkedList<Input> inputs;
	private LinkedList<Output> outputs;
	
	/**
	 * Creates a new instance of <code>Circuit</code>
	 * @param name
	 * @param exportTo
	 */
	public Circuit(String name, CircuitExport exportTo,LinkedList<Input> inputs,LinkedList<Output> outputs) {
		super();
		this.name = name;
		this.exportTo = exportTo;
		this.inputs=inputs;
		this.outputs=outputs;
	}


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
	 * @return the exportTo
	 */
	public CircuitExport getExportTo() {
		return exportTo;
	}

	/**
	 * @param exportTo the exportTo to set
	 */
	public void setExportTo(CircuitExport exportTo) {
		this.exportTo = exportTo;
	}
	
	/**
	 * @return the inputs
	 */
	public LinkedList<Input> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(LinkedList<Input> inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return the outputs
	 */
	public LinkedList<Output> getOutputs() {
		return outputs;
	}

	/**
	 * @param outputs the outputs to set
	 */
	public void setOutputs(LinkedList<Output> outputs) {
		this.outputs = outputs;
	}		
}