package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.doremus.euterpeConverter.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F14_IndividualWork extends DoremusResource {

  public F14_IndividualWork(Oeuvre o) throws URISyntaxException {
    super(o.id);
    this.resource.addProperty(RDF.type, FRBROO.F14_Individual_Work);
  }

}
