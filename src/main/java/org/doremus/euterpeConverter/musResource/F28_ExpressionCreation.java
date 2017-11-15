package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.doremus.euterpeConverter.ontology.CIDOC;
import org.doremus.euterpeConverter.ontology.FRBROO;
import org.doremus.euterpeConverter.ontology.MUS;
import org.doremus.euterpeConverter.sources.Compositeur;
import org.doremus.euterpeConverter.sources.Oeuvre;

import java.net.URISyntaxException;

public class F28_ExpressionCreation extends DoremusResource {

  public F28_ExpressionCreation(Oeuvre oeuvre, boolean createAPerformancePlan) throws URISyntaxException {
    super(oeuvre.id);
    this.resource.addProperty(RDF.type, FRBROO.F28_Expression_Creation);

    if (createAPerformancePlan) {
      // TBD
    } else {
      int ipCount = 0;
      String function = oeuvre.compositeur.size() > 1 ? "créateur" : "compositeur";

      for (Compositeur composer : oeuvre.compositeur) {
        String activityUri = this.uri + "/activity/" + ++ipCount;
        E21_Person artist = new E21_Person(composer);
        this.resource.addProperty(CIDOC.P9_consists_of,
          model.createResource(activityUri)
            .addProperty(RDF.type, CIDOC.E7_Activity)
            .addProperty(CIDOC.P14_carried_out_by, artist.asResource())
            .addProperty(MUS.U31_had_function_of_type, function, "fr")
        );
        this.model.add(artist.model);

      }
    }
  }
}
