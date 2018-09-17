package org.doremus.euterpeConverter.musResource;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
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
  private static final Property GEO_NAME = ResourceFactory.createProperty("http://www.geonames.org/ontology#name");
  private static final Resource PHILHARMONIE = ResourceFactory.createProperty("http://data.doremus.org/place/bd21be9c-3f2b-3aa3-a460-114d579eabe6");
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

    this.setClass(CIDOC.E53_Place);

    String idLabel = label;
    if (label.contains("-") && (label.contains("Philharmonie") || label.contains("Cité de la musique")))
      label = label.split("-")[0];

    this.addProperty(RDFS.label, label)
      .addProperty(GEO_NAME, label)
      .addProperty(CIDOC.P1_is_identified_by, idLabel);
  }

  private static void init() {
    URL file = Converter.class.getClassLoader().getResource("wrong_places.txt");
    try {
      assert file != null;
      wrongPlaces = Files.readAllLines(Paths.get(file.getFile()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void setWithin(E53_Place otherPlace) {
    if (wrongPlaces == null) init();
    if (!wrongPlaces.contains(this.label)) {
      this.addProperty(CIDOC.P89_falls_within, otherPlace);
      this.outer = otherPlace;
    }
  }

  public E53_Place getOuter() {
    return outer;
  }
}
