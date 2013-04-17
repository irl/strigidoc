/**
 *  Ontology.java
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

import static uk.ac.abdn.erg.iain.strigidoc.OntObjectFormatter.asString;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Ontology {

	private OWLOntologyManager m;
	private OWLOntology o;

	protected String iri;
	protected String title = null;
	protected String abstr = null;
	protected String intro = null;

	protected Set<String> contrib;
	protected Set<String> imports;

	protected Set<OntObject> classes;
	protected Set<OntObject> objprops;
	protected Set<OntObject> dataprops;
	protected Set<OntObject> annprops;

	public Ontology(String iri) throws OWLOntologyCreationException,
			IOException {

		m = OWLManager.createOWLOntologyManager();

		o = m.loadOntologyFromOntologyDocument(getOntologyInputStream(iri));

		this.iri = o.getOntologyID().getOntologyIRI().toString();
		title = getTitle();
		abstr = getAbstract();
		intro = getIntroduction();

		contrib = getContributors();
		imports = getImports();
		
		classes = getClasses();
		dataprops = getDataProperties();
		objprops = getObjectProperties();
		annprops = getAnnotationProperties();
	}

	private InputStream getOntologyInputStream(String iri) throws IOException {
		
		String accept = "application/rdf+xml, application/xml;q=0.6, text/xml;q=0.6";
		
		URL address = new URL(iri);
		URLConnection cnx = address.openConnection();
		cnx.setAllowUserInteraction(false);         
		cnx.setDoOutput(true);
		cnx.addRequestProperty("Accept", accept);
		InputStream is = cnx.getInputStream();
		
		return is;
	}
	
	private Set<String> getContributors() {
		
		HashSet<String> ret = new HashSet<String>();
		
		Set<OWLAnnotation> anns = o.getAnnotations();
		
		for (OWLAnnotation a : anns) {
			if (a.getProperty().getIRI().toString()
					.equals("http://purl.org/dc/elements/1.1/creator")) {
				ret.add(asString(a.getValue()));
			}
		}
		
		return ret;
	}


	
	private Set<String> getImports() {
		Set<OWLOntology> owlImports = o.getDirectImports();
		Set<String> ret = new HashSet<String>();
		
		for ( OWLOntology im : owlImports ) {
			ret.add(im.getOntologyID().getOntologyIRI().toString());
		}
		
		return ret;
	}

	private String getTitle() {

		Set<OWLAnnotation> anns = o.getAnnotations();

		for (OWLAnnotation a : anns) {

			if (a.getProperty().getIRI().toString()
					.equals("http://purl.org/dc/elements/1.1/title")) {
				return asString(a.getValue());
			}

		}

		return null;
	}

	private String getAbstract() {

		Set<OWLAnnotation> anns = o.getAnnotations();

		for (OWLAnnotation a : anns) {

			if (a.getProperty().getIRI().toString()
					.equals("http://www.w3.org/2000/01/rdf-schema#comment")) {
				return asString(a.getValue());
			}

		}

		return null;
	}

	private String getIntroduction() {

		Set<OWLAnnotation> anns = o.getAnnotations();

		for (OWLAnnotation a : anns) {
			if (a.getProperty().getIRI().toString()
					.equals("http://purl.org/dc/elements/1.1/description")) {
				return asString(a.getValue());
			}
		}

		return null;
	}

	private Set<OntObject> getClasses() {

		Set<OWLClass> owlClasses = o.getClassesInSignature();
		Set<OntObject> ret = new HashSet<OntObject>();

		for (OWLClass c : owlClasses) {
			if (!belongs((OWLEntity) c)) {
				continue;
			}
			OntObject ob = OntObjectFactory.createFromClass(c, o);
			ret.add(ob);
		}

		return ret;
	}

	private Set<OntObject> getDataProperties() {

		Set<OWLDataProperty> owlDataProperties = o
				.getDataPropertiesInSignature();
		Set<OntObject> ret = new HashSet<OntObject>();

		for (OWLDataProperty p : owlDataProperties) {
			if (!belongs((OWLEntity) p)) {
				continue;
			}
			OntObject ob = OntObjectFactory.createFromProperty(
					OntObject.OntObjectType.DATA_PROPERTY, p, o);
			ret.add(ob);
		}

		return ret;
	}

	private Set<OntObject> getObjectProperties() {

		Set<OWLObjectProperty> owlObjectProperties = o
				.getObjectPropertiesInSignature();
		Set<OntObject> ret = new HashSet<OntObject>();

		for (OWLObjectProperty p : owlObjectProperties) {
			if (!belongs((OWLEntity) p)) {
				continue;
			}
			OntObject ob = OntObjectFactory.createFromProperty(
					OntObject.OntObjectType.OBJECT_PROPERTY, p, o);
			ret.add(ob);
		}

		return ret;
	}

	private Set<OntObject> getAnnotationProperties() {

		Set<OWLAnnotationProperty> owlAnnotationProperties = o
				.getAnnotationPropertiesInSignature();
		Set<OntObject> ret = new HashSet<OntObject>();

		for (OWLAnnotationProperty p : owlAnnotationProperties) {
			if (!belongs((OWLEntity) p)) {
				continue;
			}
			OntObject ob = OntObjectFactory.createFromAnnotationProperty(p, o);
			ret.add(ob);
		}

		return ret;
	}

	private boolean belongs(OWLEntity e) {
		return e.getIRI().toString()
				.startsWith(o.getOntologyID().getOntologyIRI().toString());
	}

}
