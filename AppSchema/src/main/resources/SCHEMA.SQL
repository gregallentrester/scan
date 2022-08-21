# NOTES

#
# A. 
#    Trivia: Temporary tables do not appear
#    when this MySQL statement is invoked:
#
#      SHOW TABLES
#
#
# B. DDL Statements (appear to) run ASYNC in MySQL.
#   Manually pasting DDL statements into a MySQL Terminal must be done strategically,
#   meaning that you have to wait (a second) between pasting in the statements that create
#   the Stored Procedures ...  pasting in the statements that populate the 'events' table
#   (which is the referent of each of the Stored Procedures).
#
#
# C. (You can elect to) manually perform an ad hoc invocation of the next two MySQL statements
#    in order to generate tentative values to contribute to the synthesis of the dataset 
#    (the 'events' SQL table).
#
#      SELECT NOW();
#      SELECT UNIX_TIMESTAMP();
#
#
# D. The scaffolding DDL statements come next constitute the 
#    contents of a one-time copy/paste into a MySQL Terminal; this
#    establishes the working-model/data-model for the AppCallable app).


# START OF THE SCAFFOLDING DDL TO BE PASTED INTO A MySQL TERMINAL.

DROP DATABASE IF EXISTS any;
CREATE DATABASE any; USE any;


DROP TABLE IF EXISTS events;
CREATE TABLE events (
  ID int,
  LName varchar(255),
  FName varchar(255),
  Property varchar(255),
  Clock varchar(255),
  Instant long);


# 'whatever' is a Stored Procedure that detects transactions persisted in the
# 'events' SQL table - and only the transactions that have occurred within a 
# prescribed time-bounds (period)
DROP PROCEDURE IF EXISTS whatever;

DELIMITER //
CREATE PROCEDURE whatever(IN start long, IN end long)
BEGIN

  SELECT ID,FName,LName,Property,Clock,Instant FROM events   WHERE Instant BETWEEN start and end;

END //
DELIMITER ;


# 'dataset' is a Stored Procedure that populates test data into the 
# 'events' SQL table, thereby refreshing it to a known/sane state.
DROP PROCEDURE IF EXISTS dataset;

