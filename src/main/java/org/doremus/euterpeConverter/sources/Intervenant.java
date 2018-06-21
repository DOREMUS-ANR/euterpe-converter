package org.doremus.euterpeConverter.sources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Intervenant {
  //<intervenant id="5934" libelle="Iván Fischer" role="direction"/>
  //<intervenant id="4097" libelle="Emanuel Ax" role="piano"/>
  @XmlAttribute(name = "id")
  public String id;

  @XmlAttribute(name = "libelle")
  private String label;

  @XmlAttribute(name = "role")
  public String role;

  public String getLabel(){
    return label.trim();
  }
}
