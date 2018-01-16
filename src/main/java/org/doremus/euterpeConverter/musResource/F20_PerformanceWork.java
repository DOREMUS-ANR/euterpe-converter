package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.doremus.euterpeConverter.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F20_PerformanceWork extends DoremusResource {

  public F20_PerformanceWork(Evenement ev) throws URISyntaxException {
    super(ev.id);
    this.resource.addProperty(RDF.type, FRBROO.F20_Performance_Work);
  }

}
