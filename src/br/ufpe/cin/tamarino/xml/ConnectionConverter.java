package br.ufpe.cin.tamarino.xml;

import br.ufpe.cin.tamarino.circuit.components.Pin;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Connection converter
 * @author Giovane Boaviagem
 * @since 03/06/2012
 *
 */
public class ConnectionConverter implements Converter{

	@Override
	public boolean canConvert(Class arg0) {		
		return true;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		Pin p=(Pin) arg0;
		
		arg1.addAttribute("labelPin", p.getLabel());
		arg1.addAttribute("labelComponent", p.getLabelComponent());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		Pin p=new Pin(arg0.getAttribute("labelComponent"));
		p.setLabel(arg0.getAttribute("labelPin"));
		
		return p;
	}
	
}	
