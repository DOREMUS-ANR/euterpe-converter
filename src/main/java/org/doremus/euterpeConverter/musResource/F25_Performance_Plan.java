package org.doremus.euterpeConverter.musResource;

import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.euterpeConverter.sources.Oeuvre;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;

public class F25_Performance_Plan extends DoremusResource {
  public F25_Performance_Plan(String identifier) {
    super(identifier);
    this.setClass(FRBROO.F25_Performance_Plan);
  }

  public static F25_Performance_Plan from(Evenement ev) {
    F25_Performance_Plan p = new F25_Performance_Plan(ev.getId());
    p.parse(ev);
    return p;
  }

  private void parse(Evenement ev) {
    F28_ExpressionCreation creation = new F28_ExpressionCreation(ev);
    F20_PerformanceWork perfWork = new F20_PerformanceWork(ev);

    creation.addProperty(FRBROO.R17_created, this)
      .addProperty(FRBROO.R19_created_a_realisation_of, perfWork);
    perfWork.addProperty(FRBROO.R12_is_realised_in, this);
    this.model.add(creation.model).add(perfWork.model);

    for (Oeuvre o : ev.oeuvre) parseWork(o);
  }

  private void parseWork(Oeuvre o) {
    F22_SelfContainedExpression exp = new F22_SelfContainedExpression(o);
    F28_ExpressionCreation expCre = new F28_ExpressionCreation(o);
    F14_IndividualWork work = new F14_IndividualWork(o);

    expCre
      .addProperty(FRBROO.R17_created, exp)
      .addProperty(FRBROO.R19_created_a_realisation_of, work);

    this.addProperty(CIDOC.P165_incorporates, exp);
    this.model.add(expCre.model);
  }

}
