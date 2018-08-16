package org.doremus.euterpeConverter.musResource;

import org.doremus.euterpeConverter.sources.Oeuvre;
import org.doremus.ontology.FRBROO;

public class F14_IndividualWork extends DoremusResource {

  public F14_IndividualWork(Oeuvre o) {
    super(o.id);
    this.setClass(FRBROO.F14_Individual_Work);
  }

}
