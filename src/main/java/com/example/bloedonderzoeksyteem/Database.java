package com.example.bloedonderzoeksyteem;

import java.sql.*;

public class Database {

    protected Connection conn;
    private Statement stm;
    private PreparedStatement insertStatement;

    public Database(){
        String user = "root";
        String passwd="";
        String cString = "jdbc:mysql://localhost:3306/bloed_onderzoek_systeem?user=" + user + "&password="+ passwd;
        try {
            this.conn = DriverManager.getConnection(cString);
            this.stm = conn.createStatement();
            // Voeg de volgende regel toe om de voorbereide verklaring voor het invoegen van patiënten te maken
            this.insertStatement = conn.prepareStatement("INSERT INTO patient (firstname, lastname, birthdate, bsn, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            System.out.println("Kan geen verbinding maken!");
        }
    }

    public ResultSet getPatientData() throws SQLException {
        return stm.executeQuery("SELECT * FROM patient");
    }

    // Methode om een nieuwe patiënt aan de database toe te voegen
    public void addPatient(String firstName, String lastName, Date birthDate, String bsn, String address, String email, String phone) throws SQLException {
        // Gebruik de voorbereide verklaring om parameters in te voegen
        insertStatement.setString(1, firstName);
        insertStatement.setString(2, lastName);
        insertStatement.setDate(3, birthDate);
        insertStatement.setString(4, bsn);
        insertStatement.setString(5, address);
        insertStatement.setString(6, email);
        insertStatement.setString(7, phone);

        // Voer de voorbereide verklaring uit
        insertStatement.executeUpdate();
    }
}

