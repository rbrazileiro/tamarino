package br.ufpe.cin.tamarino.arduinoGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javatuples.Triplet;

public class Global extends AbstractScript {

	private static Global instance;
	
	public static Global getInstance() {
		if (instance == null) instance = new Global();
		
		return instance;
	}

	private List<String> includes = new ArrayList<String>();
	// Triplet<Tipo do objeto, nome da variavel, valor inicial>
	private List<Triplet<String, String, String>> varDeclarations = new ArrayList<Triplet<String,String,String>>();
	
	public List<Triplet<String, String, String>> getVarDeclarations() {
		return varDeclarations;
	}

	public void setVarDeclarations(List<Triplet<String, String, String>> varDeclarations) {
		this.varDeclarations = varDeclarations;
	}
	
	public void addVarDeclarations(String type, String variable, String value){
		varDeclarations.add(new Triplet<String, String, String>(type, variable, value));
	}

	@Override
	public void mountScript() {
		script = "";
		for (String  item : includes) {
			script+= "#include <"+item+".h>\n";
		}
		script += "\n";
		
		for (Triplet<String,String,String> declaration : varDeclarations) {
			script+=declaration.getValue0()+ " " + declaration.getValue1() + " = "+declaration.getValue2()+";\n";
		}
		script += "\n";
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}
	
	public void addIncude(String include){
		this.includes.add(include);
	}

}
