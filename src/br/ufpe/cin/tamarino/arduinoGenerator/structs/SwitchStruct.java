package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;

public class SwitchStruct extends AbstractScript {

	private String condition;
	private Pair<String, Block>[] tuple;

	public SwitchStruct(){
		super();
	}
	
	public SwitchStruct(String condition, Pair<String,Block>[] tuple){
		super();
		this.condition = condition;
		this.tuple = tuple;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Pair<String, Block>[] getTuple() {
		return tuple;
	}

	public void setTuple(Pair<String, Block>[] tuple) {
		this.tuple = tuple;
	}

	@Override
	public void mountScript() {		
		addTabs();
		script = "switch ("+condition+"){\n";
		
		for (int i=1; i<tuple.length; i++){
			Pair<String, Block> tupleTmp = (Pair<String, Block>) tuple[i];
			addTabs();
			script += "\tcase "+tupleTmp.getValue0()+":\n";
			Block block = (Block) tupleTmp.getValue1();
			for (AbstractScript item : block.getScriptList()) {
				addTabs();
				script += "\t" + item.getScript();
			}
			
			addTabs();
			script += "\tbreak;\n";
		}
		addTabs();
		script += "}\n";

	}

}
