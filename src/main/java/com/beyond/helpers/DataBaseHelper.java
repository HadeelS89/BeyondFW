package com.beyond.helpers;
import java.sql.*;
public class DataBaseHelper {

    private static String dbUrl = "jdbc:sqlserver://10.251.120.31:55055;DatabaseName=PassRevamp_Test;encrypt=true;trustServerCertificate=true";
    private static String username = "usr-adm-qa";
    private static String password = "5IaqGR*?9_nb";
    private static Connection con;
    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(dbUrl, username, password);
    }

    public static ResultSet executeQuery(String sqlQuery) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        rs.next();
        return rs;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet rs = executeQuery("SELECT Id FROM staff WHERE USERID = (SELECT Id FROM securityUsers WHERE email = 'aut.had3402@yopmail.com');");
        String staffId = rs.getString(1);
        rs = executeQuery("SELECT Id FROM StaffSchools WHERE StaffId = '" + staffId +"';");
        String Id = rs.getString(1);
        System.out.println(Id);
    }
}
