package com.github.jmora.snippets.licensesManager;
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

	public Set<OWLClass> subsumedConcepts(OWLClassExpression owlClass) throws ParserException {
		return this.reasoner.getSubClasses(owlClass, false).getFlattened();
	}

	public Set<OWLClass> subsumingConcepts(OWLClassExpression owlClass) throws ParserException {
		return this.reasoner.getSuperClasses(owlClass, false).getFlattened();
	}

	public Set<OWLClass> equivalentConcepts(OWLClassExpression owlClass) throws ParserException {
		return this.reasoner.getEquivalentClasses(owlClass).getEntities();
	}

	public Set<OWLClass> disjointConcepts(OWLClassExpression owlClass) throws ParserException {
		return this.reasoner.getDisjointClasses(owlClass).getFlattened();
	}

	public Set<OWLClass> subsumedConcepts(String classExpressionString) throws ParserException {
		return this.subsumedConcepts(this.getCandidate(classExpressionString));
	}

	public Set<OWLClass> subsumingConcepts(String classExpressionString) throws ParserException {
		return this.subsumingConcepts(this.getCandidate(classExpressionString));
	}

	public Set<OWLClass> equivalentConcepts(String classExpressionString) throws ParserException {
		return this.equivalentConcepts(this.getCandidate(classExpressionString));
	}

	public Set<OWLClass> disjointConcepts(String classExpressionString) throws ParserException {
		return this.disjointConcepts(this.getCandidate(classExpressionString));
	}

	public Set<OWLClass> containerConcepts(String classExpressionString) throws ParserException {
		OWLClassExpression candidate = this.getCandidate("hasPart some (" + classExpressionString + ")");
		return this.reasoner.getSubClasses(candidate, false).getFlattened();
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
