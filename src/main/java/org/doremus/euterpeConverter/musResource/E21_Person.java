package org.doremus.euterpeConverter.musResource;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.main.ISNIWrapper;
import org.doremus.euterpeConverter.main.Utils;
import org.doremus.euterpeConverter.sources.Compositeur;
import org.doremus.euterpeConverter.sources.Intervenant;
import org.doremus.isnimatcher.ISNIRecord;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.Schema;

import java.io.IOException;
import java.net.URISyntaxException;

public class E21_Person extends DoremusResource {
  private String id, label;

  public E21_Person(Compositeur record) {
    this.id = record.id;
    this.label = record.label;

    createPerson();
  }

  public E21_Person(Intervenant record) {
    this.id = record.id;
    this.label = record.getLabel();
    if (this.label.isEmpty())
      throw new RuntimeException("Empty Person name");

    createPerson();
  }

  private void createPerson() {
    try {
      this.uri = ConstructURI.build("ppe", "E21_Person", this.id);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (this.resource == null) initResource();

    interlink();
  }


  private Resource initResource() {
    this.resource = model.createResource(this.uri.toString());
    resource.addProperty(RDF.type, CIDOC.E21_Person);

    addProperty(FOAF.name, this.label);
    addProperty(RDFS.label, this.label);

    return resource;
  }

  public void interlink() {
    // 1. search in doremus by name/date
    Resource match = getPersonFromDoremus();
    if (match != null) {
      this.setUri(match.getURI());
      return;
    }

    // 2. search in isni by name/date
    ISNIRecord isniMatch = null;
    try {
      isniMatch = ISNIWrapper.search(this.label, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (isniMatch == null) return;

    // 3. search in doremus by isni
    match = getPersonFromDoremus(isniMatch.uri);
    if (match != null) {
      this.setUri(match.getURI());
      return;
    }

    // 4. add isni info
    this.isniEnrich(isniMatch);
  }

  private Resource getPersonFromDoremus() {
    return getFromDoremus(this.label, null);
  }

  private static final String NAME_SPARQL = "PREFIX ecrm: <" + CIDOC.getURI() + ">\n" +
    "PREFIX foaf: <" + FOAF.getURI() + ">\n" +
    "PREFIX schema: <" + Schema.getURI() + ">\n" +
    "SELECT DISTINCT ?s " +
    "FROM <http://data.doremus.org/bnf> " +
    "WHERE { ?s a ecrm:E21_Person; foaf:name ?name. }";
  private static final String NAME_DATE_SPARQL = "PREFIX ecrm: <" + CIDOC.getURI() + ">\n" +
    "PREFIX foaf: <" + FOAF.getURI() + ">\n" +
    "PREFIX schema: <" + Schema.getURI() + ">\n" +
    "SELECT DISTINCT ?s " +
    "FROM <http://data.doremus.org/bnf> " +
    "WHERE { ?s a ecrm:E21_Person; foaf:name ?name. " +
    "?s schema:birthDate ?date. FILTER regex(str(?date), ?birthDate) }";

  public static Resource getFromDoremus(String name, String birthDate) {
    ParameterizedSparqlString pss = new ParameterizedSparqlString();
    pss.setCommandText(birthDate != null ? NAME_DATE_SPARQL : NAME_SPARQL);
    pss.setLiteral("name", name);
    if (birthDate != null) pss.setLiteral("birthDate", birthDate);

    return (Resource) Utils.queryDoremus(pss, "s");
  }

  private static final String ISNI_SPARQL = "PREFIX owl: <" + OWL.getURI() + ">\n" +
    "SELECT DISTINCT * WHERE { ?s owl:sameAs ?isni }";

  private static Resource getPersonFromDoremus(String isni) {
    ParameterizedSparqlString pss = new ParameterizedSparqlString();
    pss.setCommandText(ISNI_SPARQL);
    pss.setIri("isni", isni);
    return (Resource) Utils.queryDoremus(pss, "s");
  }

  public void addPropertyResource(Property property, String uri) {
    if (property == null || uri == null) return;
    resource.addProperty(property, model.createResource(uri));
  }

  public void isniEnrich(ISNIRecord isni) {
    this.addPropertyResource(OWL.sameAs, isni.uri);
    this.addPropertyResource(OWL.sameAs, isni.getViafURI());
    this.addPropertyResource(OWL.sameAs, isni.getMusicBrainzUri());
    this.addPropertyResource(OWL.sameAs, isni.getMuziekwebURI());
    this.addPropertyResource(OWL.sameAs, isni.getWikidataURI());

    String wp = isni.getWikipediaUri();
    String dp = isni.getDBpediaUri();

    if (wp == null) {
      wp = isni.getWikipediaUri("fr");
      dp = isni.getDBpediaUri("fr");
    }
    this.addPropertyResource(OWL.sameAs, dp);
    this.addPropertyResource(FOAF.isPrimaryTopicOf, wp);
  }

}
