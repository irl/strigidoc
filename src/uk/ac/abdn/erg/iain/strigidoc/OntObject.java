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
		CLASS() {
			public String nom() {
				return "Class";
			}
			public String shortPlural() {
				return "Classes";
			}
		},
		DATA_PROPERTY() {
			public String nom() {
				return "Data Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		},
		OBJECT_PROPERTY() {
			public String nom() {
				return "Object Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		},
		ANNOTATION_PROPERTY() {
			public String nom() {
				return "Annotation Property";
			}
			public String shortPlural() {
				return "Properties";
			}
		};
		
		public abstract String nom();
		public abstract String shortPlural();
	}
	
}
