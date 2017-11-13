package org.doremus.euterpeConverter.sources;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@XmlRootElement(name = "evenements")
@XmlAccessorType(XmlAccessType.FIELD)
public class EuterpeFile {
  @XmlElement(name = "evenement")
  private List<Evenement> evenement;


  public EuterpeFile() {
  }


  public List<Evenement> getEvenments() {
    return evenement;
  }


  public static EuterpeFile fromFile(File file) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(EuterpeFile.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      Node fooSubtree = doc.getFirstChild().getChildNodes().item(1);

      return (EuterpeFile) jaxbUnmarshaller.unmarshal(fooSubtree);

    } catch (JAXBException | ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
