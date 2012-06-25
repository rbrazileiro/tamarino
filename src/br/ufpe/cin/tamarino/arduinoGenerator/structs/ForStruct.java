package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;
import br.ufpe.cin.tamarino.arduinoGenerator.VarDeclaration;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

public class ForStruct extends AbstractScript {

	private VarDeclaration param1;
	private String param2;
	private String param3;
	private Block block;
	
	public ForStruct(){
		super();
	}
	
	public ForStruct(VarDeclaration param1, String param2, String param3, Block block){
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.block = block;
	}
	
	public VarDeclaration getParam1() {
		return param1;
	}

	public void setParam1(VarDeclaration param1) {
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
		script += "for ("+param1.toString().trim()+";"+param2+";"+param3+"){\n";
		for (AbstractScript item : block.getScriptList()) {
			addTabs();
			item.mountScript();
			script += "\t" + item.getScript();
		}
		script += "}\n";
		addTabs();
	}

}
