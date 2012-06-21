package br.ufpe.cin.tamarino.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyHelper {
	
	public static Properties load(String name,ClassLoader loader){
		if(name==null){
			throw new IllegalArgumentException("null input: name");
		}
		
		if(loader==null){
			loader=ClassLoader.getSystemClassLoader();
		}
		
		if(name.startsWith("/")){
			name=name.substring(1);			
		}
		
		Properties result=null;
		
		InputStream in=null;
		try{
			if(LOAD_AS_RESOURCE_BUNDLE){
				name=name.replace('/', '.');
				final ResourceBundle rb=ResourceBundle.getBundle(name,Locale.getDefault(),loader);
				result=new Properties();
				
				for(Enumeration<String> keys=rb.getKeys();keys.hasMoreElements();){
					final String key=keys.nextElement();
					final String value=rb.getString(key);
					result.put(key, value);
				}
			}else{
				name=name.replace('.', '/');
				if(name.endsWith(SUFFIX)){
					name=name.concat(SUFFIX);					
				}
				
				in=loader.getResourceAsStream(name);
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
	
	public static final boolean LOAD_AS_RESOURCE_BUNDLE=true;
	public static final String SUFFIX=".properties";

}
