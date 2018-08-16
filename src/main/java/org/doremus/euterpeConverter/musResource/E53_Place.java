package org.doremus.euterpeConverter.musResource;

import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.main.Converter;
import org.doremus.euterpeConverter.main.GeoNames;
import org.doremus.ontology.CIDOC;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class E53_Place extends DoremusResource {
  private static final String GEO_NAME = "http://www.geonames.org/ontology#name";
  private static List<String> wrongPlaces = null;
  private String label;
  private E53_Place outer;

  public E53_Place(String label, String type) {
    super();

    if (label == null) return;
    this.label = label.trim();

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

  private static void init() {
    URL file = Converter.class.getClassLoader().getResource("wrong_places.txt");
    try {
      wrongPlaces = Files.readAllLines(Paths.get(file.getFile()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void setWithin(E53_Place otherPlace) {
    if (wrongPlaces == null) init();

    if (!wrongPlaces.contains(this.label)) {
      this.addProperty(CIDOC.P89_falls_within, otherPlace);
      this.outer=otherPlace;
    }
  }

  public E53_Place getOuter() {
    return outer;
  }
}
