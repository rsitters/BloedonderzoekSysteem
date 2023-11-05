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
    private PreparedStatement checkForResultStatement;

    public Database() {
        //Database gegevens
        String user = "bloedonderzoek_robin";
        String passwd = "E6-%25kt`Xci{J@";
        String cString = "jdbc:mysql://adainforma.tk/bp2_bloedonderzoek_robin?user=" + user + "&password=" + passwd;
        try {
            //Maakt verbinding met database
            this.conn = DriverManager.getConnection(cString);
            //Maakt een Statement-object voor algemene SQL-query's
            this.stm = conn.createStatement();
            //Voorbereide statements
            this.selectBloodTestsStatement = conn.prepareStatement("SELECT * FROM blood_test WHERE patient_id = ?");
            this.selectBloodTestResult = conn.prepareStatement("SELECT * FROM test_result WHERE test_id = ?");
            this.insertStatement = conn.prepareStatement("INSERT INTO patient (firstname, lastname, birthdate, bsn, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            this.deleteStatement = conn.prepareStatement("DELETE FROM patient WHERE id = ?");
            this.updateStatement = conn.prepareStatement("UPDATE patient SET firstname = ?, lastname = ?, birthdate = ?, bsn = ?, address = ?, email = ?, phone = ? WHERE id = ?");
            this.insertBloodTestStatement = conn.prepareStatement("INSERT INTO blood_test (patient_id, test_type, tube_count, test_date, doctor_id) VALUES (?, ?, ?, ?, ?)");
            this.insertResultStatement = conn.prepareStatement("INSERT INTO test_result (test_id, result, result_date, technician_id) VALUES(?,?,?,?)");
            this.checkForResultStatement = conn.prepareStatement("SELECT COUNT(*) AS count FROM test_result WHERE test_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kan geen verbinding maken!");
        }
    }

    //Haalt alle patiëntgegevens op
    public ResultSet getPatientData() throws SQLException {
        return stm.executeQuery("SELECT * FROM patient");
    }

    //Haalt alle bloedonderzoeken op
    public ResultSet getAllBloodTests() throws SQLException{
        return stm.executeQuery("SELECT * FROM blood_test");
    }

    //Haalt alle dokters op
    public ResultSet getAllDoctors() throws  SQLException{
        return stm.executeQuery("SELECT * FROM doctor");
    }

    //Haalt alle laboranten op
    public ResultSet getAllLaborants() throws  SQLException{
        return stm.executeQuery("SELECT * FROM lab_technician");
    }

    //Haalt alle bloedonderzoeken op voor specifieke patiënt
    public ResultSet getBloodTestsForPatient(int patientId) throws SQLException {
        selectBloodTestsStatement.setInt(1, patientId);

        return selectBloodTestsStatement.executeQuery();
    }

    //Haalt uitslag op voor een specifiek bloedonderzoek
    public ResultSet getBloodTestResult(int testId) throws SQLException{
        selectBloodTestResult.setInt(1, testId);

        return selectBloodTestResult.executeQuery();
    }

    //Voegt een nieuwe patiënt toe
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

    //Verwijdert een patiënt
    public void deletePatient(int patientId) throws SQLException {
        deleteStatement.setInt(1, patientId);

        deleteStatement.executeUpdate();
    }

    //Wijzigt een patiënt
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

    //Voegt een bloedonderzoek toe
    public void addResearch(int patientId, String testType, int numberOfTubes, Date testDate, int doctor) throws SQLException {
            insertBloodTestStatement.setInt(1, patientId);
            insertBloodTestStatement.setString(2, testType);
            insertBloodTestStatement.setInt(3, numberOfTubes);
            insertBloodTestStatement.setDate(4, testDate);
            insertBloodTestStatement.setInt(5, doctor);

            insertBloodTestStatement.executeUpdate();
    }

    //Voegt een uitslag toe
    public void addResult(int testId, String result, Date resultDate, int laborant) throws SQLException{
        insertResultStatement.setInt(1, testId);
        insertResultStatement.setString(2, result);
        insertResultStatement.setDate(3, resultDate);
        insertResultStatement.setInt(4, laborant);

        insertResultStatement.executeUpdate();
    }

    //Controleert of er een uitslag bekend is voor een specifieke test
    public boolean hasBloodTestResult(int bloodTestId) {
        try {
            checkForResultStatement.setInt(1, bloodTestId);

            ResultSet resultSet = checkForResultStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }

            resultSet.close();
            checkForResultStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Haalt de voor- en achternaam van een dokter op
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

    //Haalt de voor- en achternaam van een patiënt op
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

    //Haalt de voor- en achternaam van een laborant op
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

