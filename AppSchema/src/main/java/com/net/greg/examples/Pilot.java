package net.greg.examples.salient;

import java.io.*;
import java.nio.file.*;

import java.util.*;
import java.util.concurrent.*;


public final class Pilot {

  private static int CHAOSFACTOR1 = 7;
  private static int CHAOSFACTOR2 = 13;
  private static int CHAOSFACTOR3 = 17;

  private static final Integer COHORT_LIMIT = 50;
  private static final Long CSAF = 500L;


  public static void main(String[] factors) {

    generateDDLFile(COHORT_LIMIT, CSAF, factors);
  }


  private static void generateDDLFile(
      int COHORT_LIMIT, long CSAF, String[] factors) {


    if (factors.length == 3) {

      CHAOSFACTOR1 = Integer.parseInt(factors[0]);
      CHAOSFACTOR2 = Integer.parseInt(factors[1]);
      CHAOSFACTOR3 = Integer.parseInt(factors[2]);
    }

    System.err.println(

      YLW + "\nCHAOSFACTOR1 " + CHAOSFACTOR1 +
      "\nCHAOSFACTOR2 " + CHAOSFACTOR2 +
      "\nCHAOSFACTOR3 " + CHAOSFACTOR3 + NC);


    new File(FQDN_TALISMAN_FILE).delete();

    try (

      BufferedWriter talisman =
        new BufferedWriter(
          (new FileWriter(FQDN_TALISMAN_FILE)));


        BufferedWriter fileDDLContent =
          new BufferedWriter(
            (new FileWriter(FQDN_FILE)))) {

        Date NOW = null;
        long NOWLONG = 0L;

        long BEGIN_BOUNDS = 0L;
        long END_BOUNDS = 0L;

        System.err.println(
          GRN + "\n\nClock-slew Avoidance Factor: 5s" +
          YLW + "\nSynthesizing " + COHORT_LIMIT + " Cohorts" +
          NC + "\n");

        fileDDLContent.write(tranch1.toString());
        fileDDLContent.write(tranch2.toString());
        fileDDLContent.write(tranch3.toString());
        fileDDLContent.write(tranch4.toString());
        fileDDLContent.write(tranch5.toString());

        fileDDLContent.write(BANNER1);

        fileDDLContent.write(DROP);

        fileDDLContent.write(OPEN_DELIMITER);
        fileDDLContent.write(CREATE_PROC);
        fileDDLContent.write(BEGIN_TAG);

        fileDDLContent.write(DELETE_STMT);


        for (int ndx=0; ndx < COHORT_LIMIT; ndx++) {

          int LOCALE_DISCRIMINATOR =
            new Random().nextInt(17 - 3);

          int ID_INITIAL_DISCRIMINATOR =
            new Random().nextInt(90 - 65) + 65;

          char INITIAL = (char)
            ID_INITIAL_DISCRIMINATOR;

          String CUSTOMER_UUID =
            Integer.toString(
              Math.abs(
                new Random(System.currentTimeMillis()).
                  nextInt()));

          if (ndx == 0 || ndx == 1 || ndx == 2) {
            duplicateIDs.put(ndx,CUSTOMER_UUID);
          }

          NOW = new Date();
          NOWLONG = NOW.getTime();

System.err.println(
  "\n" + NOW + "\n" + NOWLONG);

          fileDDLContent.write(INSERT_DDL);
          fileDDLContent.write(VALUES_DDL1);

          if (ndx > CHAOSFACTOR1 && ndx % CHAOSFACTOR1 == 0) {

            System.err.println(
              YLW + "duplicatedAccountIDs, CASE " + CHAOSFACTOR1 + NC);
            fileDDLContent.write(duplicateIDs.get(0));
          }
          else if (ndx > CHAOSFACTOR2 && ndx % CHAOSFACTOR2 == 0) {

            System.err.println(
              YLW + "duplicatedAccountIDs, CASE " + CHAOSFACTOR2 + NC);
            fileDDLContent.write(duplicateIDs.get(1));
          }
          else if (ndx > CHAOSFACTOR3 && ndx % CHAOSFACTOR3 == 0) {

            System.err.println(
              YLW + "duplicatedAccountIDs, CASE " + CHAOSFACTOR3 + NC);
            fileDDLContent.write(duplicateIDs.get(2));
          }
          else {
            fileDDLContent.write(CUSTOMER_UUID);
          }

          fileDDLContent.write(",\"" + INITIAL);

          if (LOCALE_DISCRIMINATOR %2 == 0) {
            fileDDLContent.write(VALUES_DDL2);
          }
          else {
            fileDDLContent.write(VALUES_DDL3);
          }

          fileDDLContent.write(NOW + "\"");
          fileDDLContent.write("," +  NOWLONG);
          fileDDLContent.write(VALUES_DDL_CAP);

          if (ndx == 0) {
            BEGIN_BOUNDS = NOWLONG;
          }
          else if (ndx == COHORT_LIMIT-1) {
            END_BOUNDS = NOWLONG;
          }

          // avoid clock-slew distortion
          Thread.currentThread().sleep(CSAF);
        }

        fileDDLContent.write(END_DELIMITER);
        fileDDLContent.write(META_DELIMITER);

        fileDDLContent.write("\n# END OF THE SCAFFOLDING DDL TO BE PASTED INTO A MySQL TERMINAL.");

        fileDDLContent.write("\n\n\n# SHOW CREATE PROCEDURE dataset;\n");

        fileDDLContent.write(
          "\n\n# USE any; CALL dataset();\n");

        fileDDLContent.write("# SELECT * FROM events;\n");


        talisman.write(BEGIN_BOUNDS + "\n");
        talisman.write(Long.toString(END_BOUNDS));
      }
      catch (InterruptedException e) {  }
      catch (FileNotFoundException e) {  }
      catch (IOException e) {  }

      System.err.println(
        GRN + "\n\nPurposefully-created duplicate AccountIDs\n\t " +
        NC + duplicateIDs + "\n\n");

      System.err.println(
        YLW + "NB  Be sure that the 'events' file was purged in the AppCallable app:\n\n" +
        NC + "        rm ~/stage/AppExtract/src/main/resources/events.txt\n" +
        "                       ...  ...  ...\n\n");
  }


