package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.sources.Oeuvre;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;

public class F22_SelfContainedExpression extends DoremusResource {
  public F22_SelfContainedExpression(Oeuvre oeuvre) {
    super(oeuvre.getId());

    String title = oeuvre.titre.trim()
      .replaceAll("(?i)^bis ?:", "").trim();

    this.setClass(FRBROO.F22_Self_Contained_Expression);
    this.addProperty(DC.identifier, oeuvre.getId());

    if (!title.isEmpty())
      this.addProperty(CIDOC.P102_has_title, title)
        .addProperty(RDFS.label, title);
  }
}
