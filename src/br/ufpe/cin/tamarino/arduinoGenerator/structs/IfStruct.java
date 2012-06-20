package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;

public class IfStruct extends AbstractScript {

	private String condition;
	private Block block;


	public IfStruct(){
		super();
	}
	
	public IfStruct(String condition, Block block){
		super();
		this.condition = condition;
		this.block = block;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void mountScript() {
		addTabs();
		script = "if ("+condition+"){\n";
		for (AbstractScript item : block.getScriptList()) {
			addTabs();
			script += "\t" + item.getScript();
		}
		addTabs();
		script += "}\n";

	}

}
