package br.ufpe.cin.tamarino.circuit.xml;

import br.ufpe.cin.tamarino.circuit.Circuit;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter for the Circuit class
 * @author Giovane Boaviagem
 * @since 30/05/2012
 *
 */
public class CircuitConverter implements Converter {

	@Override
	public boolean canConvert(Class arg0) {
		if(arg0.equals(Circuit.class)){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
