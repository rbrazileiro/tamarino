package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;

public class Include extends AbstractScript {
	private String libName;	

	@Override
	public void mountScript() {
		this.setScript("#include <"+this.libName+".h>\n");		
	}

	/**
	 * @param libname
	 */
	public Include(String libname) {
		super();
		this.libName = libname;
	}
	
	public Include(){}

	/**
	 * @return the libname
	 */
	public String getLibname() {
		return libName;
	}

	/**
	 * @param libname the libname to set
	 */
	public void setLibname(String libname) {
		this.libName = libname;
	}
}
