package org.doremus.euterpeConverter.sources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Compositeur {
  //  <compositeur id="3409">Ludwig van Beethoven</compositeur>
  @XmlAttribute(name = "id")
  public String id;

  @XmlValue
  public String label;
}
