package br.ufpe.cin.tamarino.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public void run(String command) throws IOException, InterruptedException{
		if(command==null||command.equalsIgnoreCase("")){
			System.err.println("Invalid command!!");
			System.exit(1);
		}
		
		String osName=System.getProperty("os.name");
		String[] cmd=new String[3];
		if(osName.equalsIgnoreCase("Windows 95")){
			cmd[0]="command.com";
			cmd[1]="/C";
			cmd[2]=command;
		}else if(osName.startsWith("Win")){
			cmd[0]="cmd.exe";
			cmd[1]="/C";
			cmd[2]=command;
		}else{
			cmd[0]=command;
			cmd[1]="";
			cmd[2]=""; //linux
		}
		
		Runtime rt=Runtime.getRuntime();
		Process proc=rt.exec(cmd);
		
		StreamGobbler error=new StreamGobbler(proc.getErrorStream(), "ERROR");
		StreamGobbler output=new StreamGobbler(proc.getInputStream(), "OUTPUT");
		error.start();
		output.start();
		
		
		int exitVal = proc.waitFor();		
		System.out.println("Exit value: "+exitVal);
	}
	
	
	
	public static void main(String[] args){
		try {
			Exec.getInstance().run("pwd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
		
}
