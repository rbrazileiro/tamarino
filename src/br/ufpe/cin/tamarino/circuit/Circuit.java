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
	private long creation;
	private String description;
	
	/**
	 * Creates a new instance of <code>Circuit</code>
	 * @param name
	 * @param exportTo
	 */
	public Circuit(String name, String author,long creation,String description) {
		super();
		this.name = name;
		this.author=author;
		this.creation=creation;
		this.description=description;
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


	/**
	 * @return the creation
	 */
	public long getCreation() {
		return creation;
	}


	/**
	 * @param creation the creation to set
	 */
	public void setCreation(long creation) {
		this.creation = creation;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}	
	
}