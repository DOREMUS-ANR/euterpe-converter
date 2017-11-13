package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.ontology.CIDOC;
import org.doremus.euterpeConverter.ontology.MUS;
import org.doremus.euterpeConverter.sources.Evenement;

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

    this.asResource()
      .addProperty(DCTerms.identifier, ev.id)
      .addProperty(CIDOC.P102_has_title, ev.titre_ligne1)
      .addProperty(RDFS.label, ev.titre_ligne1);

    if(!ev.titre_ligne2.isEmpty())
      this.asResource().addProperty(MUS.U67_has_subtitle, ev.titre_ligne2);

    for (int i = 0; i < ev.date.size(); i++)
      this.addDate(ev.date.get(i), i);
  }


  private void addDate(Date d, int i) throws URISyntaxException {
    this.timeSpan = new E52_TimeSpan(new URI(this.uri + "/interval/" + i), d, null);
    this.resource.addProperty(CIDOC.P4_has_time_span, this.timeSpan.asResource());
    this.model.add(this.timeSpan.getModel());
  }
}