DELIMITER //
CREATE PROCEDURE dataset()
BEGIN

  DELETE FROM events;

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"D. Cooper","SR.","LA","Sun Aug 21 06:32:32 CDT 2022",1661081552316);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (986159283,"H. Granite","ESQ.","LV","Sun Aug 21 06:32:32 CDT 2022",1661081552978);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (778779625,"H. Cooper","SR.","LA","Sun Aug 21 06:32:33 CDT 2022",1661081553481);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (599486638,"S. Cooper","SR.","LA","Sun Aug 21 06:32:33 CDT 2022",1661081553983);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (403649448,"D. Cooper","SR.","LA","Sun Aug 21 06:32:34 CDT 2022",1661081554488);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1785282748,"R. Cooper","SR.","LA","Sun Aug 21 06:32:34 CDT 2022",1661081554993);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"U. Cooper","SR.","LA","Sun Aug 21 06:32:35 CDT 2022",1661081555498);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1385913390,"I. Granite","ESQ.","LV","Sun Aug 21 06:32:36 CDT 2022",1661081556003);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1202388164,"W. Granite","ESQ.","LV","Sun Aug 21 06:32:36 CDT 2022",1661081556508);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"A. Cooper","SR.","LA","Sun Aug 21 06:32:37 CDT 2022",1661081557014);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1906013524,"A. Granite","ESQ.","LV","Sun Aug 21 06:32:37 CDT 2022",1661081557520);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (2112623683,"L. Granite","ESQ.","LV","Sun Aug 21 06:32:38 CDT 2022",1661081558025);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"M. Cooper","SR.","LA","Sun Aug 21 06:32:38 CDT 2022",1661081558526);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (2104624335,"P. Granite","ESQ.","LV","Sun Aug 21 06:32:39 CDT 2022",1661081559031);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (986159283,"O. Granite","ESQ.","LV","Sun Aug 21 06:32:39 CDT 2022",1661081559536);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"G. Cooper","SR.","LA","Sun Aug 21 06:32:40 CDT 2022",1661081560039);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1075821100,"L. Granite","ESQ.","LV","Sun Aug 21 06:32:40 CDT 2022",1661081560544);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (893834871,"A. Granite","ESQ.","LV","Sun Aug 21 06:32:41 CDT 2022",1661081561049);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"U. Cooper","SR.","LA","Sun Aug 21 06:32:41 CDT 2022",1661081561554);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (2071936003,"F. Granite","ESQ.","LV","Sun Aug 21 06:32:42 CDT 2022",1661081562056);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1876098813,"M. Cooper","SR.","LA","Sun Aug 21 06:32:42 CDT 2022",1661081562560);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"C. Granite","ESQ.","LV","Sun Aug 21 06:32:43 CDT 2022",1661081563061);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1228840135,"V. Granite","ESQ.","LV","Sun Aug 21 06:32:43 CDT 2022",1661081563567);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1423907828,"S. Granite","ESQ.","LV","Sun Aug 21 06:32:44 CDT 2022",1661081564074);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"P. Cooper","SR.","LA","Sun Aug 21 06:32:44 CDT 2022",1661081564581);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1799807503,"L. Cooper","SR.","LA","Sun Aug 21 06:32:45 CDT 2022",1661081565083);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (417404705,"A. Granite","ESQ.","LV","Sun Aug 21 06:32:45 CDT 2022",1661081565586);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"F. Cooper","SR.","LA","Sun Aug 21 06:32:46 CDT 2022",1661081566092);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (986159283,"O. Cooper","SR.","LA","Sun Aug 21 06:32:46 CDT 2022",1661081566594);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1098795007,"R. Cooper","SR.","LA","Sun Aug 21 06:32:47 CDT 2022",1661081567097);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"O. Granite","ESQ.","LV","Sun Aug 21 06:32:47 CDT 2022",1661081567603);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1932915298,"A. Granite","ESQ.","LV","Sun Aug 21 06:32:48 CDT 2022",1661081568108);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (2125674497,"J. Granite","ESQ.","LV","Sun Aug 21 06:32:48 CDT 2022",1661081568609);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"F. Cooper","SR.","LA","Sun Aug 21 06:32:49 CDT 2022",1661081569114);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (778779625,"W. Granite","ESQ.","LV","Sun Aug 21 06:32:49 CDT 2022",1661081569618);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (986159283,"T. Granite","ESQ.","LV","Sun Aug 21 06:32:50 CDT 2022",1661081570126);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"J. Granite","ESQ.","LV","Sun Aug 21 06:32:50 CDT 2022",1661081570635);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (1523542469,"N. Cooper","SR.","LA","Sun Aug 21 06:32:51 CDT 2022",1661081571140);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (127288710,"G. Cooper","SR.","LA","Sun Aug 21 06:32:51 CDT 2022",1661081571643);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"L. Granite","ESQ.","LV","Sun Aug 21 06:32:52 CDT 2022",1661081572146);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (525888571,"S. Cooper","SR.","LA","Sun Aug 21 06:32:52 CDT 2022",1661081572647);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (721341012,"P. Granite","ESQ.","LV","Sun Aug 21 06:32:53 CDT 2022",1661081573155);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"S. Granite","ESQ.","LV","Sun Aug 21 06:32:53 CDT 2022",1661081573660);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (477151812,"I. Granite","ESQ.","LV","Sun Aug 21 06:32:54 CDT 2022",1661081574164);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (281314622,"T. Granite","ESQ.","LV","Sun Aug 21 06:32:54 CDT 2022",1661081574673);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"F. Granite","ESQ.","LV","Sun Aug 21 06:32:55 CDT 2022",1661081575177);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (635526683,"E. Granite","ESQ.","LV","Sun Aug 21 06:32:55 CDT 2022",1661081575679);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (829440129,"D. Granite","ESQ.","LV","Sun Aug 21 06:32:56 CDT 2022",1661081576183);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (39196536,"X. Granite","ESQ.","LV","Sun Aug 21 06:32:56 CDT 2022",1661081576687);

  INSERT INTO events (ID,LName,FName,Property,Clock,Instant)
  VALUES (986159283,"O. Cooper","SR.","LA","Sun Aug 21 06:32:57 CDT 2022",1661081577189);

END //
DELIMITER ;

# END OF THE SCAFFOLDING DDL TO BE PASTED INTO A MySQL TERMINAL.


# SHOW CREATE PROCEDURE dataset;


# USE any; CALL dataset();
# SELECT * FROM events;
