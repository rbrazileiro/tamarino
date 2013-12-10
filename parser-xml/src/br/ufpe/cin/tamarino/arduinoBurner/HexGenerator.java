package br.ufpe.cin.tamarino.arduinoBurner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.arduino.Arduino;
import br.ufpe.cin.tamarino.conf.Conf;
import br.ufpe.cin.tamarino.conf.Conf.ConfKeys;
import br.ufpe.cin.tamarino.util.Exec;
import br.ufpe.cin.tamarino.util.FileHelper;

/**
 * Generates the hex file to the arduino 
 * @author Giovane Boaviagem
 * @since 20/06/2012
 *
 */
class HexGenerator {
	
	private StringBuffer inoFile;
	private Arduino board;
	
	
	/**
	 * 
	 * @param codeArduino
	 * @throws IOException 
	 */
	public HexGenerator(File codeArduino, Arduino board) throws IOException{
		this.board=board;
		
		this.inoFile=new StringBuffer("");		
		for(String line:FileHelper.readFile(codeArduino)){
			this.inoFile.append(line+System.getProperty("line.separator"));
		}
	}
	
	/**
	 * Generates the main.cpp file
	 * @throws IOException 
	 */
	private void generateMainCPP() throws IOException{
		LinkedList<String> mainCppContent=FileHelper.readFile(new File(Conf.getInstance().getProperty(ConfKeys.PATH_CPP_ARDUINO_CORE)+"/main.cpp.model"));
		
		String pathMain=Conf.getInstance().getProperty(ConfKeys.PATH_CPP_ARDUINO_CORE_SRC);
		
		FileWriter fw=null;
		PrintWriter pw=null;
		try{
			fw=new FileWriter(pathMain+"/main.cpp");
			pw=new PrintWriter(fw);
			
			for(String line:mainCppContent){
				if(line.trim().equalsIgnoreCase("#FILE_INO#")){
					line=line.replaceAll("#FILE_INO#", this.inoFile.toString());
				}
				pw.println(line);
				pw.flush();
			}
		}finally{			
			if(pw!=null){
				pw.close();
			}
			
			if(fw!=null){
				fw.close();
			}
		}		
	}
	
	private void compileMainCPP() throws IOException, InterruptedException{		
		LinkedList<String> command=new LinkedList<String>();
		
		//definição da chamada do executavel
		String osName=System.getProperty("os.name").toLowerCase();
		String osArch=System.getProperty("os.arch");
		
		
		if((osName.indexOf("nix")!=0 || osName.indexOf("nux")!=0) && osArch.indexOf("64")!=0){ //unix and linux			
			command.add("");
		}else{
			throw new IllegalStateException("OS Not Identified!!");
		}
		
		//flags da CPU e diretorio da 
		String pinsArduinoFolder=null;
		switch(this.board.getType()){
		case DIECIMILA:			
			pinsArduinoFolder="standard";
			break;
		case UNO:
			//command.add("-mmcu=atmega328p -DF_CPU=16000000UL ");
			pinsArduinoFolder="standard";
			break;
		default:
			break;
		}
		
		//copiando o arquivo pins_arduino.h correto.
		FileHelper.copyFile(new File(Conf.getInstance().getProperty(ConfKeys.PATH_CPP_ARDUINO_CORE_SRC)+"/variants/"+pinsArduinoFolder+"/pins_arduino.h"), new File(Conf.getInstance().getProperty(ConfKeys.PATH_CPP_ARDUINO_CORE_SRC)));
		
		//Realizando a compilação
		Exec.getInstance().run(command);
	}
	
	public void generate() throws IOException, InterruptedException{
		this.generateMainCPP();
		//this.compileMainCPP();
	}
}
