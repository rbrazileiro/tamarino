package br.ufpe.cin.tamarino.conf;

import java.io.File;
import java.util.Properties;

import br.ufpe.cin.tamarino.util.PropertyHelper;

/**
 * Access the file conf
 * @author Giovane Boaviagem
 * @since 20/06/2012
 *
 */
public class Conf {
	//Localicação do arquivo properties
	private static final File propFile=new File("conf/conf.properties");
	private static Conf instance=null;
	
	private Properties prop;
	
	private Conf(){
		this.prop=PropertyHelper.load(propFile.getPath(), null);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Conf getInstance(){
		if(instance==null){
			instance=new Conf();
		}
		return instance;
	}
	
	public String getProperty(ConfKeys key){
		return prop.getProperty(key.toString());
	}
	
	public enum ConfKeys{
		PATH_TEMP;
		
		public String toString(){
			switch(this){
			case PATH_TEMP:
				return "path.temp";				
			default:
				return "KEY_NOT_SPECIFIED!";
			}
		}
		
	}
}
