package com.github.jmora.snippets.licensesManager;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;


public class LicensesChecker extends MultiOntologyRelationChecker {

	private IRI newLicenses;
	private OWLOntology definedLicenses;

	public LicensesChecker(Collection<URL> licenses) throws OWLOntologyCreationException, URISyntaxException {
		super(licenses);
		this.newLicenses = IRI.create("http://github.com/jmora/licenses/newlyDefined/");
		this.definedLicenses = this.ontologyManager.createOntology(this.newLicenses);
	}

	public Set<OWLClass> getCompatibleLicenses(String license) throws ParserException {
		Set<OWLClass> licenses = this.subsumedConcepts("License");
		Set<OWLClass> potentialLicenses = this.subsumingConcepts(license);
		potentialLicenses.retainAll(licenses);
		return potentialLicenses;
	}

	public Set<String> createMergedLicense(Collection<String> licenses, String LicenseName) throws ParserException {
		ManchesterOWLSyntaxPrefixNameShortFormProvider urier = new ManchesterOWLSyntaxPrefixNameShortFormProvider(new DefaultPrefixManager());
		SimpleShortFormProvider ssfp = new SimpleShortFormProvider();
		HashSet<String> res = new HashSet<String>();
		for (String l : licenses)
			this.ontologyManager.applyChange(new AddAxiom(this.definedLicenses, this.factory.getOWLSubClassOfAxiom(this.parseAdd(LicenseName), this.parse(l))));
		Set<OWLClass> obligations = this.subsumedConcepts("Obligation");
		for (OWLClass c : this.subsumedConcepts("Right")) {
			String partRes = "You have the right to " + ssfp.getShortForm(c) + " given that you ";
			Set<OWLClass> potentialObligations = this.subsumingConcepts(urier.getShortForm(c) + " and " + this.parseAdd(LicenseName));
			potentialObligations.retainAll(obligations);
			for (OWLClass obligation : potentialObligations)
				partRes += ssfp.getShortForm(obligation) + " and ";
			res.add(partRes);
		}
		return res;
	}

	private OWLClassExpression parseAdd(String license) {
		OWLClassExpression classOWL;
		try {
			classOWL = this.parse(license);
		} catch (ParserException e) {
			classOWL = this.factory.getOWLClass(license, new DefaultPrefixManager(this.newLicenses.toString()));
			this.ontologyManager.applyChange(new AddAxiom(this.definedLicenses, this.factory.getOWLDeclarationAxiom(classOWL.asOWLClass())));
		}
		return classOWL;
	}
}
