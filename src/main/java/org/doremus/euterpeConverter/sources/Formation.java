package org.doremus.euterpeConverter.sources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Formation {
  //  <formation id="1443">Budapest Festival Orchestra</formation>
  @XmlAttribute(name = "id")
  public String id;

  @XmlValue
  public String label;

  public String getLabel(){
    return label.trim();
  }
}
