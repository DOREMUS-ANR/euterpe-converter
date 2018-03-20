package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Formation;

import java.net.URISyntaxException;

public class F11_Corporate_Body extends DoremusResource {

  public F11_Corporate_Body(Formation formation) throws URISyntaxException {
    super(formation.id);
    String name = formation.label.trim();

    resource.addProperty(RDF.type, FRBROO.F11_Corporate_Body)
      .addProperty(RDFS.label, name)
      .addProperty(CIDOC.P131_is_identified_by, name);
  }
}
