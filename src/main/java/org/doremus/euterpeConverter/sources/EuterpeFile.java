package org.doremus.euterpeConverter.sources;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class EuterpeFile {
  @XmlElementWrapper(name = "evenements")
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
      return (EuterpeFile) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
      return null;
    }
  }

}
