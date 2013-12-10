package br.ufpe.cin.tamarino.gui;

import java.io.File;
import java.io.FileNotFoundException;

import br.ufpe.cin.tamarino.conf.Conf;
import br.ufpe.cin.tamarino.conf.Conf.ConfKeys;
import br.ufpe.cin.tamarino.fachada.Tamarino;

/**
 * Main class of the system
 * @author Giovane Boaviagem
 * @since 08/06/2012
 *
 */
public class Main {	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			Tamarino tam=null;
			String pathDeploy=Conf.getInstance().getProperty(ConfKeys.PATH_DEPLOY);
			File fileDeploy=new File(pathDeploy);
			if(!fileDeploy.exists()){
				fileDeploy.mkdirs();
			}
			
			if(!fileDeploy.isDirectory()){
				throw new FileNotFoundException("File is not a directory!");
			}
			
			System.out.println("** Iniciando a busca por novos arquivos a serem processados...");
			while(true){				
				File[] lista=fileDeploy.listFiles();
				if(lista.length>0){
					for (int i = 0; i < lista.length; i++) {
						File f=lista[i];
						if(f.isFile()){
							System.out.println("Arquivo "+f.getName()+" encontrado. Iniciando processamento.");
							tam=new Tamarino(f);
							tam.exec();
							System.out.println("Eliminando o arquivo "+f.getName()+" para evitar reprocesssamento.");
							f.delete();
							System.out.println("Processamento do arquivo "+f.getName()+" realizado com sucesso.");
						}						
					}					
				}else{
					System.out.println("Nenhum arquivo encontrado.");
				}
				Thread.sleep(3000);
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

}
