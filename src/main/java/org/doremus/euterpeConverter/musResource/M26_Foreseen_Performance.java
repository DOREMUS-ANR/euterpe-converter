package org.doremus.euterpeConverter.musResource;

import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.ontology.CIDOC;
import org.doremus.euterpeConverter.ontology.FRBROO;
import org.doremus.euterpeConverter.ontology.MUS;
import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class M26_Foreseen_Performance extends DoremusResource {
  private E52_TimeSpan timeSpan;

  public M26_Foreseen_Performance(String identifier) throws URISyntaxException {
    super(identifier);
  }

  public static M26_Foreseen_Performance from(Evenement ev) throws URISyntaxException {
    M26_Foreseen_Performance fp = new M26_Foreseen_Performance(ev.id);
    fp.parse(ev);

    return fp;
  }

  private void parse(Evenement ev) throws URISyntaxException {

    this.resource
      .addProperty(DCTerms.identifier, ev.id)
      .addProperty(CIDOC.P102_has_title, ev.titre_ligne1)
      .addProperty(RDFS.label, ev.titre_ligne1);

    for (String t : ev.activite)
      this.resource.addProperty(CIDOC.P2_has_type, t, "fr");

    E53_Place p1 = new E53_Place(ev.etablissement, "etablissement");
    E53_Place p2 = new E53_Place(ev.lieu, "lieu");
    p2.asResource().addProperty(CIDOC.P89_falls_within, p1.asResource());
    this.model.add(p1.model);
    this.model.add(p2.model);
    this.resource.addProperty(CIDOC.P7_took_place_at, p1.asResource())
      .addProperty(CIDOC.P7_took_place_at, p2.asResource());

    if (!ev.titre_ligne2.isEmpty())
      this.resource.addProperty(MUS.U67_has_subtitle, ev.titre_ligne2);

    this.resource.addProperty(FOAF.isPrimaryTopicOf, model.createResource(ev.lien_web.trim()));

    this.addNote(ev.description);

    for (int i = 0; i < ev.date.size(); i++)
      this.addDate(ev.date.get(i), i);


    F25_Performance_Plan performancePlan = new F25_Performance_Plan(ev.id);
    resource.addProperty(MUS.U77_foresees_performing_plan, performancePlan.asResource());
    this.model.add(performancePlan.model);

    for (Oeuvre o : ev.oeuvre) {
      F22_SelfContainedExpression exp = new F22_SelfContainedExpression(o);
      F28_ExpressionCreation expCre = new F28_ExpressionCreation(o, false);
      F14_IndividualWork work = new F14_IndividualWork(o);

      expCre.asResource()
        .addProperty(FRBROO.R17_created, exp.asResource())
        .addProperty(FRBROO.R19_created_a_realisation_of, work.asResource());

      this.model.add(exp.model).add(expCre.model).add(work.model);
    }
  }


  private void addDate(Date d, int i) throws URISyntaxException {
    this.timeSpan = new E52_TimeSpan(new URI(this.uri + "/interval/" + i), d, null);
    this.resource.addProperty(CIDOC.P4_has_time_span, this.timeSpan.asResource());
    this.model.add(this.timeSpan.getModel());
  }
}
