package com.beyond.helpers;

import com.beyond.common.Base;

import java.sql.*;

public class DBHelper extends Base {
    public static Connection connObj;
    Statement stmt = null;
    static ResultSet rs = null;

   // public static String JDBC_Data = "jdbc:sqlserver://releaseprod:1433;databaseName=Payroll;user=sa;password=$$saRP";
  // public static String JDBC_Data = "jdbc:oracle:thin:@localhost:1521:xe""system","oracle";
    public static void getDbConnection() throws SQLException {

    try {
        System.out.println( "environment = "+ ReadWriteHelper.readEnvironment());
        //decide conn string as per env parameter
        String connUrl = "";
        if (ReadWriteHelper.readEnvironment().equalsIgnoreCase("tst")) {
            connUrl = "jdbc:oracle:thin:@//10.251.140.163:1521/mesistst";
            System.out.println("tst database connection");
            connObj = DriverManager.getConnection(connUrl, "admassist", "admassist");
        }
        else if(ReadWriteHelper.readEnvironment().equalsIgnoreCase("stg")) {
           connUrl = "jdbc:oracle:thin:@//10.251.120.47:1521/esistst";
            System.out.println("stg database connection");
            connObj = DriverManager.getConnection(connUrl, "admassist", "adec0jxtst");
        }else {
            connUrl = "jdbc:oracle:thin:@//10.249.1.80:1541/adprd_ad";
            System.out.println("pre_prod database connection");
            connObj = DriverManager.getConnection(connUrl, "aal_ta", "aal_ta");

        }
            Class.forName("oracle.jdbc.driver.OracleDriver");


        if (connObj != null) {

            DatabaseMetaData metaObj = (DatabaseMetaData) connObj.getMetaData();

            System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());

        }

    } catch (Exception sqlException) {

        sqlException.printStackTrace();

    }

    Statement stmt = connObj.createStatement();
    String query = "update students\n" +
            "set Student_STATE_ID = NULL where Student_STATE_ID = '784193965763050'";
    //  String query2="Commit";
    // boolean gotResults = stmt.execute(query);
    boolean gotResults = stmt.execute(query);
    if (!gotResults) {
        System.out.println("No results returned");
    } else {
        rs = stmt.getResultSet();
        System.out.println("connection done ");
    }


    }
public static void main (String args[]) throws SQLException {

    getDbConnection();

}
}
