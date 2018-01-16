package org.doremus.euterpeConverter.musResource;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.ontology.MUS;
import org.doremus.euterpeConverter.sources.Formation;
import org.doremus.euterpeConverter.sources.Intervenant;
import org.doremus.string2vocabulary.VocabularyManager;

import java.net.URI;
import java.net.URISyntaxException;

public class M27_Foreseen_Individual_Performance extends DoremusResource {
  public boolean isARole;

  public M27_Foreseen_Individual_Performance(URI uri, int incrementer, Formation formation) throws URISyntaxException {
    super();
    this.uri = URI.create(uri + "/" + incrementer);
    this.regenerateResource();
    this.resource.addProperty(RDF.type, MUS.M27_Foreseen_Individual_Performance);

    F11_Corporate_Body body = new F11_Corporate_Body(formation);
    this.resource.addProperty(MUS.U6_foresees_actor, body.asResource());
    this.model.add(body.getModel());

    // TODO add a role based on the label (i.e. if contains "orchestra") ?
  }

  public M27_Foreseen_Individual_Performance(URI uri, int incrementer, Intervenant intervenant, String role) throws URISyntaxException {
    super();
    this.uri = URI.create(uri + "/" + incrementer);
    this.regenerateResource();
    this.resource.addProperty(RDF.type, MUS.M27_Foreseen_Individual_Performance);
    this.isARole = true;

    E21_Person person = new E21_Person(intervenant);
    this.resource.addProperty(MUS.U6_foresees_actor, person.asResource());
    this.resource.addProperty(RDFS.comment, role,"fr");

    if ("soliste".equals(role)) {
      this.resource.addProperty(MUS.U36_foresees_responsibility_of_type, "soliste", "fr");
      return;
    }

    if ("direction".equals(role)) {
      this.resource.addProperty(MUS.U35_foresees_function_of_type, "chef d'orchestre", "fr");
      return;
    }

    Resource match = VocabularyManager.searchInCategory(role, "fr", "mop");
    if (match != null) {
      this.resource.addProperty(MUS.U2_foresees_use_of_medium_of_performance_of_type, match);
      return;
    }

    match = VocabularyManager.searchInCategory(role, "fr", "function");
    if (match != null) {
      this.resource.addProperty(MUS.U35_foresees_function_of_type, match);
      return;
    }

    this.isARole = false;

    this.model.add(person.model);

  }
}
