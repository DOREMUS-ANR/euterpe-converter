package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.main.GeoNames;
import org.doremus.ontology.CIDOC;

import java.net.URISyntaxException;

public class E53_Place extends DoremusResource {
  private static final String GEO_NAME = "http://www.geonames.org/ontology#name";

  public E53_Place(String label, String type) {
    super();

    if (label == null) return;
    label = label.trim();

    String match = GeoNames.getUri(label, type);
    if (match != null)
      this.resource = model.createResource(match);
    else {
      try {
        uri = ConstructURI.build("E53_Place", label);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      regenerateResource(uri);
    }

    this.resource.addProperty(RDF.type, CIDOC.E53_Place)
      .addProperty(RDFS.label, label)
      .addProperty(model.createProperty(GEO_NAME), label)
      .addProperty(CIDOC.P1_is_identified_by, label);
  }
}
