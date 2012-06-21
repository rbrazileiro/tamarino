package br.ufpe.cin.tamarino.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
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
	
	/**
	 * Tests if the file specified is being used by another process 
	 * @param file File to be tested
	 * @return true if the file is being used, false otherwise
	 */
	public static boolean isFileInUse(File file){
		boolean result=false;
		
		FileChannel fc=null;
		try {
			fc=new RandomAccessFile(file, "rw").getChannel();
		} catch (FileNotFoundException e) {
			if(!file.exists()){
				System.err.println("The file "+file.getName()+" not exists!!");
			}else{
				System.out.println("The file "+file.getName()+" is in use by another process!!");
				result=true;
			}
		} finally{
			if(fc!=null){
				try {
					fc.close();
				} catch (IOException e) {				
					e.printStackTrace();
				}
			}else{
				return result;
			}
		}
		
		return false;
	}
	
	/**
	 * Copy a file to another folder
	 * @param src Origin file
	 * @param dst Destination FOLDER.
	 * @throws FileNotFoundException 
	 */
	public static void copyFile(File src,File dst) throws FileNotFoundException{
		
		if(src==null){
			throw new IllegalArgumentException("File src can't be null!!");
		}
		
		if(dst==null){
			throw new IllegalArgumentException("File dst can't be null!!");
		}
		
		if(!src.exists()){
			throw new FileNotFoundException("File "+src.getName()+" can't be found!");
		}
		
		if(!dst.exists()){
			dst.mkdirs();
		}
		
		
		dst=new File(dst.getPath()+"/"+src.getName());
		
		if(!isFileInUse(src)){
			FileChannel in=null;
			FileChannel out=null;
			try {
				in=new FileInputStream(src).getChannel();
				out=new FileOutputStream(dst).getChannel();
				
				
				in.transferTo(0, in.size(), out);
				
			} catch (FileNotFoundException e) {			
				e.printStackTrace();
			} catch (IOException e) {			
				e.printStackTrace();
			} finally{
				try {
					if(in!=null){				
						in.close();				
					}
					
					if(out!=null){
						out.close();
					}
				} catch (IOException e) {				
					e.printStackTrace();
				}			
			}
		}
		
	}
}
