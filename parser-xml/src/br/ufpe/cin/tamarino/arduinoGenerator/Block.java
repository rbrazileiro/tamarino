package br.ufpe.cin.tamarino.arduinoGenerator;

import java.util.ArrayList;
import java.util.List;

public class Block {
	
	private List<AbstractScript> scripts;
	
	public Block(){
		scripts = new ArrayList<AbstractScript>();
	}
	
	public void addScript(AbstractScript script){
		scripts.add(script);
	}
	
	public boolean removeScript(AbstractScript script){
		return scripts.remove(script);
	}
	
	public List<AbstractScript> getScriptList(){
		return scripts;
	}

}
