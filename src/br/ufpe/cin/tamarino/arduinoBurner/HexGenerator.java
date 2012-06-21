package br.ufpe.cin.tamarino.arduinoBurner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import br.ufpe.cin.tamarino.circuit.arduino.Arduino;
import br.ufpe.cin.tamarino.fachada.Tamarino;
import br.ufpe.cin.tamarino.util.FileHelper;

/**
 * Generates the hex file to the arduino 
 * @author Giovane Boaviagem
 * @since 20/06/2012
 *
 */
class HexGenerator {
	private static final File pathTools=new File("tools-cpp");
	
	private StringBuffer inoFile;
	private Arduino board;
	
	
	/**
	 * 
	 * @param codeArduino
	 * @throws IOException 
	 */
	public HexGenerator(File codeArduino, Arduino board) throws IOException{
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
		LinkedList<String> mainCppContent=FileHelper.readFile(new File(pathTools.getAbsolutePath()+"/main.cpp.model"));
		
		FileWriter fw=null;
		PrintWriter pw=null;
		try{
			fw=new FileWriter(Tamarino.TEMP_FOLDER+"/main.cpp");
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
	
	public void compileMainCPP(){
		
	}
	
	public void generate() throws IOException{
		this.generateMainCPP();	
	}
}
