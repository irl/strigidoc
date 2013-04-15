package uk.ac.abdn.erg.iain.strigidoc;

public class OntologyPrinter {

	public static void print(Ontology o) {

		System.out.println(o.getIntroductionValues());

		if ( o.abstr != null ) {
			System.out.println(o.abstr);
		}
		
		if ( o.intro != null ) {
			System.out.println(o.intro);
		}

		if (o.classes.size() > 0) {
			System.out.println("\\section{Classes}\n\n");
			
			for (OntObject ob : o.classes)
				System.out.println(OntObjectFormatter.format(ob));
		}
		if (o.objprops.size() > 0) {
			System.out.println("\\section{Object Properties}\n\n");

			for (OntObject ob : o.objprops)
				System.out.println(OntObjectFormatter.format(ob));
		}
		if (o.dataprops.size() > 0) {
			System.out.println("\\section{Data Properties}\n\n");

			for (OntObject ob : o.dataprops)
				System.out.println(OntObjectFormatter.format(ob));
		}
		if (o.annprops.size() > 0) {
			System.out.println("\\section{Annotation Properties}\n\n");

			for (OntObject ob : o.annprops)
				System.out.println(OntObjectFormatter.format(ob));
		}

	}

}
