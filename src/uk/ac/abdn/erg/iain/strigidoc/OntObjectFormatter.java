package uk.ac.abdn.erg.iain.strigidoc;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;

public class OntObjectFormatter {

	public static String format(OntObject o) {

		String ret;

		// Start with a heading for the object

		if (o.getLabel() != null) {
			ret = "\\subsection{" + o.getLabel() + "}\n";
		} else {
			ret = "\\subsection{" + o.getFragment() + "}\n";
		}

		// Now for the detail section
		
		ret += "\\begin{description}\n";
		
		// With the object's IRI
		
		ret += "\\item[" + o.getType().nom() + " IRI] \\hfill \n";
		ret += "\\\\ \\url{" + o.getIRIString() + "}\n";
		
		// With the object's super objects
		
		ret += formatRelatedObjects(o, true);
		
		// With the object's sub objects
		
		ret += formatRelatedObjects(o, false);
		
		// End the detail section
		
		ret += "\\end{description}\n\n";
		
		// Add the comment if present
		
		if ( o.getComment() != null ) {
			ret += o.getComment() + "\n\n";
		}
		
		return ret;
	}
	
	public static String asString(OWLObject value) {
		if (value instanceof OWLLiteral) {				
			return ((OWLLiteral)value).getLiteral();
		} else {
			// Silly fallback
			return value.toString();
		}
	}
	

	private static String formatRelatedObjects(OntObject o, boolean sup) {
		
		String rel = ( sup ) ? "Super-" : "Sub-";
		Set<String> obs = ( sup ) ? o.getSupers() : o.getSubs();
		
		if ( obs.size() == 0 ) {
			return "";
		}
		
		String ret = "\\item[" + rel + o.getType().shortPlural() + "] \\hfill \\\\ \n";
		
		for ( String i : obs ) {
			ret += "\\url{" + i + "}\\\\\n";
		}
		
		return ret;
	}
}
