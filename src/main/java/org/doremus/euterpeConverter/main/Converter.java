package org.doremus.euterpeConverter.main;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.doremus.euterpeConverter.musResource.F25_Performance_Plan;
import org.doremus.euterpeConverter.musResource.M26_Foreseen_Performance;
import org.doremus.euterpeConverter.ontology.*;
import org.doremus.euterpeConverter.sources.EuterpeFile;
import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.string2vocabulary.VocabularyManager;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Converter {
  public final static String SCHEMA = "http://schema.org/";
  public static Resource Philharmonie, DOREMUS;

  public static Properties properties;
  private static int maxFilesInFolder, filesInCurrentFolder, currentFolder;
  private static String inputFolderPath, outputFolderPath;


  public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, NoSuchAlgorithmException {
    loadProperties();
    GeoNames.loadCache();
    GeoNames.setUser(properties.getProperty("geonames_user"));

    System.out.println("Running with the following properties: " + properties);

    ClassLoader classLoader = Converter.class.getClassLoader();
    VocabularyManager.setVocabularyFolder(classLoader.getResource("vocabulary").getPath());
    VocabularyManager.init(classLoader.getResource("property2family.csv"));


    inputFolderPath = properties.getProperty("src");
    outputFolderPath = properties.getProperty("out");

    maxFilesInFolder = Integer.parseInt(properties.getProperty("maxFilesInAFolder", "1000"));
    filesInCurrentFolder = 0;
    currentFolder = 0;

    convertFolder(new File(inputFolderPath));
  }

  private static void convertFolder(File folder) throws URISyntaxException, IOException, NoSuchAlgorithmException {

    File[] list = folder.isDirectory() ? folder.listFiles() : new File[]{folder};

    if (list == null || list.length < 1) {
      System.out.println("Input directory is empty");
      return;
    }

    Model m = ModelFactory.createDefaultModel();
    Philharmonie = m.createResource("http://data.doremus.org/organization/Philharmonie_de_Paris");
    DOREMUS = m.createResource("http://data.doremus.org/organization/DOREMUS");

    for (File file : list) {
      if (file.isDirectory()) {
        convertFolder(file);
        continue;
      }
      if (!file.isFile() || !file.getName().endsWith(".xml")) continue;
      convertFile(file);
    }

  }

  private static void write(Model m, String id) throws IOException {
    if (m == null) return;

    m.setNsPrefix("mus", MUS.getURI());
    m.setNsPrefix("ecrm", CIDOC.getURI());
    m.setNsPrefix("efrbroo", FRBROO.getURI());
    m.setNsPrefix("xsd", XSD.getURI());
    m.setNsPrefix("dcterms", DCTerms.getURI());
    m.setNsPrefix("foaf", FOAF.getURI());
    m.setNsPrefix("rdfs", RDFS.getURI());
    m.setNsPrefix("prov", PROV.getURI());
    m.setNsPrefix("owl", OWL.getURI());
    m.setNsPrefix("time", Time.getURI());
    m.setNsPrefix("schema", SCHEMA);

    VocabularyManager.string2uri(m);

    File fileName;
    if (filesInCurrentFolder > maxFilesInFolder) {
      ++currentFolder;
      filesInCurrentFolder = 0;
    }
    fileName = Paths.get(outputFolderPath, currentFolder + "", id + ".ttl").toFile();
    ++filesInCurrentFolder;

    //noinspection ResultOfMethodCallIgnored
    fileName.getParentFile().mkdirs();
    FileWriter out = new FileWriter(fileName);

    // m.write(System.out, "TURTLE");
    m.write(out, "TURTLE");
    out.close();
  }

  private static Model convertFile(File file) throws URISyntaxException {
    EuterpeFile ef = EuterpeFile.fromFile(file);
    for (Evenement ev : ef.getEvenments()) {

      if (!ev.isAConcert()) continue;
      System.out.println(ev.id);

      M26_Foreseen_Performance concert = M26_Foreseen_Performance.from(ev);

      try {
        write(concert.getModel(), ev.id);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  private static void loadProperties() {
    properties = new Properties();
    String filename = "config.properties";

    try {
      InputStream input = new FileInputStream(filename);
      properties.load(input);
      input.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }

}

