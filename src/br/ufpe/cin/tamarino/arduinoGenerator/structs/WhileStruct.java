package br.ufpe.cin.tamarino.arduinoGenerator.structs;

import java.util.List;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Block;

/**
 * 
 * @author Giovane Boaviagem
 * @since 26/06/2012
 *
 */
public class WhileStruct extends AbstractScript{
	private String param;
	private Block block;

	@Override
	public void mountScript() {
		StringBuffer script=new StringBuffer("");
		script.append("while("+param+"){\n");
		addTabs();
		List<AbstractScript> lista=block.getScriptList();
		for (AbstractScript item:lista) {
			item.mountScript();
			script.append(item.getScript());
		}
		remTabs();
		script.append("}\n");
		this.setScript(script.toString());
	}

}
