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
    if ((v.charAt(0) + "").matches("(?i)a-z")) {
      //    <date>vendredi 14/10/2016, 20:30</date>
      v = v.split(" ", 2)[1];
    }
    return f.parse(v + "UTC");
  }

  @Override
  public String marshal(Date v) {
    return f.format(v);
  }
}
