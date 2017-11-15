package org.doremus.euterpeConverter.musResource;


import org.apache.jena.vocabulary.RDF;
import org.doremus.euterpeConverter.ontology.FRBROO;

import java.net.URISyntaxException;

public class F25_Performance_Plan extends DoremusResource {
  public F25_Performance_Plan(String identifier) throws URISyntaxException {
    super(identifier);

    this.resource.addProperty(RDF.type, FRBROO.F25_Performance_Plan);
  }
}
