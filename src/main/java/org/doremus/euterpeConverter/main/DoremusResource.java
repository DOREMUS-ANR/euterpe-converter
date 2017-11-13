package org.doremus.euterpeConverter.main;


import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.ontology.CIDOC;

import java.net.URI;
import java.net.URISyntaxException;


public abstract class DoremusResource {
  protected String className;
  protected String sourceDb;

  protected Model model;
  protected URI uri;
  protected Resource resource;
//  protected Record record;
  protected String identifier;
  protected Resource publisher;

  public DoremusResource(String identifier) throws URISyntaxException {
    this.identifier = identifier;
    this.model = ModelFactory.createDefaultModel();

    /* generate URI */
    this.className = this.getClass().getSimpleName();
    this.sourceDb = "bnf";
    if (this.className.startsWith("P")) {
      this.sourceDb = "pp";
      this.className = this.className.substring(1);
//      this.publisher = model.createResource(PP2RDF.organizationURI);
    } else
//      this.publisher = model.createResource(BNF2RDF.organizationURI);

    this.resource = null;
    /* create RDF resource */
    regenerateResource();
  }

  protected void regenerateResource() throws URISyntaxException {
    // delete old one
    if (this.resource != null) this.resource.removeProperties();

    // generate the new one
    this.uri = ConstructURI.build(this.sourceDb, this.className, this.identifier);
    this.resource = model.createResource(this.uri.toString());
  }

//  public DoremusResource(Record record) throws URISyntaxException {
//    this(record.getIdentifier());
//    this.record = record;
//  }
//
//  public DoremusResource(Record record, String identifier) throws URISyntaxException {
//    this(identifier);
//    this.record = record;
//  }

  public Resource asResource() {
    return this.resource;
  }

  public Model getModel() {
    return this.model;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  protected void addNote(String text) {
    if (text == null) return;
    text = text.trim();
    if (text.isEmpty()) return;

    this.resource
      .addProperty(RDFS.comment, text, "fr")
      .addProperty(CIDOC.P3_has_note, text, "fr");
  }

}
