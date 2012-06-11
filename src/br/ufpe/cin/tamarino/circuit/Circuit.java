package br.ufpe.cin.tamarino.circuit;

/**
 * Represents a circuit.  
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Circuit {	
	private String name;
	private String author;	
	
	/**
	 * Creates a new instance of <code>Circuit</code>
	 * @param name
	 * @param exportTo
	 */
	public Circuit(String name, String author) {
		super();
		this.name = name;
		this.author=author;		
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}


	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}