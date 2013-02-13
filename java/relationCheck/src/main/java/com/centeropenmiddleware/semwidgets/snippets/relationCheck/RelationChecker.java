package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Relation Checker
 * 
 * <P>
 * Class to check the relation of any number of concepts with those in some ontology. This is a small snippet, no fancy exception handling, etc.
 * 
 */
public class RelationChecker {

	private OWLReasoner reasoner;
	private OWLDataFactory factory;
	private ManchesterOWLSyntaxClassExpressionParser parser;

	public RelationChecker(File ontologyFile) throws OWLOntologyCreationException {
		OWLOntologyManager ontoManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontoManager.loadOntologyFromOntologyDocument(ontologyFile);
		this.reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
		this.reasoner.precomputeInferences();
		this.factory = ontoManager.getOWLDataFactory();
		OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(ontoManager, Collections.singleton(ontology),
				new SimpleShortFormProvider()));
		this.parser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec);

	}

	public Set<OWLClass> subsumedConcepts(String classExpressionString) throws ParserException {
		OWLClassExpression candidate = this.getCandidate(classExpressionString);
		return this.reasoner.getSubClasses(candidate, false).getFlattened();
	}

	public Set<OWLClass> subsumingConcepts(String classExpressionString) throws ParserException {
		OWLClassExpression candidate = this.getCandidate(classExpressionString);
		return this.reasoner.getSuperClasses(candidate, false).getFlattened();
	}

	public Set<OWLClass> equivalentConcepts(String classExpressionString) throws ParserException {
		OWLClassExpression candidate = this.getCandidate(classExpressionString);
		return this.reasoner.getEquivalentClasses(candidate).getEntities();
	}

	public Set<OWLClass> disjointConcepts(String classExpressionString) throws ParserException {
		OWLClassExpression candidate = this.getCandidate(classExpressionString);
		return this.reasoner.getDisjointClasses(candidate).getFlattened();
	}

	private OWLClassExpression getCandidate(String classExpressionString) throws ParserException {
		// ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(this.factory, classExpressionString);
		// return parser.parseClassExpression();
		return this.parser.parse(classExpressionString);
	}

	public ManchesterOWLSyntaxClassExpressionParser getParser() {
		return this.parser;
	}

}
