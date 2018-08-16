package org.doremus.euterpeConverter.musResource;

import org.doremus.euterpeConverter.sources.Evenement;
import org.doremus.ontology.FRBROO;

public class F20_PerformanceWork extends DoremusResource {

  public F20_PerformanceWork(Evenement ev) {
    super(ev.id);
    this.setClass(FRBROO.F20_Performance_Work);
  }

}
