/**
 *  OntObjectFactory.java
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

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

public class OntObjectFactory {

	public static OntObject createFromClass(OWLClass c, OWLOntology o) {
		OntObject ob = createFromEntity(OntObject.OntObjectType.CLASS, (OWLEntity) c, o);
		
		Set<OWLClassExpression> sup = c.getSuperClasses(o);
		Set<OWLClassExpression> sub = c.getSubClasses(o);
		
		for ( OWLClassExpression s : sup ) {			
			checkAndAddRelated(ob, s, o, true);
		}
		
		for ( OWLClassExpression s : sub ) {
			checkAndAddRelated(ob, s, o, false);
		}
		
		return ob;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OntObject createFromProperty(OntObject.OntObjectType t, OWLProperty p, OWLOntology o) {
		OntObject ob = createFromEntity(t, (OWLEntity) p, o);
		
		Set<OWLPropertyExpression> sup = p.getSuperProperties(o);
		Set<OWLPropertyExpression> sub = p.getSubProperties(o);
		
		for ( OWLPropertyExpression s : sup ) {
			checkAndAddRelated(ob, s, o, true);
		}
		
		for ( OWLPropertyExpression s : sub ) {
			checkAndAddRelated(ob, s, o, false);
		}
		
		return ob;
	}
	
	public static OntObject createFromAnnotationProperty(OWLAnnotationProperty p, OWLOntology o) {
		
		OntObject ob = createFromEntity(OntObject.OntObjectType.ANNOTATION_PROPERTY, (OWLEntity) p, o);
		
		Set<OWLAnnotationProperty> sup = p.getSuperProperties(o);
		Set<OWLAnnotationProperty> sub = p.getSubProperties(o);
		
		for ( OWLAnnotationProperty s : sup ) {
			checkAndAddRelated(ob, s, o, true);
		}
		
		for ( OWLAnnotationProperty s : sub ) {
			checkAndAddRelated(ob, s, o, false);
		}
		
		return ob;
		
	}

	private static OntObject createFromEntity(OntObject.OntObjectType t, OWLEntity e, OWLOntology o) {
		
		OntObject ob = new OntObject(t, e.getIRI().toString());
		
		Set<OWLAnnotation> anns = e.getAnnotations(o);
		
		for (OWLAnnotation a : anns) {
			if (a.getProperty().getIRI().toString()
					.equals("http://www.w3.org/2000/01/rdf-schema#comment")) {
				ob.setComment(asString(a.getValue()));
				break;
			}
		}
		
		return ob;
	}
	
	private static boolean checkAndAddRelated(OntObject ob, OWLObject s, OWLOntology o, boolean sup) {
		if(! (s instanceof OWLEntity)) {
			return false;
		}
		OWLEntity entity = (OWLEntity) s;
		if ( ob.getIRIString().startsWith(o.getOntologyID().getOntologyIRI().toString()) ) {
			if ( sup ) {
				ob.addSuper(entity.getIRI().toString());
			} else {
				ob.addSub(entity.getIRI().toString());
			}
			return true;
		} else {
			return false;
		}
	}
}