  private static final Map<Integer,String> duplicateIDs =
    new ConcurrentHashMap();

  private static final String FQDN_FILE =
    "src/main/resources/SCHEMA.SQL";

  private static final String FQDN_TALISMAN_FILE =
      "/tmp/TALISMAN";

  private static final String BANNER1 =
    "\n# 'dataset' is a Stored Procedure that populates test data into the " +
    "\n# 'events' SQL table, thereby refreshing it to a known/sane state.";

  private static final String DROP =
    "\nDROP PROCEDURE IF EXISTS dataset;\n\n";

  private static final String OPEN_DELIMITER = "DELIMITER //\n";
  private static final String CREATE_PROC = "CREATE PROCEDURE dataset()\n";
  private static final String BEGIN_TAG = "BEGIN\n\n";

  private static final String DELETE_STMT = "  DELETE FROM events;\n\n";


  private static final String END_DELIMITER = "END //\n";
  private static final String META_DELIMITER = "DELIMITER ;\n";


  private static String INSERT_DDL =
    "  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)\n";

  private static String VALUES_DDL1 =
    "  VALUES (";

  private static String VALUES_DDL2 =
    ". Granite\",\"ESQ.\",\"LV\",\"";

  private static String VALUES_DDL3 =
    ". Cooper\",\"SR.\",\"LA\",\"";

  private static String VALUES_DDL_CAP = ");\n\n";


  private static String L1 = "# NOTES\n\n";
  private static String L2 = "#\n";
  private static String L3 = "# A. \n";
  private static String L4 = "#    Trivia: Temporary tables do not appear\n";
  private static String L5 = "#    when this MySQL statement is invoked:\n";
  private static String L6 = "#\n";
  private static String L7 = "#      SHOW TABLES\n";
  private static String L8 = "#\n";
  private static String L9 = "#\n";
  private static String L10 = "# B. DDL Statements (appear to) run ASYNC in MySQL.\n";
  private static String L11 = "#   Manually pasting DDL statements into a MySQL Terminal must be done strategically,\n";
  private static String L12 = "#   meaning that you have to wait (a second) between pasting in the statements that create\n";
  private static String L13 = "#   the Stored Procedures ...  pasting in the statements that populate the 'events' table\n";
  private static String L14 = "#   (which is the referent of each of the Stored Procedures).\n";
  private static String L15 = "#\n#\n";

  private static String L16 = "# C. (You can elect to) manually perform an ad hoc invocation of the next two MySQL statements\n";
  private static String L17 = "#    in order to generate tentative values to contribute to the synthesis of the dataset \n"; private static String L18 = "#    (the 'events' SQL table).\n";
  private static String L19 = "#\n";
  private static String L20 = "#      SELECT NOW();\n";
  private static String L21 = "#      SELECT UNIX_TIMESTAMP();\n";
  private static String L22 = "#\n";
  private static String L23 = "#\n";
  private static String L24 = "# D. The scaffolding DDL statements come next constitute the \n";
  private static String L25 = "#    contents of a one-time copy/paste into a MySQL Terminal; this\n";
  private static String L26 = "#    establishes the working-model/data-model for the AppCallable app).\n\n\n";


