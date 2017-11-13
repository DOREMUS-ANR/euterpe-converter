package org.doremus.euterpeConverter.main;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.doremus.euterpeConverter.ontology.*;
import org.doremus.euterpeConverter.sources.EuterpeFile;
import org.doremus.string2vocabulary.VocabularyManager;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Converter {
  private static final String UTF8_BOM = "\uFEFF";
  public final static String SCHEMA = "http://schema.org/";

  public static Properties properties;
  private static int maxFilesInFolder, filesInCurrentFolder, currentFolder;
  private static Model general;
  private static boolean oneFile;


  public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, NoSuchAlgorithmException {
    loadProperties();
    System.out.println("Running with the following properties: " + properties);

    ClassLoader classLoader = Converter.class.getClassLoader();
    VocabularyManager.setVocabularyFolder(classLoader.getResource("vocabulary").getPath());
    VocabularyManager.init(classLoader.getResource("property2family.csv"));


    String inputFolderPath = properties.getProperty("src");
    maxFilesInFolder = Integer.parseInt(properties.getProperty("maxFilesInAFolder", "1000"));
    filesInCurrentFolder = 0;
    currentFolder = 0;

    oneFile = Boolean.parseBoolean(properties.getProperty("oneFile", "false"));
    general = ModelFactory.createDefaultModel();
    convertFolder(new File(inputFolderPath));
  }

  private static void convertFolder(File folder) throws URISyntaxException, IOException, NoSuchAlgorithmException {
    String outputFolderPath = properties.getProperty("out");

    File[] list = folder.isDirectory() ? folder.listFiles() : new File[]{folder};

    if (list == null || list.length < 1) {
      System.out.println("Input directory is empty");
      return;
    }

//    int i = 100;
    for (File file : list) {
//      if(i < 0) break;
//      --i;

      if (!file.isFile() || !file.getName().endsWith(".xml")) continue;
      Model m;

      m = convertFile(file);

      if (m == null) continue;

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

      // Write the output file
      // write a unique file
      if (oneFile) {
        general.add(m);
        continue;
      }
      // write file by file
      File fileName;
      String newFileName = file.getName().replaceFirst(".xml", ".ttl");
      String parentFolder = file.getParentFile().getAbsolutePath();
      if (outputFolderPath != null && !outputFolderPath.isEmpty()) {
        // default folder specified, write there
        if (filesInCurrentFolder > maxFilesInFolder) {
          ++currentFolder;
          filesInCurrentFolder = 0;
        }
        fileName = Paths.get(outputFolderPath, currentFolder + "", newFileName).toFile();
        ++filesInCurrentFolder;

      } else {
        // write in the same folder, in the "RDF" subfolder
        fileName = Paths.get(parentFolder, "RDF", newFileName).toFile();
      }
      //noinspection ResultOfMethodCallIgnored
      fileName.getParentFile().mkdirs();
      FileWriter out = new FileWriter(fileName);

      // m.write(System.out, "TURTLE");
      m.write(out, "TURTLE");
      out.close();

    }

    for (File subList : list) {
      if (subList.isDirectory())
        convertFolder(subList);
    }

    if (oneFile) {
      File fileName = Paths.get(outputFolderPath, folder.getName() + ".ttl").toFile();
      FileWriter out = new FileWriter(fileName);
      general.write(out, "TURTLE");
      out.close();
    }
  }

  private static Model convertFile(File file) {
    EuterpeFile ef = EuterpeFile.fromFile(file);
    System.out.println(ef.getEvenments().get(1).getId());
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

