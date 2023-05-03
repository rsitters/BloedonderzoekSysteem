package com.example.bloedonderzoeksyteem;

import java.sql.*;

public class Database {

    protected Connection conn;

    public Database(){
        String user = "root";
        String passwd="";
        String cString = "jdbc:mysql://localhost:3306/bloed_onderzoek_systeem?user=" + user + "&password="+ passwd;
        try {
            this.conn = DriverManager.getConnection(cString);
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Kan geen verbinding maken!");
        }
    }
}