package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;


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
	private OWLOntologyManager ontologyManager;
	private ManchesterOWLSyntaxClassExpressionParser parser;
	private IRI mergedIRI;
	private OWLOntologyMerger ontologyMerger;
	private OWLOntology mergedOntology;

	public RelationChecker(File ontologyFile) throws OWLOntologyCreationException {
		this.ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = this.ontologyManager.loadOntologyFromOntologyDocument(ontologyFile);
		this.reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
		this.reasoner.precomputeInferences();
		this.factory = this.ontologyManager.getOWLDataFactory();
		OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager, Collections.singleton(ontology),
				new SimpleShortFormProvider()));
		this.parser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec);
	}

	public RelationChecker() throws OWLOntologyCreationException {
		this.ontologyManager = OWLManager.createOWLOntologyManager();
		this.mergedIRI = IRI.create("http://centeropenmiddleware.com/semwidgets/merged");
		this.factory = this.ontologyManager.getOWLDataFactory();
		this.ontologyMerger = new OWLOntologyMerger(this.ontologyManager);
		this.mergedOntology = this.ontologyMerger.createMergedOntology(this.ontologyManager, this.mergedIRI);
		this.updateReasoner();
	}

	private void updateReasoner() throws OWLOntologyCreationException {
		// TODO: move some statements to constructor and check whether it still works (I don't know what these objects do internally)
		this.ontologyManager.removeOntology(this.mergedOntology);
		this.mergedOntology = this.ontologyMerger.createMergedOntology(this.ontologyManager, this.mergedIRI);
		this.reasoner = new Reasoner.ReasonerFactory().createReasoner(this.mergedOntology);
		this.reasoner.precomputeInferences();
		ManchesterOWLSyntaxPrefixNameShortFormProvider mospnsfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(this.ontologyManager, this.mergedOntology);
		// OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager,
		// this.ontologyManager.getOntologies(), mospnsfp));
		OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager,
				Collections.singleton(this.mergedOntology), mospnsfp));
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
		return this.parser.parse(classExpressionString);
	}

	public ManchesterOWLSyntaxClassExpressionParser getParser() {
		return this.parser;
	}

	public void addOntology(URL ontologyURL) throws IOException, OWLOntologyCreationException, URISyntaxException {
		OWLOntology newOntology = this.ontologyManager.loadOntology(IRI.create(ontologyURL));
		this.updateReasoner();
		DefaultPrefixManager pm = new DefaultPrefixManager();
		SimpleShortFormProvider ssfp = new SimpleShortFormProvider();
		System.out.println("wtf");
		for (OWLClass c : this.mergedOntology.getClassesInSignature()) {
			System.out.println(c.toStringID());
			System.out.println(pm.getShortForm(c));
			System.out.println(ssfp.getShortForm(c));
		}
		System.out.println("really");
		for (OWLClass c : newOntology.getClassesInSignature()) {
			System.out.println(c.toStringID());
			System.out.println(pm.getShortForm(c));
			System.out.println(ssfp.getShortForm(c));
		}

	}

	public void removeOntology(URL ontologyURL) throws URISyntaxException, OWLOntologyCreationException {
		this.ontologyManager.removeOntology(this.ontologyManager.getOntology(IRI.create(ontologyURL)));
		this.updateReasoner();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.ontologyManager;
	}

}
