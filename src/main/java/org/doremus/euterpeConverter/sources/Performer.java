package org.doremus.euterpeConverter.sources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public abstract class Performer {
  //<intervenant id="5934" libelle="Iván Fischer" role="direction"/>

  @XmlAttribute(name = "id")
  public String id;

  @XmlValue
  private String label;

  @XmlAttribute(name = "libelle")
  private String label2;

  @XmlAttribute(name = "role")
  public String role;


  public String getLabel() {
    if (label != null && !label.isEmpty())
      return label.trim();
    else if (label2 != null && !label2.isEmpty())
      return label2.trim();
    else return null;
  }
}
