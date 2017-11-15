package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.ontology.CIDOC;
import org.doremus.euterpeConverter.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F22_SelfContainedExpression extends DoremusResource {
  public F22_SelfContainedExpression(Oeuvre oeuvre) throws URISyntaxException {
    super(oeuvre.id);

    this.resource.addProperty(RDF.type, FRBROO.F22_Self_Contained_Expression)
      .addProperty(CIDOC.P102_has_title, oeuvre.titre.trim())
      .addProperty(RDFS.label, oeuvre.titre.trim());
  }

}
