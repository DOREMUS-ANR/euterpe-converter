package org.doremus.euterpeConverter.musResource;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.main.Converter;
import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.euterpeConverter.sources.Formation;
import org.doremus.euterpeConverter.sources.Intervenant;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.MUS;
import org.doremus.ontology.PROV;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;

public class M26_Foreseen_Performance extends DoremusResource {
  private E52_TimeSpan timeSpan;
  private Resource provEntity, provActivity;

  public M26_Foreseen_Performance(String identifier) {
    super(identifier);
    this.resource.addProperty(RDF.type, MUS.M26_Foreseen_Performance);
  }

  public static M26_Foreseen_Performance from(Evenement ev) {
    M26_Foreseen_Performance fp = new M26_Foreseen_Performance(ev.id);
    fp.parse(ev);
    fp.addProvenance(ev.id);
    fp.resource.addProperty(DCTerms.identifier, ev.id);
    return fp;
  }

  private void addProvenance(String id) {
    // PROV-O tracing
    provEntity = model.createResource("http://data.doremus.org/source/euterpe/" + id)
      .addProperty(RDF.type, PROV.Entity).addProperty(PROV.wasAttributedTo, Converter.Philharmonie);

    String provUri = null;
    try {
      provUri = ConstructURI.build("ppe", "prov", id).toString();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    provActivity = model.createResource(provUri)
      .addProperty(RDF.type, PROV.Activity).addProperty(RDF.type, PROV.Derivation)
      .addProperty(PROV.used, provEntity)
      .addProperty(RDFS.comment, "Reprise et conversion de la notice avec id " + id +
        " de la base Euterpe de la Philharmonie de Paris", "fr")
      .addProperty(RDFS.comment, "Resumption and conversion of the record with id " + id
        + " of the dataset Euterpe of Philharmonie de Paris", "en")
      .addProperty(PROV.atTime, Instant.now().toString(), XSDDatatype.XSDdateTime);


    this.asResource().addProperty(RDF.type, PROV.Entity)
      .addProperty(PROV.wasAttributedTo, Converter.DOREMUS)
      .addProperty(PROV.wasDerivedFrom, this.provEntity)
      .addProperty(PROV.wasGeneratedBy, this.provActivity);
  }

  private void parse(Evenement ev) {

    this.resource
      .addProperty(DCTerms.identifier, ev.id)
      .addProperty(CIDOC.P102_has_title, ev.titre_ligne1)
      .addProperty(RDFS.label, ev.titre_ligne1);

    for (String act : ev.activite)
      this.resource.addProperty(CIDOC.P2_has_type, act, "fr");

    // TODO make a resource ?
    for (String t : ev.activite)
      this.resource.addProperty(CIDOC.P2_has_type, t, "fr");

    E53_Place p1 = new E53_Place(ev.etablissement, "etablissement");
    E53_Place p2 = new E53_Place(ev.lieu, "lieu");


    if (p2.asResource() != null) {
      this.addProperty(MUS.U7_foresees_place_at, p2);
      p2.setWithin(p1);
      this.addProperty(MUS.U7_foresees_place_at, p2.getOuter());
    } else this.addProperty(MUS.U7_foresees_place_at, p1);

    if (!ev.titre_ligne2.isEmpty())
      this.resource.addProperty(MUS.U67_has_subtitle, ev.titre_ligne2);

    this.resource.addProperty(FOAF.isPrimaryTopicOf, model.createResource(ev.lien_web.trim()));

    // Notes
    this.addNote(ev.description);
    for (String programLine : ev.getProgramLines()) addNote(programLine);

    // dates
    for (int i = 0; i < ev.date.size(); i++)
      this.addDate(ev.date.get(i), i);

    // Musicians
    int activityCount = 0;
    for (Formation f : ev.getFormations()) {
      System.out.println(f.getLabel());

      M27_Foreseen_Individual_Performance m27
        = new M27_Foreseen_Individual_Performance(this.uri, ++activityCount, f);
      resource.addProperty(CIDOC.P69_has_association_with, m27.asResource());
      this.model.add(m27.getModel());
    }

    M27_Foreseen_Individual_Performance oldM27 = null;
    for (Intervenant i : ev.getIntervenants()) {
      System.out.println(i.getLabel());
      for (String role : i.role.split(",")) {
        role = role.trim();

        boolean fromMuseum = role.contains("Musée de la musique");
        if (fromMuseum && oldM27 != null) {
          this.resource.addProperty(MUS.U3_foresees_use_of_specific_object,
            model.createResource().addProperty(RDF.type, CIDOC.E22_Man_Made_Object)
              .addProperty(RDFS.label, role.replaceAll("^\\((.+)\\)$", "$1"))
          );
          continue;
        }

        M27_Foreseen_Individual_Performance m27 =
          new M27_Foreseen_Individual_Performance(this.uri, ++activityCount, i, role);

        if (!fromMuseum && !m27.isARole) {
          M27_Foreseen_Individual_Performance target = (oldM27 == null) ? m27 : oldM27;
          target.asResource().addProperty(MUS.U37_foresees_performing_character, role, "fr");
          if (oldM27 == null) continue;
        }
        oldM27 = m27;
        resource.addProperty(CIDOC.P69_has_association_with, m27.asResource());
        this.model.add(m27.getModel());
      }
    }

    // Performance Plan
    F25_Performance_Plan performancePlan = F25_Performance_Plan.from(ev);
    resource.addProperty(MUS.U77_foresees_performing_plan, performancePlan.asResource());
    this.model.add(performancePlan.model);

  }


  private void addDate(Date d, int i) {
    URI tsUri = null;
    try {
      tsUri = new URI(this.uri + "/interval/" + i);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    this.timeSpan = new E52_TimeSpan(tsUri, d, null);
    this.resource.addProperty(MUS.U8_foresees_time_span, this.timeSpan.asResource());
    this.model.add(this.timeSpan.getModel());
  }
}
