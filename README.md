strigidoc
=========

A documentation generator for OWL ontologies using OWLAPI.

Building from source
--------------------

You can build a JAR file from source by running:

    git clone https://github.com/irl/strigidoc.git
    cd strigidoc
    mvn package

This produces three JAR files in the target/ directory.

 * strigidoc-0.1-SNAPSHOT.jar - Compiled source for use as a library
 * strigidoc-0.1-SNAPSHOT-sources.jar - Sources archive
 * strigidoc-0.1-SNAPSHOT-standalone.jar - Standalone runnable JAR file

Using the pre-built binary
--------------------------

The JAR file should be run with a single command line argument, the URL to the ontology you would like documented. The LaTeX output appears on the standard output and can be saved using output redirection. For example:

    java -jar strigidoc-0.1-SNAPSHOT-standalone.jar "http://homepages.abdn.ac.uk/i.learmonth/pages/qunits/#" > qunits.tex

License Details
---------------

All code in this repository is (C) Iain R. Learmonth 2013.

Code is available under the GNU GPL Version 3, or at your option, any later version.

