package org.doremus.euterpeConverter.sources;

import org.doremus.euterpeConverter.main.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
  @XmlElements({
    @XmlElement(name = "intervenant", type = Intervenant.class),
    @XmlElement(name = "formation", type = Formation.class)
  })
  public List<Artist> performers;

  public boolean isAConcert() {
    return activite.indexOf("concert") > -1 || activite.indexOf("concert éducatif") > -1;
  }

  public List<String> getProgramLines() {
    return safeList(ligne);
  }

  public List<Intervenant> getIntervenants() {
    return (List<Intervenant>) safeList(performers).stream()
      .filter(x -> x instanceof Intervenant)
      .collect(Collectors.toList());
  }

  public List<Formation> getFormations() {
    return (List<Formation>) safeList(performers).stream()
      .filter(x -> x instanceof Formation)
      .filter(x -> !((Formation) x).getLabel().isEmpty())
      .collect(Collectors.toList());
  }


  private List safeList(List input) {
    if (input != null) return input;
    return new ArrayList<>();

  }

  public String getId() {
    return "e" + id;
  }
}
