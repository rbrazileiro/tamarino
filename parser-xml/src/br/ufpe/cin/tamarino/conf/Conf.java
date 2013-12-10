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
		this.prop=PropertyHelper.load(false,propFile.getPath(), null);
	}
	
	/**
	 * @return The unique instance of <code>Conf</code>
	 */
	public static Conf getInstance(){
		if(instance==null){
			instance=new Conf();
		}
		return instance;
	}
	
	/**
	 * @param key The property's key. See {@link ConfKeys}
	 * @return The value of property specified by the key.
	 */
	public String getProperty(ConfKeys key){
		return prop.getProperty(key.toString());
	}
	
	/**
	 * List of property keys of the file conf.properties
	 * @author Giovane Boaviagem
	 * @since 21/06/2012
	 */
	public enum ConfKeys{
		/**
		 * Path to the deploy folder
		 */
		PATH_DEPLOY,
		/**
		 * Path to the temp folder
		 */
		PATH_TEMP,
		/**
		 * Path to the cpp folder
		 */
		PATH_CPP,
		/**
		 * Path to the arduino-core's folder
		 */
		PATH_CPP_ARDUINO_CORE,
		/**
		 * Path to the arduino-core's bin folder
		 */
		PATH_CPP_ARDUINO_CORE_BIN,
		/**
		 * Path to the arduino-core's src folder
		 */
		PATH_CPP_ARDUINO_CORE_SRC,
		/**
		 * Path to the avr's bin folder (linux 64 bits)
		 */
		PATH_CPP_TOOLS_LINUX64_AVR_BIN;
		
		@Override
		public String toString(){
			switch(this){
			case PATH_DEPLOY:
				return "path.deploy";
			case PATH_TEMP:
				return "path.temp";
			case PATH_CPP:
				return "path.cpp";
			case PATH_CPP_ARDUINO_CORE:
				return "path.cpp.arduino.core";
			case PATH_CPP_ARDUINO_CORE_BIN:
				return "path.cpp.arduino.core.bin";
			case PATH_CPP_ARDUINO_CORE_SRC:
				return "path.cpp.arduino.core.src";
			case PATH_CPP_TOOLS_LINUX64_AVR_BIN:
				return "path.cpp.tools.linux64.avr.bin";
			default:
				return "KEY_NOT_SPECIFIED!";
			}
		}
		
	}
}
