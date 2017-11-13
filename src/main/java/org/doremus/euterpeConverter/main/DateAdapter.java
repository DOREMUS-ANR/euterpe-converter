package org.doremus.euterpeConverter.main;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Parse custom dates in the XML
// https://stackoverflow.com/a/17049927/1218213

public class DateAdapter extends XmlAdapter<String, Date> {
  private static final DateFormat f = new SimpleDateFormat("dd/MM/yyyy, hh:mm");

  @Override
  public Date unmarshal(String v) throws Exception {
    //    <date>vendredi 14/10/2016, 20:30</date>
    String[] parts = v.split(" ", 2);
    return f.parse(parts[1] + "UTC");
  }

  @Override
  public String marshal(Date v) throws Exception {
    return f.format(v);
  }
}
