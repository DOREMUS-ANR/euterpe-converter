package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F22_SelfContainedExpression extends DoremusResource {
  public F22_SelfContainedExpression(Oeuvre oeuvre) throws URISyntaxException {
    super(oeuvre.id);

    String title = oeuvre.titre.trim()
      .replaceAll("(?i)^bis ?:", "").trim();

    this.resource.addProperty(RDF.type, FRBROO.F22_Self_Contained_Expression)
      .addProperty(DCTerms.identifier, oeuvre.id)
      .addProperty(CIDOC.P102_has_title, title)
      .addProperty(RDFS.label, title);
  }

}
