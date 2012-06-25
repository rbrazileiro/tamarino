package br.ufpe.cin.tamarino.arduinoGenerator;

import java.io.File;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.arduinoGenerator.functions.Include;
import br.ufpe.cin.tamarino.circuit.arduino.Arduino;

/**
 * 
 * @author Giovane Boaviagem
 * @since 21/06/2012
 *
 */
public class ArduinoGenerator {
	/**
	 * Generates a .ino file
	 * @param ard
	 */
	public static File generate(Arduino ard){
		ArduinoCodeBuild acb=new ArduinoCodeBuild();
		
		LinkedList<Include> includes=ard.getIncludes();
		for(int i=0;i<includes.size();i++){
			Include af=includes.get(i);			
			acb.addInclude(af);						
		}
		
		LinkedList<VarDeclaration> varDeclarations=ard.getGlobals();
		for(int i=0;i<varDeclarations.size();i++){
			VarDeclaration af=varDeclarations.get(i);			
			acb.addVarDeclaration(af);
									
		}
		
		//adicionando as funções do setup
		LinkedList<AbstractScript> setup=ard.getSetup();
		for(int i=0;i<setup.size();i++){
			AbstractScript af=setup.get(i);			
			acb.addSetupFunction(af);
		}
		
		//adicionando as funções do loop
		LinkedList<AbstractScript> loop=ard.getLoop();
		for(int i=0;i<loop.size();i++){						
			acb.addLoopFunction(loop.get(i));
		}
		
		StringBuffer description=new StringBuffer(ard.getDescription());
		acb.processCode(description);
		
		return acb.getFinalFile();
	}

}
