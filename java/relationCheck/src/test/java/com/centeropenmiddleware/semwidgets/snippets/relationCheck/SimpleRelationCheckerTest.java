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
public class SimpleRelationCheckerTest {

	private MultiOntologyRelationChecker multiChecker;
	private SingleOntologyRelationChecker singleChecker;

	@Before
	public void method() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		// this.checker = new RelationChecker(new File("resources/bank.owl")); this.parser = this.checker.getParser();
		this.singleChecker = new SingleOntologyRelationChecker(new File("resources/bank.owl"));
		this.multiChecker = new MultiOntologyRelationChecker();
		this.multiChecker.addOntology(new File("resources/bank.owl").toURI().toURL());
	}

	@Test
	public void testSubsumedM() throws ParserException {
		Set<OWLClass> subsumed = this.multiChecker.subsumedConcepts("Externa");
		TestCase.assertTrue(subsumed.contains(this.multiChecker.parse("Internacional")) && subsumed.contains(this.multiChecker.parse("Nacional")));
	}

	@Test
	public void testSubsumedS() throws ParserException {
		Set<OWLClass> subsumed = this.singleChecker.subsumedConcepts("Externa");
		TestCase.assertTrue(subsumed.contains(this.singleChecker.parse("Internacional")) && subsumed.contains(this.singleChecker.parse("Nacional")));
	}

	@Test
	public void testSubsumingM() throws ParserException {
		Set<OWLClass> subsuming = this.multiChecker.subsumingConcepts("Transferencia");
		TestCase.assertTrue(subsuming.contains(this.multiChecker.parse("Movimiento")));
	}

	@Test
	public void testSubsumingS() throws ParserException {
		Set<OWLClass> subsuming = this.singleChecker.subsumingConcepts("Transferencia");
		TestCase.assertTrue(subsuming.contains(this.singleChecker.parse("Movimiento")));
	}

	@Test
	public void testEquivalentM() throws ParserException {
		Set<OWLClass> equivalent = this.multiChecker.equivalentConcepts("tiene_titular some Titular");
		TestCase.assertTrue(equivalent.contains(this.multiChecker.parse("Cuenta")));
	}

	@Test
	public void testEquivalentS() throws ParserException {
		Set<OWLClass> equivalent = this.singleChecker.equivalentConcepts("tiene_titular some Titular");
		TestCase.assertTrue(equivalent.contains(this.singleChecker.parse("Cuenta")));
	}

	@Test
	public void testDisjointM() throws ParserException {
		Set<OWLClass> disjoint = this.multiChecker.disjointConcepts("Nacional");
		TestCase.assertTrue(disjoint.contains(this.multiChecker.parse("Traspaso")) && disjoint.contains(this.multiChecker.parse("Interna")));
	}

	@Test
	public void testDisjointS() throws ParserException {
		Set<OWLClass> disjoint = this.singleChecker.disjointConcepts("Nacional");
		TestCase.assertTrue(disjoint.contains(this.singleChecker.parse("Traspaso")) && disjoint.contains(this.singleChecker.parse("Interna")));
	}

}