  private static String L27 = "# START OF THE SCAFFOLDING DDL TO BE PASTED INTO A MySQL TERMINAL.\n\n";

  private static String L28 = "DROP DATABASE IF EXISTS any;\n";
  private static String L29 = "CREATE DATABASE any; USE any;\n\n\n";


  private static String L30 = "DROP TABLE IF EXISTS events;\n";
  private static String L31 = "CREATE TABLE events (\n";
  private static String L32 = "  ID int,\n";
  private static String L33 = "  LName varchar(255),\n";
  private static String L34 = "  FName varchar(255),\n";
  private static String L35 = "  Property varchar(255),\n";
  private static String L36 = "  Clock varchar(255),\n";
  private static String L37 = "  Instant long);\n\n\n";


  private static String L38 = "# 'whatever' is a Stored Procedure that detects transactions persisted in the\n";
  private static String L39 = "# 'events' SQL table - and only the transactions that have occurred within a \n";
  private static String L40 = "# prescribed time-bounds (period)\n";

  private static String L41 = "DROP PROCEDURE IF EXISTS whatever;\n\n";

  private static String L42 = "DELIMITER //\n";
  private static String L43 = "CREATE PROCEDURE whatever(IN start long, IN end long)\n";
  private static String L44 = "BEGIN\n\n";

  private static String L45 = "  SELECT ID,FName,LName,Property,Clock,Instant FROM events ";
  private static String L46 = "  WHERE Instant BETWEEN start and end;\n\n";

  private static String L47 = "END //\n";
  private static String L48 = "DELIMITER ;\n\n";


  private static StringBuilder tranch1 = new StringBuilder();
  private static StringBuilder tranch2 = new StringBuilder();
  private static StringBuilder tranch3 = new StringBuilder();
  private static StringBuilder tranch4 = new StringBuilder();
  private static StringBuilder tranch5 = new StringBuilder();


  /*
   Consider a static block to be similar to an anonymous
   method; or, it can be likened to a class-level constructor
   */
  static {
    tranch1.append(L1).append(L2).append(L3).append(L4).append(L5).append(L6).append(L7).append(L8).append(L9);
    tranch2.append(L10).append(L11).append(L12).append(L13).append(L14).append(L15).append(L16).append(L17).append(L18).append(L19);
    tranch3.append(L20).append(L21).append(L22).append(L23).append(L24).append(L25).append(L26).append(L27).append(L28).append(L29);
    tranch4.append(L30).append(L31).append(L32).append(L33).append(L34).append(L35).append(L36).append(L37).append(L38).append(L39);
    tranch5.append(L40).append(L41).append(L42).append(L43).append(L44).append(L45).append(L46).append(L47).append(L48);
  }


  public static final String RED = "\033[1;91m";
  public static final String GRN = "\033[1;92m";
  public static final String YLW = "\033[1;93m";
  public static final String NC = "\u001B[0m";
}


/*

/Users/greg/stage/AppSchema/src/main/resources/

^^^
A. PROBLEM
use any; CALL (1660703900,1660703925);
ERROR 1290 (HY000): The MySQL server is running with the --secure-file-priv option so it cannot execute this statement


B. CHECK
mysql> SHOW VARIABLES LIKE "secure_file_priv";
+------------------+-------+
| Variable_name    | Value |
+------------------+-------+
| secure_file_priv | NULL  |
+------------------+-------+
1 row in set (0.00 sec)

mysql> show variables like "local_infile";
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| local_infile  | OFF   |
+---------------+-------+
1 row in set (0.00 sec)

mysql> v\set global local_infile = 1;


C. THEN
mysql> show variables like "local_infile";
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| local_infile  | ON    |
+---------------+-------+
1 row in set (0.01 sec)


OUTCOME
NO RESOLUTION, UNKNOWN ILL SIDE EFFECTS

ref  https://stackoverflow.com/questions/32737478/how-should-i-resolve-secure-file-priv-in-mysql


^^^
What to do when you have no configuration file
Sometimes, your MySQL installation didn’t come with a config file.

This happens to me when I install the official MySQL community edition for Mac OS, which doesn’t add a default my.cnf file to start the server.

In that case, you should create your own configuration file in a location that will be picked up by MySQL.

MySQL will look in the following locations for a .cnf file:

/etc/my.cnf
/etc/mysql/my.cnf
/usr/local/mysql/etc/my.cnf
~/.my.cnf


^^^
MYSQL PWD greg4444
*/
