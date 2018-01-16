package org.doremus.euterpeConverter.sources;

import org.doremus.euterpeConverter.main.DateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Evenement {
  @XmlElement
  public String id;
  @XmlElement
  public String etablissement;
  @XmlElement
  public String lieu;
  @XmlElement(name = "titre_ligne1")
  public String titre_ligne1;
  @XmlElement(name = "titre_ligne2")
  public String titre_ligne2;
  @XmlElement
  public String lien_web;
  @XmlElement
  public String description;

  @XmlElementWrapper(name = "representations")
  @XmlElement(name = "date")
  @XmlJavaTypeAdapter(DateAdapter.class)
  public List<Date> date;

  @XmlElementWrapper(name = "programme")
  @XmlElement
  public List<Oeuvre> oeuvre;

  @XmlElementWrapper(name = "programme")
  @XmlElement
  public List<String> ligne;

  @XmlElementWrapper(name = "activites")
  @XmlElement
  public List<String> activite;

  @XmlElementWrapper(name = "distribution")
  @XmlElement
  public List<Formation> formation;

  @XmlElementWrapper(name = "distribution")
  @XmlElement
  public List<Intervenant> intervenant;


  public boolean isAConcert() {
    return activite.indexOf("concert") > -1 || activite.indexOf("concert éducatif") > -1;
  }
}
