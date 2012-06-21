package br.ufpe.cin.tamarino.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author Giovane Boaviagem
 * @since 20/06/2012
 *
 */
public class FileHelper {
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static LinkedList<String> readFile(File file) throws IOException{
		LinkedList<String> ret=new LinkedList<String>();
		
		FileInputStream fis=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		try{
			fis=new FileInputStream(file);
			isr=new InputStreamReader(fis);
			br=new BufferedReader(isr);
			
			String line=null;
			while((line=br.readLine())!=null){
				ret.add(line);
			}
			
		}finally{
			try {
				if(br!=null){					
					br.close();					
				}
				
				if(isr!=null){
					isr.close();
				}
				
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}
		
		return ret;
	}

}
