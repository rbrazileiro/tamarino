package br.ufpe.cin.tamarino.circuit.components.input;

import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.Pin;

/**
 * 
 * @author Giovane Boaviagem
 * @since 30/05/2012
 *
 */
public class Button extends Component{
	
	private static int count=0;
	
	public Button(){
		super("button"+count);
		count++;
	}

	public Button(String label) {
		super(label);		
	}

	@Override
	protected LinkedList<Pin> setPins() {
		Pin p0=new Pin();
		Pin p1=new Pin();
		LinkedList<Pin> listPins=new LinkedList<Pin>();
		listPins.add(0, p0);
		listPins.add(1, p1);
		
		return listPins;
	}
}
