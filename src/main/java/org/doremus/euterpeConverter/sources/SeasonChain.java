package org.doremus.euterpeConverter.sources;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class SeasonChain {
  @CsvBindByName(column = "Id")
  private String id;
  @CsvBindByName(column = "Cycle")
  private String cycle;
  @CsvBindByName(column = "Saison")
  private String season;


  public static List<SeasonChain> init(File csv) {
    try {
      return new CsvToBeanBuilder<>(new FileReader(csv)).withType(SeasonChain.class).build().parse();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return season.trim();
  }

  public String getCycle() {
    if (cycle == null) return "";
    return cycle.trim();
  }

  public boolean hasCycle() {
    return !getCycle().isEmpty();
  }
}
