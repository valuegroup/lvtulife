package com.lvtulife.system.component.onekeyBuild;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class DB {
    private static String sDriver;
    private static String sURL;
    private static String sUser;
    private static String sPass;

    private static ResourceBundle bundle = PropertyResourceBundle.getBundle("db");

    static {
        /*Configuration rc = new Configuration("db.properties");// 相对路径
        sDriver = rc.getValue("jdbc.driver");// 以下读取properties文件的值
        sURL = rc.getValue("jdbc.url");
        sUser = rc.getValue("jdbc.username");
        sPass = rc.getValue("jdbc.password");*/

        sDriver = bundle.getString("jdbc.driver");// 以下读取properties文件的值
        sURL = bundle.getString("jdbc.url");
        sUser = bundle.getString("jdbc.username");
        sPass = bundle.getString("jdbc.password");
    }

    public static Connection getConn() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(sDriver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC CLASS ERR");
        }
        conn = DriverManager.getConnection(sURL, sUser, sPass);
        return conn;
    }
}