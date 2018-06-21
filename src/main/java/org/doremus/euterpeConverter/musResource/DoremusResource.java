package org.doremus.euterpeConverter.musResource;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.ConstructURI;
import org.doremus.euterpeConverter.main.Converter;
import org.doremus.ontology.CIDOC;

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

  public DoremusResource() {
    // do nothing, enables customisation for child class
    this.model = ModelFactory.createDefaultModel();
    this.className = this.getClass().getSimpleName();
    this.publisher = Converter.Philharmonie;
  }

  public DoremusResource(String identifier) throws URISyntaxException {
    this();
    this.identifier = identifier;

    /* generate URI */
    this.className = this.getClass().getSimpleName();
    this.sourceDb = "ppe";

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

  protected void regenerateResource(URI uri) {
    this.uri = uri;

    // delete old one
    if (this.resource != null) this.resource.removeProperties();

    // generate the new one
    this.resource = model.createResource(this.uri.toString());
  }

  public DoremusResource(URI uri) {
    this();
    this.uri = uri;
    this.resource = model.createResource(this.uri.toString());
  }


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
