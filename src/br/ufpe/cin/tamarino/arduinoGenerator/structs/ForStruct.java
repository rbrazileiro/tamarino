package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;

public class ForStruct extends AbstractScript {

	private String param1;
	private String param2;
	private String param3;
	private Block block;

	public ForStruct(){
		super();
	}
	
	public ForStruct(String param1, String param2, String param3, Block block){
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.block = block;
	}
	
	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
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
		script += "for ("+param1+","+param2+","+param3+"){\n";
		for (AbstractScript item : block.getScriptList()) {
			addTabs();
			script += "\t" + item.getScript();
		}
		addTabs();
		script += "}\n";
	}

}
