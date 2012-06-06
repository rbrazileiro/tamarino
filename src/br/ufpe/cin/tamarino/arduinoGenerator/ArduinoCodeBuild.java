package br.ufpe.cin.tamarino.arduinoGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArduinoCodeBuild {

	private String code;
	private File finalFile;
	private String filePath;
	
	private List<AbstractFunction> setupFluxogram;
	private List<AbstractFunction> loopFluxogram;
	
	public ArduinoCodeBuild(String filePath){
		this.filePath = filePath;
	}
	
	public void addSetupFunction(AbstractFunction function){
		setupFluxogram.add(setupFluxogram.size(), function);
	}
	
	public void removeSetupFunction(AbstractFunction function){
		setupFluxogram.remove(function);
	}
	
	public void addLoopFunction(AbstractFunction function){
		loopFluxogram.add(loopFluxogram.size(), function);
	}
	
	public void removeLoopFunction(AbstractFunction function){
		loopFluxogram.remove(function);
	}
	
	public boolean processCode(){
		boolean result = false;
		
		// Montagem do código
		
		// função SETUP
		code = "void setup(){\n";
		for (AbstractFunction function : setupFluxogram) {
			code += function.script;
		} 
		
		code += "}\n\n";
		
		// função LOOP
		
		code += "void loop(){\n";
		for (AbstractFunction lFunction : loopFluxogram) {
			code += lFunction.script;
		}
		
		code += "}\n";
		
		// Fim da montagem do código
		
		// Salvar o código em um arquivo .INO
		result = saveCode();
		
		return result;
	}
	
	private boolean saveCode(){
		boolean result = false;
		
		if (finalFile == null || !finalFile.exists()) {
			finalFile = new File(filePath+"\\arduinoCode.ino");
		}
		
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
