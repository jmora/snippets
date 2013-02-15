package com.centeropenmiddleware.semwidgets.snippets.relationCheck;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;


public class MultiOntologyRelationChecker extends RelationChecker {

	private IRI mergedIRI;
	private OWLOntologyMerger ontologyMerger;
	private OWLOntology mergedOntology;
	private OWLOntologyManager ontologyManager;
	private OWLDataFactory factory;
	private HashMap<URL, OWLOntology> ontologiesUsed;

	public MultiOntologyRelationChecker() throws OWLOntologyCreationException {
		this.ontologyManager = OWLManager.createOWLOntologyManager();
		this.mergedIRI = IRI.create("http://centeropenmiddleware.com/semwidgets/merged");
		this.factory = this.ontologyManager.getOWLDataFactory();
		this.ontologyMerger = new OWLOntologyMerger(this.ontologyManager);
		this.mergedOntology = this.ontologyMerger.createMergedOntology(this.ontologyManager, this.mergedIRI);
		this.updateReasoner();
		this.ontologiesUsed = new HashMap<URL, OWLOntology>();
	}

	private void updateReasoner() throws OWLOntologyCreationException {
		this.ontologyManager.removeOntology(this.mergedOntology);
		this.mergedOntology = this.ontologyMerger.createMergedOntology(this.ontologyManager, this.mergedIRI);
		this.reasoner = new Reasoner.ReasonerFactory().createReasoner(this.mergedOntology);
		this.reasoner.precomputeInferences();
		OWLEntityChecker oec = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager,
				Collections.singleton(this.mergedOntology), new SimpleShortFormProvider()));
		this.parser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec);
		OWLEntityChecker oec2 = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(this.ontologyManager,
				Collections.singleton(this.mergedOntology), new ManchesterOWLSyntaxPrefixNameShortFormProvider(this.ontologyManager, this.mergedOntology)));
		this.fullParser = new ManchesterOWLSyntaxClassExpressionParser(this.factory, oec2);
	}

	public void addOntology(URL ontologyURL) throws IOException, OWLOntologyCreationException, URISyntaxException {
		if (this.ontologiesUsed.containsKey(ontologyURL))
			return;
		OWLOntology partRes = this.ontologyManager.loadOntology(IRI.create(ontologyURL));
		this.ontologiesUsed.put(ontologyURL, partRes);
		this.updateReasoner();
	}

	public void removeOntology(URL ontologyURL) throws OWLOntologyCreationException, URISyntaxException {
		if (!this.ontologiesUsed.containsKey(ontologyURL))
			return;
		this.ontologyManager.removeOntology(this.ontologiesUsed.get(ontologyURL));
		this.ontologiesUsed.remove(ontologyURL);
		this.updateReasoner();
	}
}
