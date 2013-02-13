package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.util.Set;
import junit.framework.TestCase;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Unit test for RelationChecker.
 */
// @RunWith(Suite.class) @SuiteClasses({ RelationCheckerTest.class })
public class RelationCheckerTest {

	private RelationChecker checker;
	private ManchesterOWLSyntaxClassExpressionParser parser;

	@Before
	public void method() throws OWLOntologyCreationException {
		this.checker = new RelationChecker(new File("resources/bank.owl"));
		this.parser = this.checker.getParser();
	}

	@Test
	public void testSubsumed() throws ParserException {
		Set<OWLClass> subsumed = this.checker.subsumedConcepts("Externa");
		TestCase.assertTrue(subsumed.contains(this.parser.parse("Internacional")) && subsumed.contains(this.parser.parse("Nacional")));
	}

	@Test
	public void testSubsuming() throws ParserException {
		Set<OWLClass> subsuming = this.checker.subsumingConcepts("Transferencia");
		TestCase.assertTrue(subsuming.contains(this.parser.parse("Movimiento")));
	}

	@Test
	public void testEquivalent() throws ParserException {
		Set<OWLClass> equivalent = this.checker.equivalentConcepts("tiene_titular some Titular");
		TestCase.assertTrue(equivalent.contains(this.parser.parse("Cuenta")));
	}

	@Test
	public void testDisjoint() throws ParserException {
		Set<OWLClass> disjoint = this.checker.disjointConcepts("Nacional");
		TestCase.assertTrue(disjoint.contains(this.parser.parse("Traspaso")) && disjoint.contains(this.parser.parse("Interna")));
	}

	@Test
	public void testAddition() {
		TestCase.assertTrue(true);
	}

	@Test
	public void testDeletion() {
		TestCase.assertTrue(true);
	}

}
