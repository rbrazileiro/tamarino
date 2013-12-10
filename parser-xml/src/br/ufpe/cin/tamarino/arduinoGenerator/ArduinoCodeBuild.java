package br.ufpe.cin.tamarino.arduinoGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.tamarino.arduinoGenerator.functions.Include;
import br.ufpe.cin.tamarino.conf.Conf;
import br.ufpe.cin.tamarino.conf.Conf.ConfKeys;

public class ArduinoCodeBuild {
	
	private static int count=0;

	private StringBuffer code;
	private File finalFile;	
	
	private List<AbstractScript> includeFluxogram;
	private List<AbstractScript> varDeclarationFluxogram;
	private List<AbstractScript> setupFluxogram;
	private List<AbstractScript> loopFluxogram;
	
	public ArduinoCodeBuild(){		
		code=new StringBuffer("");
		setupFluxogram = new ArrayList<AbstractScript>();
		loopFluxogram = new ArrayList<AbstractScript>();
		includeFluxogram=new ArrayList<AbstractScript>();
		varDeclarationFluxogram=new ArrayList<AbstractScript>();		
	}
	
	public void addInclude(Include inc){
		includeFluxogram.add(inc);
	}
	
	public void addVarDeclaration(VarDeclaration var){
		varDeclarationFluxogram.add(var);
	}
	
	
	
	public void addSetupFunction(AbstractScript function){
		setupFluxogram.add(setupFluxogram.size(), function);
	}
	
	public void removeSetupFunction(AbstractScript function){
		setupFluxogram.remove(function);
	}
	
	public void addLoopFunction(AbstractScript function){
		loopFluxogram.add(loopFluxogram.size(), function);
	}
	
	public void removeLoopFunction(AbstractScript function){
		loopFluxogram.remove(function);
	}
	
	public File getFinalFile(){
		return this.finalFile;
	}
	
	public boolean processCode(StringBuffer header){
		boolean result = false;
		
		// Montagem do código
		
		//CABECALHO E DESCRICAO
		addCodeHeader(header);
		
		//INCLUDES
		for(AbstractScript inc : includeFluxogram){
			inc.mountScript();
			code.append(inc.getScript());
		}
		
		code.append("\n");
		
		//VARIAVEIS GLOBAIS
		for(AbstractScript global:varDeclarationFluxogram){
			global.mountScript();
			code.append(global.getScript());
		}
		
		code.append("\n");
		
		// função SETUP		
		code.append("void setup(){\n");
		AbstractScript.addTabs();
		for (AbstractScript function : setupFluxogram) {
			function.mountScript();
			code.append(function.getScript());
		}
		AbstractScript.remTabs();
		
		code.append("}\n\n");
		
		// função LOOP
		
		
		code.append("void loop(){\n");
		AbstractScript.addTabs();
		for (AbstractScript lFunction : loopFluxogram) {
			lFunction.mountScript();
			code.append(lFunction.getScript());
		}
		AbstractScript.remTabs();
		code.append("}\n");
		
		// Fim da montagem do código
		
		// Salvar o código em um arquivo .INO
		result = saveCode();
		
		return result;
	}
	
	private void addCodeHeader(StringBuffer buffer) {
		code.append("/** \n"+buffer.toString()+" \n */\n\n");
	}

	private boolean saveCode(){
		boolean result = false;
		finalFile = new File(Conf.getInstance().getProperty(ConfKeys.PATH_TEMP)+"/arduinoCode"+count+".ino");
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(finalFile));
			br.write(code.toString());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		return result;
	}
}
