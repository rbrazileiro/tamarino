package br.ufpe.cin.tamarino.circuit.components;

import br.ufpe.cin.tamarino.xml.ConnectionConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * Represents a connection between 2 pins
 * @author Giovane Boaviagem
 * @since 30/05/2012
 *
 */
@XStreamAlias("connection")
public class Connection {
	@XStreamAlias("to")	
	@XStreamConverter(value=ConnectionConverter.class)
	private Pin to;
	@XStreamAlias("from")	
	@XStreamConverter(value=ConnectionConverter.class)
	private Pin from;
	
	public Connection(){}
	
	/**
	 * @param to
	 * @param from
	 */
	public Connection(Pin to, Pin from) {		
		this.to = to;
		this.from = from;
	}
	
	
	/**
	 * @return the to
	 */
	public Pin getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Pin to) {
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public Pin getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Pin from) {
		this.from = from;
	}	
}
