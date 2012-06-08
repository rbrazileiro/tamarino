package br.ufpe.cin.tamarino.gui;

import java.io.File;
import java.io.FileNotFoundException;

import br.ufpe.cin.tamarino.fachada.Tamarino;

/**
 * Main class of the system
 * @author Giovane Boaviagem
 * @since 08/06/2012
 *
 */
public class Main {
	private static File fileName=new File("testeOut.xml");
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			if(args.length>1){
				fileName=new File(args[0]);
			}
			
			if(fileName!=null&&fileName.exists()&&fileName.isFile()){				
				Tamarino tam=new Tamarino(fileName);
				tam.exec();			
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
	}

}
