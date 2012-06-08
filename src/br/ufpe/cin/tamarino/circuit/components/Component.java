package br.ufpe.cin.tamarino.circuit.components;

import java.util.HashMap;

import br.ufpe.cin.tamarino.circuit.ComponentType;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Represents a component of a circuit
 * @author Giovane Boaviagem Ribeiro
 * @since 30/05/2012
 * TODO Montar estrutura do arquivo de configurações que carregará:
 * - Pinagem
 * - estado do componente
 * - Outros atributos específicos de cada componente.
 */
public abstract class Component {
	@XStreamAsAttribute
	private String label;
	private ComponentType type;
	private HashMap<ComponentProperty,Object> properties;
	
	public Component(){
		this.label="";
		this.properties=this.initProperties();
		this.type=null;
	}
	
	/**
	 * Creates a new instance of <code>Component</code>
	 * with a specified label.
	 * @param label Label of the component
	 */
	public Component(String label){	
		this.label=label;
		this.properties=new HashMap<ComponentProperty, Object>();
		this.type=null;
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
	 * @return the type
	 */
	public ComponentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ComponentType type) {
		this.type = type;
	}

	/**
	 * @return the properties
	 */
	public HashMap<ComponentProperty, Object> getProperties() {
		return properties;
	}

	/**
	 * 
	 * @return
	 */
	public abstract HashMap<ComponentProperty,Object> initProperties();
}
