package net.greg.examples.salient;

import java.io.*;
import java.nio.file.*;

import java.util.*;
import java.util.concurrent.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public final class Extractor {

  public static final String process(final String path) {

    String jsonResult = "";

    try (

      BufferedReader reader =
        new BufferedReader(
          new FileReader(path))) {

      String line;

      while (null != (line = reader.readLine())) {

        String[] constituents = line.split(",");

        for (int i=0; i < constituents.length; i++) {

          if (i % 5 == 0) {

            Long ndx = Long.parseLong(constituents[i]);

            if ( ! compendium.keySet().contains(ndx)) {
              compendium.put(ndx, 1);
            }
            else {

              int tally = compendium.get(ndx);
              compendium.put(ndx, ++tally);
            }
          }
        }
      }


      Set<Map.Entry<Long, Integer>> entries =
        compendium.entrySet();

      for (Map.Entry<Long, Integer> entry : entries) {

        Long key = entry.getKey();
        Integer value = entry.getValue();


        if (value > 1) {
          System.err.println(
            GRN + "Key " + key + " [" + value + "] dups" + NC);
        }
        else {
          compendium.remove(key);
        }
      }


      System.err.println(
        "\n\n" + GRN + "CHM:" + NC +
        "\n " + compendium + "\n");

      jsonResult =
        new ObjectMapper().
          writeValueAsString(compendium);
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return jsonResult;
  }


  public static final void persist(String json) {

    try ( PrintWriter any = new PrintWriter("viola.json")) {

      System.err.println(
        "\n" + GRN + "JSON:" + NC +
        "\n " + json + "\n");

      System.err.println(
        "\n" + GRN + "JQ:" + NC);

      any.println(json + "\n");

      new File(FQDN_FILE).delete();
    }
    catch (FileNotFoundException e) {  }
    catch (IOException e) {  }
  }


  public static void main(String[] any) {

    persist(process(FQDN_FILE));
  }


  private static final Map<Long,Integer> compendium =
    new ConcurrentHashMap();

  private static final String FQDN_FILE =
    "src/main/resources/events.txt";

  public static final String RED = "\033[1;91m";
  public static final String GRN = "\033[1;92m";
  public static final String YLW = "\033[1;93m";
  public static final String NC = "\u001B[0m";
}
