package br.ufpe.cin.tamarino.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyHelper {
	
	public static final String SUFFIX=".properties";
	
	public static Properties load(boolean loadAsResourceBundle,String name,ClassLoader loader){
		if(name==null){
			throw new IllegalArgumentException("null input: name");
		}
		
		if(name.startsWith("/")){
			name=name.substring(1);			
		}
		
		Properties result=null;
		
		InputStream in=null;
		try{
			if(loadAsResourceBundle){
				name=name.replace('/', '.');
				final ResourceBundle rb=ResourceBundle.getBundle(name,Locale.getDefault(),loader);
				result=new Properties();
				
				for(Enumeration<String> keys=rb.getKeys();keys.hasMoreElements();){
					final String key=keys.nextElement();
					final String value=rb.getString(key);
					result.put(key, value);
				}
			}else{
				//name=name.replace('.', '/');
				if(!name.endsWith(SUFFIX)){
					name=name.concat(SUFFIX);					
				}
				
				if(loader!=null){
					in=loader.getResourceAsStream(name);
				}else{
					in=new FileInputStream(new File(name));
				}
				
				if(in!=null){
					result=new Properties();
					result.load(in);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}catch (MissingResourceException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){				
					in.close();				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
