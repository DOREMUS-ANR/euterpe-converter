package org.doremus.euterpeConverter.musResource;


import org.apache.jena.vocabulary.RDF;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F25_Performance_Plan extends DoremusResource {
  public F25_Performance_Plan(String identifier) throws URISyntaxException {
    super(identifier);
    this.resource.addProperty(RDF.type, FRBROO.F25_Performance_Plan);
  }

  public static F25_Performance_Plan from(Evenement ev) throws URISyntaxException {
    F25_Performance_Plan p = new F25_Performance_Plan(ev.id);
    p.parse(ev);
    return p;
  }

  private void parse(Evenement ev) throws URISyntaxException {
    F28_ExpressionCreation creation = new F28_ExpressionCreation(ev);
    F20_PerformanceWork perfWork = new F20_PerformanceWork(ev);

    creation.asResource().addProperty(FRBROO.R17_created, this.asResource())
      .addProperty(FRBROO.R19_created_a_realisation_of, perfWork.asResource());
    perfWork.asResource().addProperty(FRBROO.R12_is_realised_in, this.asResource());
    this.model.add(creation.model).add(perfWork.model);

    for (Oeuvre o : ev.oeuvre) parseWork(o);
  }

  private void parseWork(Oeuvre o) throws URISyntaxException {
    F22_SelfContainedExpression exp = new F22_SelfContainedExpression(o);
    F28_ExpressionCreation expCre = new F28_ExpressionCreation(o);
    F14_IndividualWork work = new F14_IndividualWork(o);

    expCre.asResource()
      .addProperty(FRBROO.R17_created, exp.asResource())
      .addProperty(FRBROO.R19_created_a_realisation_of, work.asResource());

    this.resource.addProperty(CIDOC.P165_incorporates, exp.asResource());
    this.model.add(exp.model).add(expCre.model).add(work.model);
  }

}
