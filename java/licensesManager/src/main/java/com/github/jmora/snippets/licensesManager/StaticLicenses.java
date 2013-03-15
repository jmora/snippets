package com.github.jmora.snippets.licensesManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


public class StaticLicenses {

	private static Set<URL> predefinedLicenses;
	private static SimpleShortFormProvider ssfp = new SimpleShortFormProvider();

	public static Map<String, Collection<String>> getCompatibleLicenses(URL newLicense) throws OWLOntologyCreationException, URISyntaxException,
			ParserException, IOException {
		HashMap<String, Collection<String>> correspondences = new HashMap<String, Collection<String>>();
		SingleOntologyRelationChecker newOnto = new SingleOntologyRelationChecker(newLicense);
		MultiOntologyRelationChecker allLicenses = new MultiOntologyRelationChecker(StaticLicenses.predefinedLicenses);
		allLicenses.addOntology(newLicense);
		for (OWLClass license : newOnto.subsumedConcepts(newOnto.parse("License")))
			correspondences.put(StaticLicenses.ssfp.getShortForm(license), StaticLicenses.mapGetShortForm(allLicenses.subsumingConcepts(license)));
		return correspondences;
	}

	public static Set<String> getCompatibleLicenses(URL newLicense, String licenseName) throws OWLOntologyCreationException, URISyntaxException, IOException,
			ParserException {
		MultiOntologyRelationChecker allLicenses = new MultiOntologyRelationChecker(StaticLicenses.predefinedLicenses);
		allLicenses.addOntology(newLicense);
		return StaticLicenses.mapGetShortForm(allLicenses.subsumingConcepts(licenseName));
	}

	private static Set<String> mapGetShortForm(Collection<OWLClass> classes) {
		HashSet<String> res = new HashSet<String>();
		for (OWLClass c : classes)
			res.add(StaticLicenses.ssfp.getShortForm(c));
		return res;
	}

	public static Collection<String> getMergedLicense(Collection<URL> licenses) {
		HashSet<String> res = new HashSet<String>();

		return res;
	}

}
