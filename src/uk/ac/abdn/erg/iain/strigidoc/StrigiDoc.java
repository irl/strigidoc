/**
 *  StrigiDoc.java
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

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * The main application class
 */
public class StrigiDoc {

	/**
	 * StrigiDoc application that takes an OWL ontology URI on the command line
	 * and prints LaTeX output on standard output.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		Options options = new Options();
		
		Option output = OptionBuilder.withArgName("output")
					.hasArg()
					.withDescription("the output format (latex/clatex)")
					.create("output");

		options.addOption(output);

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			printUsage(options);
			return;
		}

		if ( cmd.getArgs().length != 1 ) {
			printUsage(options);
			return;
		}

		OntologyFormatter.OutputType outputValue;

		if ( cmd.hasOption("output") ) {
			String outputString = cmd.getOptionValue("output");

			if ( outputString.equals("latex") ) {
				outputValue = OntologyFormatter.OutputType.LATEX;
			} else if ( outputString.equals("clatex") ) {
				outputValue = OntologyFormatter.OutputType.LATEX_COMPLETE;
			} else {
				printUsage(options);
				return;
			}
		} else {
			outputValue = OntologyFormatter.OutputType.LATEX_COMPLETE;
		}
				

		try {
			Ontology o = new Ontology(cmd.getArgs()[0]);
			System.out.println(OntologyFormatter.format(o, outputValue));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints usage instructions to the terminal
	 */
	private static void printUsage(Options options) {
		System.err.println("StrigiDoc Copyright (C) 2013 Iain R. Learmonth and contributors");
		System.err.println("");

		HelpFormatter f = new HelpFormatter();
		f.printHelp("java -jar strigidoc-*-standalone.jar", options);

		System.err.println("");
	}

}

