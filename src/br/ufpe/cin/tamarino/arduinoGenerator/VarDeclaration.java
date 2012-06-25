package br.ufpe.cin.tamarino.arduinoGenerator;

public class VarDeclaration extends AbstractScript {
	
	private String typeVar;
	private String varName;
	private String value;

	/**
	 * @param type
	 * @param varName
	 */
	public VarDeclaration(String type, String varName, String value) {
		super();
		this.typeVar = type;
		this.varName = varName;
		this.value=value;
	}
	
	public VarDeclaration(){}

	@Override
	public void mountScript() {
		if(value==null|| value.equalsIgnoreCase("")){
			script = this.typeVar +" "+this.varName+";\n";
		}else{
			script = this.typeVar +" "+this.varName+" = "+this.value+";\n";
		}		
	}
	
	@Override
	public String toString(){
		if(script==null){
			this.mountScript();
		}
		
		return script.substring(0, script.length()-2); //elimina o ";" final			
	}

	/**
	 * @return the type
	 */
	public String getTypeVar() {
		return typeVar;
	}

	/**
	 * @param type the type to set
	 */
	public void setTypeVar(String typeVar) {
		this.typeVar = typeVar;
	}

	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
