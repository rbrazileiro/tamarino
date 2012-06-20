package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;

public class ElseStruct extends AbstractScript {


	private Block block;

	public ElseStruct(){
		super();
	}
	
	public ElseStruct(Block block){
		super();
		this.block = block;
		mountScript();
	}
	
	public Block getBlock(){
		return block;
	}
	
	public void setBlock(Block block){
		this.block = block;
	}
	
	@Override
	public void mountScript() {
		addTabs();
		script += "else {\n";
		for (AbstractScript item : block.getScriptList()) {
			addTabs();
			script += "\t" + item.getScript();
		}
		addTabs();
		script += "}\n";

	}

}
