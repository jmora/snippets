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
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;


/**
 * Hello world!
 * 
 */
public class Main {
	public static void main(String[] args) throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		System.out.println("I'm going to do some things, don't mind me.");
		MultiOntologyRelationChecker rc = new MultiOntologyRelationChecker();
		rc.addOntology(new File("resources/testNames.owl").toURI().toURL());
		ManchesterOWLSyntaxPrefixNameShortFormProvider dpm = new ManchesterOWLSyntaxPrefixNameShortFormProvider(new DefaultPrefixManager());
		SimpleShortFormProvider ssfp = new SimpleShortFormProvider();

		System.out.println("\nSubsumed!");
		try {
			for (OWLClass c : rc.subsumedConcepts("A"))
				System.out.println(dpm.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nSubsuming!");
		try {
			for (OWLClass c : rc.subsumingConcepts("C"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nEquivalent!");
		try {
			for (OWLClass c : rc.equivalentConcepts("E"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nDisjoint!");
		try {
			for (OWLClass c : rc.disjointConcepts("D"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		rc.addOntology(new File("resources/testRelations.owl").toURI().toURL());

		System.out.println("\nSubsumed!");
		try {
			for (OWLClass c : rc.subsumedConcepts("A"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nSubsuming!");
		try {
			for (OWLClass c : rc.subsumingConcepts("D"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nEquivalent!");
		try {
			for (OWLClass c : rc.equivalentConcepts("F"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nDisjoint!");
		try {
			for (OWLClass c : rc.disjointConcepts("E"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		rc.addOntology(new File("resources/testCollisions.owl").toURI().toURL());

		System.out.println("\nSubsumed!");
		try {
			for (OWLClass c : rc.subsumedConcepts("A"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nSubsuming!");
		try {
			for (OWLClass c : rc.subsumingConcepts("H"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nEquivalent!");
		try {
			for (OWLClass c : rc.equivalentConcepts("B"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\nDisjoint!");
		try {
			for (OWLClass c : rc.disjointConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#B>"))
				System.out.println(ssfp.getShortForm(c));
		} catch (ParserException e) {
			System.err.println(e.getMessage());
		}

	}
}
