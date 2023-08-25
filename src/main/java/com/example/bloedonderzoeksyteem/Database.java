package com.example.bloedonderzoeksyteem;

import java.sql.*;

public class Database {

    protected Connection conn;
    private Statement stm;
    private PreparedStatement selectBloodTestsStatement;
    private PreparedStatement selectBloodTestResult;
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;
    private  PreparedStatement updateStatement;
    private PreparedStatement insertBloodTestStatement;
    private PreparedStatement insertResultStatement;

    public Database() {
        String user = "root";
        String passwd = "";
        String cString = "jdbc:mysql://localhost:3306/bloed_onderzoek_systeem?user=" + user + "&password=" + passwd;
        try {
            this.conn = DriverManager.getConnection(cString);
            this.stm = conn.createStatement();
            this.selectBloodTestsStatement = conn.prepareStatement("SELECT * FROM blood_test WHERE patient_id = ?");
            this.selectBloodTestResult = conn.prepareStatement("SELECT * FROM test_result WHERE test_id = ?");
            this.insertStatement = conn.prepareStatement("INSERT INTO patient (firstname, lastname, birthdate, bsn, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            this.deleteStatement = conn.prepareStatement("DELETE FROM patient WHERE id = ?");
            this.updateStatement = conn.prepareStatement("UPDATE patient SET firstname = ?, lastname = ?, birthdate = ?, bsn = ?, address = ?, email = ?, phone = ? WHERE id = ?");
            this.insertBloodTestStatement = conn.prepareStatement("INSERT INTO blood_test (patient_id, test_type, tube_count, test_date, doctor_id) VALUES (?, ?, ?, ?, ?)");
            this.insertResultStatement = conn.prepareStatement("INSERT INTO test_result (test_id, result, result_date, technician_id) VALUES(?,?,?,?)");
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

    public ResultSet getAllLaborants() throws  SQLException{
        return stm.executeQuery("SELECT * FROM lab_technician");
    }

    public ResultSet getBloodTestsForPatient(int patientId) throws SQLException {
        selectBloodTestsStatement.setInt(1, patientId);

        return selectBloodTestsStatement.executeQuery();
    }

    public ResultSet getBloodTestResult(int testId) throws SQLException{
        selectBloodTestResult.setInt(1, testId);

        return selectBloodTestResult.executeQuery();
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
        deleteStatement.setInt(1, patientId);

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

    public void addResearch(int patientId, String testType, int numberOfTubes, Date testDate, int doctor) throws SQLException {
            insertBloodTestStatement.setInt(1, patientId);
            insertBloodTestStatement.setString(2, testType);
            insertBloodTestStatement.setInt(3, numberOfTubes);
            insertBloodTestStatement.setDate(4, testDate);
            insertBloodTestStatement.setInt(5, doctor);

            insertBloodTestStatement.executeUpdate();
    }

    public void addResult(int testId, String result, Date resultDate, int laborant) throws SQLException{
        insertResultStatement.setInt(1, testId);
        insertResultStatement.setString(2, result);
        insertResultStatement.setDate(3, resultDate);
        insertResultStatement.setInt(4, laborant);

        insertResultStatement.executeUpdate();
    }

    public String getDoctorName(int doctorId) {
        try {
            ResultSet resultSet = stm.executeQuery("SELECT * FROM doctor WHERE id = " + doctorId);
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                return firstName + " " + lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPatientName(int patientId) {
        try {
            ResultSet resultSet = stm.executeQuery("SELECT * FROM patient WHERE id = " + patientId);
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                return firstName + " " + lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getLaborantName(int laborantId) {
        try {
            ResultSet resultSet = stm.executeQuery("SELECT * FROM lab_technician WHERE id = " + laborantId);
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                return firstName + " " + lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}

