/**
 *  OntologyFormatter.java
 *
 *  This file is a part of StrigiDoc.
 *
 *  Copyright (C) 2013 Iain R. Learmonth and contributors
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package uk.ac.abdn.erg.iain.strigidoc;

public class OntologyFormatter {

	/**
	 * Format the documentation for the given ontology as the given output type
	 *
	 * @param o the ontology
	 * @return the formatted documentation
	 */	
	public static String format(Ontology o, OntologyFormatter.OutputType ot) {

		switch ( ot ) {

			case LATEX:
				return formatAsLatex(o);

		}

		return null;

	}

	private static String formatAsLatex(Ontology o) {

		String ret = "";

		ret += "\\begin{description}\n";
		ret += "\\item[Ontology IRI] \\hfill \n";
		ret += "\\url{" + o.iri + "}\\\\ ";
				
		if (o.contrib.size() > 0) {
			ret += "\\item[Authors] \\hfill \n";

			for (String author : o.contrib ) {
				if (author.contains("<")) {
					author = author.substring(0, author.indexOf('<') - 1);
				}
				ret += author + "\\\\ ";
			}

		}
		
		if (o.imports.size() > 0) {
			ret += "\\item[Imported Ontologies] \\hfill \n";

			for (String im : o.imports) {
				ret += "\\url{" + im + "}\\\\ ";
			}
		}
		ret += "\\end{description}\n";

		if ( o.abstr != null ) {
			ret += o.abstr;
		}
		
		if ( o.intro != null ) {
			ret += "\\section{Introduction}\n\n";
			ret += o.intro;
		}

		if (o.classes.size() > 0) {
			ret += "\\section{Classes}\n\n";
			
			for (OntObject ob : o.classes)
				ret += OntObjectFormatter.format(ob);
		}
		if (o.objprops.size() > 0) {
			ret += "\\section{Object Properties}\n\n";

			for (OntObject ob : o.objprops)
				ret += OntObjectFormatter.format(ob);
		}
		if (o.dataprops.size() > 0) {
			ret += "\\section{Data Properties}\n\n";

			for (OntObject ob : o.dataprops)
				ret += OntObjectFormatter.format(ob);
		}
		if (o.annprops.size() > 0) {
			ret += "\\section{Annotation Properties}\n\n";

			for (OntObject ob : o.annprops)
				ret += OntObjectFormatter.format(ob);
		}

		return ret;

	}

	/**
	 * Supported output types for the formatter
	 */
	public enum OutputType {

		/**
		 * Output as LaTeX for inclusion in a document
		 */
		LATEX;

	}

}
