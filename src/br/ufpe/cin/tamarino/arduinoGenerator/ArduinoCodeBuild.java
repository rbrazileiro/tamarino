package br.ufpe.cin.tamarino.arduinoGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.tamarino.conf.Conf;
import br.ufpe.cin.tamarino.conf.Conf.ConfKeys;

public class ArduinoCodeBuild {

	private String code;
	private File finalFile;	
	
	private List<AbstractScript> setupFluxogram;
	private List<AbstractScript> loopFluxogram;
	
	public ArduinoCodeBuild(){		
		setupFluxogram = new ArrayList<AbstractScript>();
		loopFluxogram = new ArrayList<AbstractScript>();
		finalFile = new File(Conf.getInstance().getProperty(ConfKeys.PATH_TEMP)+"/arduinoCode.ino");
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
		addCodeHeader(header);
		// função SETUP
		code += "void setup(){\n";
		for (AbstractScript function : setupFluxogram) {
			code += "\t"+function.script;
		} 
		
		code += "}\n\n";
		
		// função LOOP
		
		
		code += "void loop(){\n";
		for (AbstractScript lFunction : loopFluxogram) {
			code += "\t"+lFunction.script;
		}
		
		code += "}\n";
		
		// Fim da montagem do código
		
		// Salvar o código em um arquivo .INO
		result = saveCode();
		
		return result;
	}
	
	private void addCodeHeader(StringBuffer buffer) {
		code ="/* \n"+buffer.toString()+" \n*/\n\n";
	}

	private boolean saveCode(){
		boolean result = false;
		
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(finalFile));
			br.write(code);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
