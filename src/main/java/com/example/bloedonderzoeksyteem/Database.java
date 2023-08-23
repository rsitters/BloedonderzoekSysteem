package com.example.bloedonderzoeksyteem;

import java.sql.*;

public class Database {

    protected Connection conn;
    private Statement stm;
    private PreparedStatement selectBloodTestsStatement;
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement deleteBloodtestStatement;
    private  PreparedStatement updateStatement;
    private PreparedStatement insertBloodTestStatement;

    public Database() {
        String user = "root";
        String passwd = "";
        String cString = "jdbc:mysql://localhost:3306/bloed_onderzoek_systeem?user=" + user + "&password=" + passwd;
        try {
            this.conn = DriverManager.getConnection(cString);
            this.stm = conn.createStatement();
            this.selectBloodTestsStatement = conn.prepareStatement("SELECT * FROM blood_test WHERE patient_id = ?");
            this.insertStatement = conn.prepareStatement("INSERT INTO patient (firstname, lastname, birthdate, bsn, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            this.deleteStatement = conn.prepareStatement("DELETE FROM patient WHERE id = ?");
            this.deleteBloodtestStatement = conn.prepareStatement("DELETE FROM blood_test WHERE patient_id = ?");
            this.updateStatement = conn.prepareStatement("UPDATE patient SET firstname = ?, lastname = ?, birthdate = ?, bsn = ?, address = ?, email = ?, phone = ? WHERE id = ?");
            this.insertBloodTestStatement = conn.prepareStatement("INSERT INTO blood_test (patient_id, test_type, tube_count, test_date, doctor_id) VALUES (?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            System.out.println("Kan geen verbinding maken!");
        }
    }

    public ResultSet getPatientData() throws SQLException {
        return stm.executeQuery("SELECT * FROM patient");
    }

    public ResultSet getAllBloodTests() throws SQLException{
        return stm.executeQuery("SELECT * FROM blood_test");
    }

    public ResultSet getAllDoctors() throws  SQLException{
        return stm.executeQuery("SELECT * FROM doctor");
    }

    public ResultSet getBloodTestsForPatient(int patientId) throws SQLException {
        selectBloodTestsStatement.setInt(1, patientId);

        return selectBloodTestsStatement.executeQuery();
    }

    public void addPatient(String firstName, String lastName, Date birthDate, String bsn, String address, String email, String phone) throws SQLException {
        insertStatement.setString(1, firstName);
        insertStatement.setString(2, lastName);
        insertStatement.setDate(3, birthDate);
        insertStatement.setString(4, bsn);
        insertStatement.setString(5, address);
        insertStatement.setString(6, email);
        insertStatement.setString(7, phone);

        insertStatement.executeUpdate();
    }

    public void deletePatient(int patientId) throws SQLException {
        deleteBloodtestStatement.setInt(1, patientId);
        deleteStatement.setInt(1, patientId);

        deleteBloodtestStatement.executeUpdate();
        deleteStatement.executeUpdate();
    }

    public void updatePatient(int patientId, String firstName, String lastName, Date birthDate, String bsn, String address, String email, String phone) throws SQLException {
        updateStatement.setString(1, firstName);
        updateStatement.setString(2, lastName);
        updateStatement.setDate(3, birthDate);
        updateStatement.setString(4, bsn);
        updateStatement.setString(5, address);
        updateStatement.setString(6, email);
        updateStatement.setString(7, phone);
        updateStatement.setInt(8, patientId);

        updateStatement.executeUpdate();
    }

    public void addResearch(int patientId, String testType, int numberOfTubes, Date testDate, int doctor) throws SQLException {{
            insertBloodTestStatement.setInt(1, patientId);
            insertBloodTestStatement.setString(2, testType);
            insertBloodTestStatement.setInt(3, numberOfTubes);
            insertBloodTestStatement.setDate(4, testDate);
            insertBloodTestStatement.setInt(5, doctor);

            insertBloodTestStatement.executeUpdate();
        }
    }
}

