package br.ufpe.cin.tamarino.gui;

import java.io.File;
import java.io.FileNotFoundException;

import br.ufpe.cin.tamarino.fachada.Tamarino;

public class Main {
	
	public static void main(String[] args){
		String file=null;
//		String option=null;
		if(args!=null){
			if(args.length>=1){
				file=args[0];
			}
			
//			if(args.length>=2){
//				option=args[1];
//			}
		}
		
		try {
			if(file!=null){				
				Tamarino tam=new Tamarino(new File(file));				
			}
		} catch (FileNotFoundException e) {			 
			e.printStackTrace();
		}
	}

}
