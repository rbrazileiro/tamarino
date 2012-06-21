package br.ufpe.cin.tamarino.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author Giovane Boaviagem
 * @since 19/06/2012
 *
 */
public class Exec {
	
	private static Exec instance=null;
	
	private Exec(){}
	
	public static Exec getInstance(){
		if(instance==null){
			instance=new Exec();
		}
		return instance;
	}
	
	/**
	 * @author Giovane Boaviagem
	 * @since 19/06/2012
	 */
	private class StreamGobbler extends Thread{
		private InputStream is;
		private String type;
		
		public StreamGobbler(InputStream is,String type) {
			this.is=is;
			this.type=type;
		}
		
		public void run(){
			try{
				InputStreamReader isr=new InputStreamReader(this.is);
				BufferedReader br=new BufferedReader(isr);
				String line=null;
				while((line=br.readLine())!=null){
					System.out.println(type+"> "+line);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param command
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void run(LinkedList<String> cmd) throws IOException, InterruptedException{
		if(cmd==null||cmd.size()==0){
			System.err.println("Invalid command!!");
			System.exit(1);
		}
		
		String osName=System.getProperty("os.name");		
		if(osName.equalsIgnoreCase("Windows 95")){
			cmd.add(0,"command.com");
			cmd.add(1,"/C");			
		}else if(osName.startsWith("Win")){
			cmd.add(0,"cmd.exe");
			cmd.add(1,"/C");			
		}
		
		Runtime rt=Runtime.getRuntime();
		Process proc=rt.exec((String[]) cmd.toArray());
		
		StreamGobbler error=new StreamGobbler(proc.getErrorStream(), "ERROR");
		StreamGobbler output=new StreamGobbler(proc.getInputStream(), "OUTPUT");
		error.start();
		output.start();
		
		
		int exitVal = proc.waitFor();		
		System.out.println("Exit value: "+exitVal);
	}
	
	
	
	public static void main(String[] args){
		try {
			LinkedList<String> list=new LinkedList<String>();
			list.add("pwd");
			Exec.getInstance().run(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
		
}
