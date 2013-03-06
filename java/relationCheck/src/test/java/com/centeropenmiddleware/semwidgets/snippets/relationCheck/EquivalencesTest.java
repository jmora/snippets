package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;
import junit.framework.TestCase;
import org.junit.Test;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Unit test for RelationChecker.
 */
public class EquivalencesTest {

	@Test
	public void testEquivalentAdd() throws ParserException, OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		MultiOntologyRelationChecker multiChecker;
		multiChecker = new MultiOntologyRelationChecker();
		multiChecker.addOntology(new File("resources/testNames.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testMereology.owl").toURI().toURL());
		Set<OWLClass> equivalent = multiChecker.equivalentConcepts("D");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("F")));
		ArrayList<String> newEqs = new ArrayList<String>();
		newEqs.add("F");
		newEqs.add("partOf some E");
		multiChecker.addEquivalences(newEqs);
		newEqs = new ArrayList<String>();
		newEqs.add("partOf some E");
		newEqs.add("J");
		multiChecker.addEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("J")));
	}

	@Test
	public void testEquivalentRemove() throws ParserException, OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		MultiOntologyRelationChecker multiChecker;
		multiChecker = new MultiOntologyRelationChecker();
		multiChecker.addOntology(new File("resources/testNames.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testMereology.owl").toURI().toURL());
		Set<OWLClass> equivalent = multiChecker.equivalentConcepts("D");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("F")));
		ArrayList<String> newEqs = new ArrayList<String>();
		newEqs.add("F");
		newEqs.add("partOf some E");
		multiChecker.addEquivalences(newEqs);
		newEqs = new ArrayList<String>();
		newEqs.add("partOf some E");
		newEqs.add("J");
		multiChecker.addEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("J")));
		multiChecker.removeEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertFalse(equivalent.contains(multiChecker.parse("J")));
	}

	@Test
	public void testEquivalentPurge() throws ParserException, OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		MultiOntologyRelationChecker multiChecker;
		multiChecker = new MultiOntologyRelationChecker();
		multiChecker.addOntology(new File("resources/testNames.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testMereology.owl").toURI().toURL());
		Set<OWLClass> equivalent = multiChecker.equivalentConcepts("D");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("F")));
		ArrayList<String> newEqs = new ArrayList<String>();
		newEqs.add("F");
		newEqs.add("partOf some E");
		multiChecker.addEquivalences(newEqs);
		newEqs = new ArrayList<String>();
		newEqs.add("partOf some E");
		newEqs.add("J");
		multiChecker.addEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("J")));
		multiChecker.removeEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertFalse(equivalent.contains(multiChecker.parse("J")));
		newEqs = new ArrayList<String>();
		newEqs.add("J");
		multiChecker.purgeAuxiliarConcepts(newEqs);
	}

	@Test(expected = ParserException.class)
	public void testEquivalentPurgeKills() throws ParserException, OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		MultiOntologyRelationChecker multiChecker;
		multiChecker = new MultiOntologyRelationChecker();
		multiChecker.addOntology(new File("resources/testNames.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		multiChecker.addOntology(new File("resources/testMereology.owl").toURI().toURL());
		Set<OWLClass> equivalent = multiChecker.equivalentConcepts("D");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("F")));
		ArrayList<String> newEqs = new ArrayList<String>();
		newEqs.add("F");
		newEqs.add("partOf some E");
		multiChecker.addEquivalences(newEqs);
		newEqs = new ArrayList<String>();
		newEqs.add("partOf some E");
		newEqs.add("J");
		multiChecker.addEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertTrue(equivalent.contains(multiChecker.parse("J")));
		multiChecker.removeEquivalences(newEqs);
		equivalent = multiChecker.equivalentConcepts("F");
		TestCase.assertFalse(equivalent.contains(multiChecker.parse("J")));
		newEqs = new ArrayList<String>();
		newEqs.add("J");
		multiChecker.purgeAuxiliarConcepts(newEqs);
		multiChecker.parse("J");
	}

}
