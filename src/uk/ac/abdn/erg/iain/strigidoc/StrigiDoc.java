package uk.ac.abdn.erg.iain.strigidoc;

import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class StrigiDoc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		try {
			Ontology data = new Ontology(args[0]);
			OntologyPrinter.print(data);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
