package net.greg.examples.salient;

import java.io.*;
import java.util.*;

import java.nio.file.*;

import java.sql.*;


/*
 https://bit.ly/3dwVAQv
 https://www.baeldung.com/java-jdbc-url-format
 https://www.baeldung.com/jdbc-check-table-exists
 https://www.javatpoint.com/java-drivermanager-getconnection-method
 https://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc
*/
public final class PilotBuilder {

  private PilotBuilder accept() {

    try (

      BufferedReader reader =
        new BufferedReader(
          new FileReader(FQDN_TALISMAN_FILE))) {

        String line;

        int lineCount = 0;

        while (null != (line = reader.readLine())) {

          if (lineCount == 0) {
            BEGIN = Long.parseLong(line); //1660890370346L;
          }
          else {
            END = Long.parseLong(line); // 1660890395247L;
          }

          lineCount++;
        }
      }

      catch (IOException e) {
        e.printStackTrace();
      }

      return this;
  }


  private PilotBuilder call() {

    try (

      Connection conn =
        DriverManager.
          getConnection(URL, "root", MYSQL_PASSWD);

      CallableStatement callableStmt =
        conn.prepareCall(callableQuery);

      PreparedStatement prepStmt =
        conn.prepareStatement(
          "SELECT count(*) FROM information_schema.tables WHERE table_name = 'events' LIMIT 1")) {

      tableExistenceHandler(prepStmt).
      callableMetaHandler(callableStmt).
      callableContentsHandler(callableStmt);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    return this;
  }



  private PilotBuilder tableExistenceHandler(PreparedStatement prepStmt) {

    try (

      ResultSet resultSet =
        prepStmt.executeQuery()) {

      resultSet.next();

      System.err.println(
        "\nDoes 'events' TABLE exist?\n " + GRN +
        (resultSet.getInt(1) != 0) + "\n" + NC);
    }
    catch (SQLException e) { e.printStackTrace(); }

    return this;
  }


  /*
    Because the ResultSetMetaData Interface does not implement the
    AutoCloseable Interface, try-with-resources is not possible.

    Manually closing the ResultSet instance prevents memory leaks;
    there's no analog close() method in the ResultSetMetaData Interface.

    Specifically, a ResultSetMetaData reference cannot appear within a
    try-with-resources clause (it has not auto-closeable support), and
    it cannot exist w/o the ResultSet's return value, which in the case
    of using a try-with-resources clause, is "closed-over" within a
    LEXICAL (or STRUCTURAL) CLOSURE - wherein ANY ASSIGNMENTS are
    EXPRESSLY PROHIBITED.

    By the way, the names of these two interrelated
    conccepts/terms are, unfortunately, easily conflated with each other.
   */
  private PilotBuilder callableMetaHandler(CallableStatement callableStmt) {

    ResultSet resultSet = null;

    try {

      callableStmt.setLong(1, BEGIN);
      callableStmt.setLong(2, END);

      resultSet = callableStmt.executeQuery();

      ResultSetMetaData meta = resultSet.getMetaData();

      int colCount = meta.getColumnCount();

      System.err.println(
        GRN + "Column Count: " + colCount +
        "\nSchema: " + NC);

      for (int i = 1; i < colCount; i++) {

        System.err.println(
          meta.getColumnName(i) + "\t | " +
          meta.getColumnTypeName(i));
      }

      System.err.println("\n\n");
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally { try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }}

    return this;
  }


  private PilotBuilder callableContentsHandler(CallableStatement callableStmt) {

    ResultSet resultSet = null;

    try (

        BufferedWriter output =
          new BufferedWriter(
            (new FileWriter(FQDN_FILE)))) {

      callableStmt.setLong(1, BEGIN);
      callableStmt.setLong(2, END);

      resultSet =
        callableStmt.executeQuery();


      StringBuilder line = null;

      while (resultSet.next()) {

        line = new StringBuilder();

        line.append(resultSet.getInt(1));
        line.append(",").append(resultSet.getString(2));
        line.append(",").append(resultSet.getString(3));
        line.append(",").append(resultSet.getString(4));
        line.append(",").append(resultSet.getString(5));
        line.append(",").append(resultSet.getString(6)).
        append("\n");

        output.write(line.toString());
      }
    }
    catch (FileNotFoundException e) { e.printStackTrace(); }
    catch (IOException e) { e.printStackTrace(); }
    catch (SQLException e) { e.printStackTrace(); }
    finally { try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }}

    return this;
  }



  public static void main(String[] any) {

    new PilotBuilder().accept().call();

    // Extractor is-a candidate for becoming a Nested Class of this (PilotBuilder) class
    Extractor.persist(Extractor.process(FQDN_FILE));;
  }


  // Temporal Bounds
  private static long BEGIN;
  private static long END;


  // I/O | Optional glimpse
  private static final String FQDN_FILE = "src/main/resources/MODEL.SQL";

  private static final String FQDN_TALISMAN_FILE = "/tmp/TALISMAN";

  // JDBC
  private static final String URL =
    "jdbc:mysql://localhost:3306/any?useSSL=false&serverTimezone=UTC";

  private static final String MYSQL_PASSWD = "greg4444";

  private static final String callableQuery = "{ call whatever(?,?) }";


  // Fluff-y
  public static final String RED = "\033[1;91m";
  public static final String GRN = "\033[1;92m";
  public static final String YLW = "\033[1;93m";
  public static final String NC = "\u001B[0m";
}
