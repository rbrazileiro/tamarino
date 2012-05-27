/**
 * 
 */
package br.ufpe.cin.tamarino.circuit.components.input;

import java.util.HashMap;

import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.Pin;

/**
 * @author giovane
 */
public class Button extends Component {
	private boolean state;

	/**
	 * @param label
	 * @param pinagem
	 */
	public Button(String label, boolean state) {		
		super();
		
		this.state=false;
		
		this.setLabel(label);
		Pin p0=new Pin(label,"p0");
		Pin p1=new Pin(label, "p1");
		HashMap<String,Pin> pinagem=new HashMap<String,Pin>();
		pinagem.put(p0.getLabel(), p0);
		pinagem.put(p1.getLabel(), p1);
		this.setPinagem(pinagem);
	}
	
//	/**
//	 * 
//	 */
//	public void changeState(){
//		this.state=!this.state;
//		
//		if(this.state){
//			Pin p1=this.getPinagem().get("p1");
//			p1.setLevel(this.getPinagem().get("p0").isLevel());
//			this.getPinagem().put("p1", p1);
//		}else{
//			Pin p1=this.getPinagem().get("p1");
//			p1.setLevel(false);
//			this.getPinagem().put("p1", p1);
//		}
//	}
}
