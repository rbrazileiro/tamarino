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
		String script = "else {\n";
		addTabs();
		for (AbstractScript item : block.getScriptList()) {			
			script += item.getScript();
		}
		remTabs();
		script += "}\n";
		
		this.setScript(script);
	}

}
