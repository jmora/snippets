package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Hello world!
 * 
 */
public class Main {
	public static void main(String[] args) throws OWLOntologyCreationException {
		System.out.println("I'm going to do some things, don't mind me.");
		RelationChecker rc = new RelationChecker(new File(args[0]));

		System.out.println("\nSubsumed!");
		try {
			for (OWLClass c : rc.subsumedConcepts("Externa"))
				System.out.println(c.toStringID());
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nSubsuming!");
		try {
			for (OWLClass c : rc.subsumingConcepts("Transferencia"))
				System.out.println(c.toStringID());
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
			for (OWLClass c : rc.disjointConcepts("Nacional"))
				System.out.println(c.toStringID());
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

	}
}
