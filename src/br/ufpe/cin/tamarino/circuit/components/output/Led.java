package br.ufpe.cin.tamarino.circuit.components.output;

import java.util.HashMap;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.ComponentType;
import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.ComponentProperty;
import br.ufpe.cin.tamarino.circuit.components.Pin;

/**
 * 
 * @author Giovane Boaviagem
 * @since 30/05/2012
 *
 */
public class Led extends Component{
	
	private static int count;
	
	public Led(){
		super("led"+count);
		count++;
	}
	
	public Led(String label){
		super(label);	
	}

	//@Override
	protected LinkedList<Pin> setPins() {
		Pin p0=new Pin("p0");
		Pin p1=new Pin("p1");
		LinkedList<Pin> listPins=new LinkedList<Pin>();
		listPins.add(0, p0);
		listPins.add(1, p1);
		
		return listPins;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.LED;
	}

	@Override
	public HashMap<ComponentProperty, Object> initProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}
