package org.doremus.euterpeConverter.sources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Oeuvre {
  @XmlElement
  private String id;
  @XmlElement
  public String titre;

  @XmlElementWrapper(name = "compositeurs")
  @XmlElement
  public List<Compositeur> compositeur;


  public String getId() {
    return "w" + id;
  }

}
