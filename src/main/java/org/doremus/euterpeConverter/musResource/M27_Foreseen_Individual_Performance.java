package org.doremus.euterpeConverter.musResource;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.sources.Formation;
import org.doremus.euterpeConverter.sources.Intervenant;
import org.doremus.ontology.MUS;
import org.doremus.string2vocabulary.VocabularyManager;

import java.net.URI;

public class M27_Foreseen_Individual_Performance extends DoremusResource {
  public boolean isARole;

  public M27_Foreseen_Individual_Performance(URI uri, int incrementer, Formation formation) {
    super();
    this.uri = URI.create(uri + "/" + incrementer);
    this.resource = model.createResource(this.uri.toString());
    this.setClass(MUS.M27_Foreseen_Individual_Performance);

    F11_Corporate_Body body = new F11_Corporate_Body(formation);
    this.resource.addProperty(MUS.U6_foresees_actor, body.asResource());
    this.model.add(body.getModel());

    // TODO add a role based on the label (i.e. if contains "orchestra") ?
  }

  public M27_Foreseen_Individual_Performance(URI uri, int incrementer, Intervenant intervenant, String role) {
    super();
    this.uri = URI.create(uri + "/" + incrementer);
    this.regenerateResource(this.uri);
    this.setClass(MUS.M27_Foreseen_Individual_Performance);
    this.isARole = true;

    E21_Person person = new E21_Person(intervenant);
    this.resource.addProperty(MUS.U6_foresees_actor, person.asResource());
    this.resource.addProperty(RDFS.comment, role, "fr");

    if ("soliste".equals(role)) {
      this.resource.addProperty(MUS.U36_foresees_responsibility, "soliste", "fr");
      return;
    }

    if ("direction".equals(role)) {
      this.resource.addProperty(MUS.U35_foresees_function, "conductor", "en");
      return;
    }

    Resource match = VocabularyManager.searchInCategory(role, "fr", "mop");
    if (match != null) {
      this.resource.addProperty(MUS.U2_foresees_use_of_medium_of_performance, match);
      return;
    }

    match = VocabularyManager.searchInCategory(role, "fr", "function");
    if (match != null) {
      this.resource.addProperty(MUS.U35_foresees_function, match);
      return;
    }

    this.isARole = false;

    this.model.add(person.model);

  }
}
