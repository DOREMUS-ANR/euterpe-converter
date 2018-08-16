package org.doremus.euterpeConverter.musResource;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.euterpeConverter.main.Utils;
import org.doremus.euterpeConverter.sources.Formation;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;

public class F11_Corporate_Body extends DoremusResource {

  public F11_Corporate_Body(Formation formation) {
    super(formation.id);
    String name = formation.getLabel();

    Resource r = getFromDoremus(name);
    if (r != null) {
      this.resource = r;
      setUri(r.getURI());
    }

    resource.addProperty(RDF.type, FRBROO.F11_Corporate_Body)
      .addProperty(RDFS.label, name)
      .addProperty(CIDOC.P131_is_identified_by, name);
  }


  private static final String NAME_SPARQL = "PREFIX rdfs: <" + RDFS.getURI() + ">\n" +
    "PREFIX efrbroo: <" + FRBROO.getURI() + ">\n" +
    "SELECT DISTINCT ?s " +
    "WHERE { ?s a efrbroo:F11_Corporate_Body; rdfs:label ?o. " +
    "FILTER (lcase(str(?o)) = ?name) }";

  public static Resource getFromDoremus(String name) {
    ParameterizedSparqlString pss = new ParameterizedSparqlString();
    pss.setCommandText(NAME_SPARQL);
    pss.setLiteral("name", name.toLowerCase());

    return (Resource) Utils.queryDoremus(pss, "s");
  }

}
