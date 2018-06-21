package org.doremus.euterpeConverter.musResource;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.ontology.CIDOC;
import org.doremus.euterpeConverter.sources.Compositeur;
import org.doremus.euterpeConverter.sources.Intervenant;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class E21_Person extends DoremusResource {
  private static final String ENDPOINT = "http://data.doremus.org/sparql";
  private String id, label;
  ParameterizedSparqlString artistQuery = null;

  private static HashMap<String, String> cache = null;

  public E21_Person(Compositeur record) throws URISyntaxException {
    this.id = record.id;
    this.label = record.label;

    createPerson();
  }

  public E21_Person(Intervenant record) throws URISyntaxException {
    this.id = record.id;
    this.label = record.getLabel();
    if (this.label.isEmpty())
      throw new RuntimeException("Empty Person name");

    createPerson();
  }

  private void createPerson() throws URISyntaxException {
    if (cache == null) loadCache();

    String url = cache.getOrDefault(this.id, null);
    if (url == null) {
      // never searched, search it
      this.resource = searchInDb(this.label);
      if (this.resource != null) {
        url = this.resource.getURI();
        addToCache(this.id, url);
      } else {
        addToCache(this.id, "none");
      }
    } else if (url.equals("none")) {
      // searched without success
      // traditional way
      url = null;
    } else {
      url = cache.get(this.id);
    }

    this.uri = (url == null) ?
      ConstructURI.build("ppe", "E21_Person", this.id) :
      new URI(url);

    if (this.resource == null)
      initResource();

  }


  public Resource searchInDb(String label) {
    if (artistQuery == null) {
      artistQuery = new ParameterizedSparqlString();
      artistQuery.setCommandText(
        "SELECT DISTINCT *\n" +
          "WHERE { ?artist foaf:name ?o \n" +
          "FILTER (lcase(str(?o)) = ?name) }\n");
      artistQuery.setNsPrefix("foaf", FOAF.NS);
    }

    artistQuery.setLiteral("name", label.toLowerCase());

    QueryExecution qe = QueryExecutionFactory.sparqlService(ENDPOINT, artistQuery.asQuery());

    ResultSet res = qe.execSelect();
    if (!res.hasNext()) return null;
    return res.nextSolution().getResource("artist");
  }


  private Resource initResource() {
    this.resource = model.createResource(this.uri.toString());
    resource.addProperty(RDF.type, CIDOC.E21_Person);

    addProperty(FOAF.name, this.label);
    addProperty(RDFS.label, this.label);

    return resource;
  }


  public void addProperty(Property property, String object, String lang) {
    if (property == null || object == null || object.isEmpty()) return;

    if (lang != null)
      resource.addProperty(property, model.createLiteral(object, lang));
    else
      resource.addProperty(property, object);
  }

  private void addProperty(Property property, String object) {
    addProperty(property, object, null);
  }


  private static void loadCache() {
    cache = new HashMap<>();
    try {
      FileInputStream fis = new FileInputStream("person.properties");
      Properties properties = new Properties();
      properties.load(fis);

      for (String key : properties.stringPropertyNames()) {
        cache.put(key, properties.get(key).toString());
      }
    } catch (IOException e) {
      System.out.println("No 'person.properties' file found. I will create it.");
    }

  }

  private static void addToCache(String key, String value) {
    cache.put(key, value);
    saveCache();
  }

  private static void saveCache() {
    Properties properties = new Properties();

    for (Map.Entry<String, String> entry : cache.entrySet()) {
      properties.put(entry.getKey(), entry.getValue() + "");
    }

    try {
      properties.store(new FileOutputStream("person.properties"), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
