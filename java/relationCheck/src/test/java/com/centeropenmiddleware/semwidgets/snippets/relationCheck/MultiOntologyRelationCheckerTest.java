package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Set;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Unit test for RelationChecker.
 */
public class MultiOntologyRelationCheckerTest {

	private MultiOntologyRelationChecker multiChecker;

	@Before
	public void method() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		this.multiChecker = new MultiOntologyRelationChecker();
		this.multiChecker.addOntology(new File("resources/testNames.owl").toURI().toURL());
		this.multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
	}

	@Test
	public void testSubsumedAdd() throws ParserException {
		Set<OWLClass> subsumed = this.multiChecker.subsumedConcepts("A");
		TestCase.assertTrue(subsumed.contains(this.multiChecker.parse("D")));
	}

	@Test
	public void testSubsumingAdd() throws ParserException {
		Set<OWLClass> subsuming = this.multiChecker.subsumingConcepts("F");
		TestCase.assertTrue(subsuming.contains(this.multiChecker.parse("A")));
	}

	@Test
	public void testEquivalentAdd() throws ParserException, OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		this.multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("D");
		TestCase.assertTrue(equivalent.contains(this.multiChecker.parse("F")));
	}

	@Test
	public void testDisjointAdd() throws ParserException {
		Set<OWLClass> disjoint = this.multiChecker.disjointConcepts("C");
		TestCase.assertTrue(disjoint.contains(this.multiChecker.parse("G")));
	}

	@Test
	public void testSubsumedRemove() throws OWLOntologyCreationException, ParserException, URISyntaxException, IOException {
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> subsumed = this.multiChecker.subsumedConcepts("A");
		TestCase.assertFalse(subsumed.contains(this.multiChecker.parse("D")));
	}

	@Test
	public void testSubsumingRemove() throws OWLOntologyCreationException, ParserException, URISyntaxException, IOException {
		this.multiChecker.addOntology(new File("resources/testRelations.owl").toURI().toURL());
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> subsuming = this.multiChecker.subsumingConcepts("D");
		TestCase.assertFalse(subsuming.contains(this.multiChecker.parse("A")));
	}

	@Test(expected = ParserException.class)
	public void testEquivalentRemove() throws OWLOntologyCreationException, MalformedURLException, URISyntaxException, ParserException {
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("D");
		TestCase.assertFalse(equivalent.contains(this.multiChecker.parse("F"))); // F is not even there anymore
	}

	@Test
	public void testDisjointRemove() throws OWLOntologyCreationException, MalformedURLException, URISyntaxException, ParserException {
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> disjoint = this.multiChecker.disjointConcepts("C");
		TestCase.assertFalse(disjoint.contains(this.multiChecker.parse("G")));
	}

	@Test
	public void testSubsumedCollision() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException, ParserException {
		this.multiChecker.addOntology(new File("resources/testCollisions.owl").toURI().toURL());
		this.multiChecker.removeOntology(new File("resources/testRelations.owl").toURI().toURL());
		Set<OWLClass> subsumed = this.multiChecker.subsumedConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#A>");
		TestCase.assertTrue(subsumed.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#B>")));
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#B>");
		TestCase.assertTrue(equivalent.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#D>")));
		subsumed = this.multiChecker.subsumedConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#A>");
		TestCase.assertFalse(subsumed.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#D>")));
		subsumed = this.multiChecker.subsumedConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#A>");
		TestCase.assertFalse(subsumed.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#D>")));
	}

	@Test
	public void testSubsumingCollision() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException, ParserException {
		this.multiChecker.addOntology(new File("resources/testCollisions.owl").toURI().toURL());
		Set<OWLClass> subsuming = this.multiChecker.subsumingConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#H>");
		TestCase.assertTrue(subsuming.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#B>")));
		TestCase.assertFalse(subsuming.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#A>")));
	}

	@Test
	public void testEquivalentCollision() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException, ParserException {
		this.multiChecker.addOntology(new File("resources/testCollisions.owl").toURI().toURL());
		Set<OWLClass> disjoint = this.multiChecker.disjointConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#D>");
		TestCase.assertTrue(disjoint.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#B>")));
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#D>");
		TestCase.assertTrue(equivalent.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#B>")));
	}

	@Test
	public void testDisjointCollision() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException, ParserException {
		this.multiChecker.addOntology(new File("resources/testCollisions.owl").toURI().toURL());
		Set<OWLClass> disjoint = this.multiChecker.disjointConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#B>");
		TestCase.assertTrue(disjoint.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testNames#D>")));
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#B>");
		TestCase.assertTrue(equivalent.contains(this.multiChecker.parse("<http://www.centeropenmiddleware.com/ontology/tests/jmora/testCollisions#D>")));
	}

}
