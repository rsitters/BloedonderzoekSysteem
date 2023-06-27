package com.example.bloedonderzoeksyteem;

import java.sql.*;

public class Database {

    protected Connection conn;
    private Statement stm;

    public Database(){
        String user = "root";
        String passwd="";
        String cString = "jdbc:mysql://localhost:3306/bloed_onderzoek_systeem?user=" + user + "&password="+ passwd;
        try {
            this.conn = DriverManager.getConnection(cString);
            this.stm = conn.createStatement(); // Voeg deze regel toe
        } catch (SQLException e) {
            System.out.println("Kan geen verbinding maken!");
        }
    }

    public ResultSet getPatientData() throws SQLException{
        return stm.executeQuery("SELECT * FROM patient");
    }
}
