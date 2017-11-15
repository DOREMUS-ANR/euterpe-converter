package org.doremus.euterpeConverter.musResource;

import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
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
    this.resource.addProperty(RDF.type, MUS.M26_Foreseen_Performance);
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

    this.model.add(p1.model);
    this.resource.addProperty(MUS.U7_foresees_place_at, p1.asResource());

    if (p2.asResource() != null) {
      p2.asResource().addProperty(CIDOC.P89_falls_within, p1.asResource());
      this.model.add(p2.model);
      this.resource.addProperty(MUS.U7_foresees_place_at, p2.asResource());
    }


    if (!ev.titre_ligne2.isEmpty())
      this.resource.addProperty(MUS.U67_has_subtitle, ev.titre_ligne2);

    this.resource.addProperty(FOAF.isPrimaryTopicOf, model.createResource(ev.lien_web.trim()));

    this.addNote(ev.description);

    for (int i = 0; i < ev.date.size(); i++)
      this.addDate(ev.date.get(i), i);


    F25_Performance_Plan performancePlan = F25_Performance_Plan.from(ev);
    resource.addProperty(MUS.U77_foresees_performing_plan, performancePlan.asResource());
    this.model.add(performancePlan.model);

  }


  private void addDate(Date d, int i) throws URISyntaxException {
    this.timeSpan = new E52_TimeSpan(new URI(this.uri + "/interval/" + i), d, null);
    this.resource.addProperty(MUS.U8_foresees_time_span, this.timeSpan.asResource());
    this.model.add(this.timeSpan.getModel());
  }
}
