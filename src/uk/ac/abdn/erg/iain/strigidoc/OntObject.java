/**
 *  OntObject.java
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

import java.util.HashSet;
import java.util.Set;

public class OntObject {

	private OntObjectType type;
	private String iri;
	
	private String label = null;
	private String comment = null;
	
	private Set<String> sup;
	private Set<String> sub;
	
	public OntObject(OntObjectType type, String iri) {
		this.type = type;
		this.iri = iri;
		
		sup = new HashSet<String>();
		sub = new HashSet<String>();
	}
	
	public String getIRIString() {
		return iri;
	}
	
	public OntObjectType getType() {
		return type;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getComment() {
		return comment;
	}
	
	public Set<String> getSupers() {
		return sup;
	}
	
	public Set<String> getSubs() {
		return sub;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void addSuper(String sup) {
		this.sup.add(sup);
	}
	
	public void addSub(String sub) {
		this.sub.add(sub);
	}
	
	public String getFragment() {
		if ( iri.contains("#") ) {
			return iri.substring(iri.indexOf('#') + 1);
		} else {
			return iri.substring(iri.lastIndexOf('/') + 1);
		}
	}
	
	public enum OntObjectType {
		/**
		 * Instance of owl:Class
		 */
		CLASS() {
			public String nom() {
				return "Class";
			}
			public String shortPlural() {
				return "Classes";
			}
		},
		/**
		 * Instance of owl:DatatypeProperty
		 */
		DATA_PROPERTY() {
			public String nom() {
				return "Data Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		},
		/**
		 * Instance of owl:ObjectProperty
		 */
		OBJECT_PROPERTY() {
			public String nom() {
				return "Object Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		},
		/**
		 * Instance of owl:AnnotationProperty
		 */
		ANNOTATION_PROPERTY() {
			public String nom() {
				return "Annotation Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		};
	
		/**
		 * Returns a human readable name of the type
		 *
		 * @return a human readable name of the type
		 */
		public abstract String nom();

		/**
		 * Returns a short human readable name of the type in plural form
		 *
		 * @return a short human readable name of the type in plural form
		 */
		public abstract String shortPlural();
	}
	
}
