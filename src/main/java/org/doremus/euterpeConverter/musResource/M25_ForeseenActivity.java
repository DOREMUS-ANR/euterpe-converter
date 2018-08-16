package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.sources.SeasonChain;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.MUS;

import java.net.URISyntaxException;

public class M25_ForeseenActivity extends DoremusResource {
  public M25_ForeseenActivity(SeasonChain sc) {
    this(sc.getTitle());

    this.addProperty(CIDOC.P2_has_type, "saison artistique", "fr")
      .addProperty(CIDOC.P2_has_type, "artistic season", "en");
    this.addTitle(sc.getTitle());


    M26_Foreseen_Performance concert = new M26_Foreseen_Performance(sc.getId());
    this.addProperty(CIDOC.P9_consists_of, concert.asResource());

    //    M25 Foreseen Activity  P9 consists of M25 Foreseen Activity
    if (sc.hasCycle()) {
      M25_ForeseenActivity cycle = new M25_ForeseenActivity(sc.getTitle() + sc.getCycle());
      cycle.addTitle(sc.getCycle());

      cycle.addProperty(CIDOC.P2_has_type, typeFromTitle(sc.getCycle()));

      cycle.addProperty(CIDOC.P9_consists_of, concert.asResource());
      this.addProperty(CIDOC.P9_consists_of, cycle);
    }
  }

  private String typeFromTitle(String title) {
    title = title.toLowerCase();
    if (title.startsWith("week-end"))
      return "week-end";
    if(title.contains("biennale")|| title.contains("jazz à la villette") || title.contains("days off")
      || title.contains("festival"))
      return "festival";
    return "cycle";
  }


  public M25_ForeseenActivity(String title) {
    super();
    try {
      regenerateResource(ConstructURI.build("M25_Foreseen_Activity", title));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    this.setClass(MUS.M25_Foreseen_Activity);
//    this.addProperty(MUS.U92_foresees_to_put_into_effect, model.createResource()
//      .addProperty(RDF.type, MUS.M59_Program));

  }

  public void addTitle(String title) {
    this.addProperty(RDFS.label, title).addProperty(CIDOC.P102_has_title, title);

  }
}
