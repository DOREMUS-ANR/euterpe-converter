package org.doremus.euterpeConverter.musResource;


import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.vocabulary.RDF;
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

  public DoremusResource(String identifier) {
    this();
    this.identifier = identifier;

    /* generate URI */
    this.className = this.getClass().getSimpleName();
    this.sourceDb = "ppe";

    this.resource = null;
    /* create RDF resource */
    regenerateResource();
  }

  protected void regenerateResource() {
    // delete old one
    if (this.resource != null) this.resource.removeProperties();

    // generate the new one
    try {
      this.uri = ConstructURI.build(this.sourceDb, this.className, this.identifier);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
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

  protected void setUri(String uri) {
    if (this.uri != null && uri.equals(this.uri.toString())) return;

    try {
      this.uri = new URI(uri);
      if (this.resource != null)
        this.resource = ResourceUtils.renameResource(this.resource, uri);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  protected void setClass(OntClass _class) {
    this.resource.addProperty(RDF.type, _class);
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

  public DoremusResource addProperty(Property property, DoremusResource resource) {
    if (resource != null) {
      this.addProperty(property, resource.asResource());
      this.model.add(resource.getModel());
    }
    return this;
  }

  public DoremusResource addProperty(Property property, Resource resource) {
    if (resource != null) this.resource.addProperty(property, resource);
    return this;
  }

  public DoremusResource addProperty(Property property, String literal) {
    if (literal != null && !literal.isEmpty())
      this.resource.addProperty(property, literal.trim());
    return this;
  }
  public DoremusResource addProperty(Property property, String literal, String lang) {
    if (literal != null && !literal.isEmpty())
      this.resource.addProperty(property, literal.trim(), lang);
    return this;
  }

  protected DoremusResource addProperty(Property property, Literal literal) {
    if (literal != null)
      this.resource.addProperty(property, literal);
    return this;
  }

  protected DoremusResource addProperty(Property property, String literal, XSDDatatype datatype) {
    if (literal != null && !literal.isEmpty())
      this.resource.addProperty(property, literal.trim(), datatype);
    return this;
  }


}
