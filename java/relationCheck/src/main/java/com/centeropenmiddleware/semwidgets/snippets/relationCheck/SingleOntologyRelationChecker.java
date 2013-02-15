package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.File;
import java.util.Collections;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;


public class SingleOntologyRelationChecker extends RelationChecker {

	private OWLOntologyManager ontologyManager;
	private OWLDataFactory factory;

	public SingleOntologyRelationChecker(File ontologyFile) throws OWLOntologyCreationException {
		this.ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = this.ontologyManager.loadOntologyFromOntologyDocument(ontologyFile);
		this.reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
		this.reasoner.precomputeInferences();
		this.factory = this.ontologyManager.getOWLDataFactory();
		OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager, Collections.singleton(ontology),
				new SimpleShortFormProvider()));
		this.parser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec);
		OWLEntityChecker oec2 = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager, Collections.singleton(ontology),
				new ManchesterOWLSyntaxPrefixNameShortFormProvider(this.ontologyManager, ontology)));
		this.fullParser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec2);
	}
}
