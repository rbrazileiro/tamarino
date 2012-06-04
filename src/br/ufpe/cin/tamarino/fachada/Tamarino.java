package br.ufpe.cin.tamarino.fachada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import br.ufpe.cin.tamarino.circuit.Circuit;
import br.ufpe.cin.tamarino.circuit.components.Component;
import br.ufpe.cin.tamarino.circuit.components.Connection;
import br.ufpe.cin.tamarino.circuit.components.Pin;
import br.ufpe.cin.tamarino.circuit.components.output.Led;
import br.ufpe.cin.tamarino.xml.ParserTamarino;

/**
 * Fachada do sistema.
 * @author Giovane Boaviagem
 * @since 26/05/2012
 *
 */
public class Tamarino {
	
	private Circuit circ;

	
	/**
	 * Creates a new instance of <code>Tamarino</code>
	 * @param arqXML
	 * @throws FileNotFoundException
	 */
	public Tamarino(File arqXML) throws FileNotFoundException{
		if(arqXML==null){
			throw new NullPointerException("XML file can't be null!!");
		}
		
		this.circ=(Circuit) ParserTamarino.getInstance().toObject(new FileInputStream(arqXML));
	}
	
	/**
	 * 
	 */
	public void testFile(){
		System.out.println("==== Testando o arquivo ====");
		System.out.println("* Lista de componentes:");
		for(Iterator<Component> it=this.circ.getComponents().iterator();it.hasNext();){
			Component comp=it.next();
			Led l=(Led) comp;
			System.out.println("** Nome: "+comp.getLabel());
			System.out.println("** Pinagem: ");
			System.out.println("** Tipo: "+l.getType());
			for(Iterator<Pin> it2=comp.getListPins().iterator();it2.hasNext();){
				Pin p=it2.next();
				System.out.println("*** Label: "+p.getLabel());
				System.out.println("*** Label do componente: "+p.getLabelComponent());				
			}			
		}
		System.out.println();
		
		System.out.println("* Lista de conexões:");
		for(Iterator<Connection> it3=this.circ.getConnections().iterator();it3.hasNext();){
			Connection conn=it3.next();
			System.out.println("** Pino de origem: ");
			System.out.println("*** label: "+conn.getFrom().getLabel());
			System.out.println("*** label do componente: "+conn.getFrom().getLabelComponent());
			System.out.println("** Pino de destino: ");
			System.out.println("*** label: "+conn.getTo().getLabel());
			System.out.println("*** label do componente: "+conn.getTo().getLabelComponent());
		}
	}
}
