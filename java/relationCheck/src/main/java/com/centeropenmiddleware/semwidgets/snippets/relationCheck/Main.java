package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Hello world!
 * 
 */
public class Main {
	public static void main(String[] args) throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		System.out.println("I'm going to do some things, don't mind me.");
		// RelationChecker rc = new RelationChecker(new File(args[0]));
		RelationChecker rc = new RelationChecker();
		rc.addOntology(new File("resources/bank.owl").toURI().toURL());
		DefaultPrefixManager pm = new DefaultPrefixManager();
		SimpleShortFormProvider ssfp = new SimpleShortFormProvider();

		System.out.println("\nSubsumed!");
		try {
			for (OWLClass c : rc.subsumedConcepts("<http://centeropenmiddleware.com/semwidgets/merged#Cuenta>")) {
				System.out.println(pm.getShortForm(c));
				System.out.println(ssfp.getShortForm(c));
			}
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nSubsuming!");
		try {
			for (OWLClass c : rc.subsumingConcepts("<http://www.centeropenmiddleware.com/ontology/san#Tarjeta>")) {
				System.out.println(c.toStringID());
				System.out.println(pm.getShortForm(c));
				System.out.println(ssfp.getShortForm(c));
			}
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nEquivalent!");
		try {
			for (OWLClass c : rc.equivalentConcepts("tiene_titular some Titular"))
				System.out.println(c.toStringID());
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nDisjoint!");
		try {
			for (OWLClass c : rc.disjointConcepts("san:Nacional"))
				System.out.println(c.toStringID());
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

	}
}
