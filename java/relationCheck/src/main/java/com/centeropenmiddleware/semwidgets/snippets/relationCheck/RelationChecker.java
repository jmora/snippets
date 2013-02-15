package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.util.Set;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Relation Checker
 * 
 * <P>
 * Class to check the relation of any number of concepts with those in some ontology. This is a small snippet, no fancy exception handling, etc.
 * 
 */
public abstract class RelationChecker {

	protected OWLReasoner reasoner;
	protected ManchesterOWLSyntaxClassExpressionParser parser;
	protected ManchesterOWLSyntaxClassExpressionParser fullParser;

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
		try {
			return this.parser.parse(classExpressionString);
		} catch (ParserException e) {
			return this.fullParser.parse(classExpressionString);
		}
	}

	public OWLClassExpression parse(String classExpressionString) throws ParserException {
		return this.getCandidate(classExpressionString);
	}

}
