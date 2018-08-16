package org.doremus.euterpeConverter.main;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import java.util.*;


public class Utils {
  private static Map<String, Locale> localeMap;
  private static Map<String, String> intermarcScriptMap;

  public static final String opusHeaderRegex = "(?i)^(?:op(?:\\.|us| )|Oeuvre|Werk nr\\.?) ?(?:post(?:hume|h?\\.|h))?";
  public static final String opusSubnumberRegex = "(?i)(?:,? n(?:[o°.]| °)[s.]?)";

  public static List<Integer> toRange(String rangeString) {
    if (!rangeString.contains(" à ")) return null;

    String[] singleNum = rangeString.split(" à ", 2);
    List<Integer> range = new ArrayList<>();
    try {
      int start = Integer.parseInt(singleNum[0]);
      int end = Integer.parseInt(singleNum[1]);

      for (int i = start; i <= end; i++) {
        range.add(i);
      }

    } catch (Exception e) {
      System.out.println("Not able to parse range in " + rangeString);
      System.out.println("--> " + e.getMessage());
      return null;
    }

    return range;
  }

  public static Literal toSafeNumLiteral(String str) {
    Model model = ModelFactory.createDefaultModel();
    if (str.matches("\\d+"))
      return model.createTypedLiteral(Integer.parseInt(str));
    else return model.createTypedLiteral(str);
  }

  public static String toISO2Lang(String lang) {
    if (lang == null || lang.isEmpty()) return null;
    if (lang.length() == 2) return lang;

    // workarounds
    if (lang.equals("fre")) return "fr";
    if (lang.equals("dut")) return "nl";
    if (lang.equals("ger")) return "de";
    if (lang.equals("amr")) return "hy";
    if (lang.equals("arm")) return "hy";
    if (lang.equals("gre")) return "el";
    if (lang.equals("chi")) return "zh";
    if (lang.equals("rum")) return "ro";
    if (lang.equals("baq")) return "eus";
    if (lang.equals("tib")) return "bo";
    if (lang.equals("cze")) return "cs";
    if (lang.equals("mac")) return "mk";
    if (lang.equals("geo")) return "ka";
    if (lang.equals("slo")) return "sk";
    if (lang.equals("ice")) return "is";

    if (localeMap == null) { //init
      String[] languages = Locale.getISOLanguages();
      localeMap = new HashMap<>(languages.length);
      for (String language : languages) {
        Locale locale = new Locale(language);
        localeMap.put(locale.getISO3Language(), locale);
      }
    }
    Locale l = localeMap.get(lang);
    if (l == null) return lang;
    return l.toLanguageTag();
  }

  public static QuerySolution queryDoremus(ParameterizedSparqlString sparql) {
    QueryExecution qexec = QueryExecutionFactory.sparqlService("http://data.doremus.org/sparql", sparql.toString());
    ResultSet r = qexec.execSelect();
    if (!r.hasNext()) return null;
    return r.next();
  }

  public static RDFNode queryDoremus(ParameterizedSparqlString sparql, String var) {
    QuerySolution result = queryDoremus(sparql);
    if (result == null) return null;
    else return result.get(var);
  }

}

