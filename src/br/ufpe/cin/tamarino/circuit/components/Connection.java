package br.ufpe.cin.tamarino.circuit.components;

/**
 * Represents a connection between 2 pins
 * @author Giovane Boaviagem
 * @since 30/05/2012
 *
 */
public class Connection {
	private int idComponentFrom;
	private int idPinFrom;	
	private int labelComponentTo;
	private int idPinTo;
	/**
	 * @param idComponentFrom
	 * @param idPinFrom
	 * @param labelComponentTo
	 * @param idPinTo
	 */
	public Connection(int idComponentFrom, int idPinFrom, int labelComponentTo,
			int idPinTo) {
		super();
		this.idComponentFrom = idComponentFrom;
		this.idPinFrom = idPinFrom;
		this.labelComponentTo = labelComponentTo;
		this.idPinTo = idPinTo;
	}
	/**
	 * @return the idComponentFrom
	 */
	public int getIdComponentFrom() {
		return idComponentFrom;
	}
	/**
	 * @param idComponentFrom the idComponentFrom to set
	 */
	public void setIdComponentFrom(int idComponentFrom) {
		this.idComponentFrom = idComponentFrom;
	}
	/**
	 * @return the idPinFrom
	 */
	public int getIdPinFrom() {
		return idPinFrom;
	}
	/**
	 * @param idPinFrom the idPinFrom to set
	 */
	public void setIdPinFrom(int idPinFrom) {
		this.idPinFrom = idPinFrom;
	}
	/**
	 * @return the labelComponentTo
	 */
	public int getLabelComponentTo() {
		return labelComponentTo;
	}
	/**
	 * @param labelComponentTo the labelComponentTo to set
	 */
	public void setLabelComponentTo(int labelComponentTo) {
		this.labelComponentTo = labelComponentTo;
	}
	/**
	 * @return the idPinTo
	 */
	public int getIdPinTo() {
		return idPinTo;
	}
	/**
	 * @param idPinTo the idPinTo to set
	 */
	public void setIdPinTo(int idPinTo) {
		this.idPinTo = idPinTo;
	}	
}
