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
public class MereologyCheckerTest {

	private SingleOntologyRelationChecker singleChecker;

	@Before
	public void method() throws OWLOntologyCreationException, MalformedURLException, IOException, URISyntaxException {
		this.singleChecker = new SingleOntologyRelationChecker(new File("resources/testMereology.owl"));
	}

	@Test
	public void testContainersDirect() throws ParserException {
		Set<OWLClass> contained = this.singleChecker.containerConcepts("A1");
		TestCase.assertTrue(contained.contains(this.singleChecker.parse("A")));
	}

	@Test
	public void testContainers() throws ParserException {
		Set<OWLClass> contained = this.singleChecker.containerConcepts("A11");
		TestCase.assertTrue(contained.contains(this.singleChecker.parse("A")));
	}

	@Test
	public void testContainersInferred() throws ParserException {
		Set<OWLClass> contained = this.singleChecker.containerConcepts("A11");
		TestCase.assertTrue(contained.contains(this.singleChecker.parse("B")));
	}

}
