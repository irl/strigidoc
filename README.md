strigidoc
=========

A documentation generator for OWL ontologies using OWLAPI.

This git repository contains an Eclipse workspace. You can use Eclipse to easily build this project and then export a JAR file.

The JAR file should be run with a single command line argument, the URL to the ontology you would like documented. The LaTeX output appears on the standard output and can be saved using output redirection. For example:

    java -jar strigidoc.jar "http://homepages.abdn.ac.uk/i.learmonth/pages/qunits/#" > qunits.tex

Note: This is very much a work in progress at this stage.

All code in this repository is (C) Iain R. Learmonth 2013.

Code is available under the GNU GPL Version 3, or at your option, any later version.

