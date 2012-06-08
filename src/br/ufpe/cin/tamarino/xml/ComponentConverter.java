package br.ufpe.cin.tamarino.xml;

import br.ufpe.cin.tamarino.circuit.components.Component;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ComponentConverter implements Converter {

	@Override
	public boolean canConvert(Class arg0) {
		if(arg0==Component.class){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {		

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		return null;
	}

}
