package org.doremus.euterpeConverter.main;

import org.geonames.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GeoNames {
  static Map<String, Integer> cache; // cache label -> idGeoNames
  public static String destFolder;

  public static void setDestFolder(String folder) {
    destFolder = folder;
  }

  public static void setUser(String user) {
    WebService.setUserName(user); // add your username here
  }

  public static String toURI(int id) {
    return "http://sws.geonames.org/" + id + "/";
  }

  public static void downloadRdf(int id) {
    String uri = toURI(id) + "about.rdf";
    String outPath = Paths.get(destFolder, id + ".rdf").toString();
    if (new File(outPath).exists()) return; // it is already there!
    try {
      URL website = new URL(uri);
      ReadableByteChannel rbc = Channels.newChannel(website.openStream());
      FileOutputStream fos = new FileOutputStream(outPath);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Toponym query(String label, String type) {
    if (label == null) return null;

    String featureCode = null;
    Toponym tp = null;

    if (cache.containsKey(label)) {
      int k = cache.get(label);
      if (k != -1) {
        tp = new Toponym();
        tp.setGeoNameId(k);
      }
      return tp;
    }

    String fullLabel = label;
    if (label.startsWith("Eglise ")) {
      type = "eglise";
      label = label.replace("Eglise ", "").trim();
    }
    switch (type) {
      case "eglise":
        featureCode = "CH";
        break;
      case "etablissement":
        featureCode = "BLDG";
        break;
      case "lieu":
      default:
        // do nothing
    }

    ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
    searchCriteria.setName(label);
    searchCriteria.setMaxRows(1);
    if (featureCode != null) searchCriteria.setFeatureCode(featureCode);

    try {
      ToponymSearchResult searchResult = WebService.search(searchCriteria);
      if (searchResult.getToponyms().size() > 0)
        tp = searchResult.getToponyms().get(0);

      addToCache(fullLabel, tp != null ? tp.getGeoNameId() : -1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tp;
  }

  public static int get(String label) {
    return cache.getOrDefault(label, -1);

  }

  public static String getUri(String label, String type) {
    Toponym t = query(label, type);
    if (t == null) return null;
    else return toURI(t.getGeoNameId());
  }


  static void loadCache() {
    cache = new HashMap<>();
    try {
      FileInputStream fis = new FileInputStream("places.properties");
      Properties properties = new Properties();
      properties.load(fis);

      for (String key : properties.stringPropertyNames()) {
        cache.put(key, Integer.parseInt(properties.get(key).toString()));
      }
    } catch (IOException e) {
      System.out.println("No 'places.properties' file found. I will create it.");
    }

  }

  private static void addToCache(String key, int value) {
    cache.put(key, value);
    saveCache();
  }

  static void removeFromCache(String key) {
    cache.remove(key);
    saveCache();
  }

  private static void saveCache() {
    Properties properties = new Properties();

    for (Map.Entry<String, Integer> entry : cache.entrySet()) {
      properties.put(entry.getKey(), entry.getValue() + "");
    }

    try {
      properties.store(new FileOutputStream("places.properties"), null);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
