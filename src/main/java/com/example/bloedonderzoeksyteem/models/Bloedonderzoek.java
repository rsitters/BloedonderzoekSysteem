package com.example.bloedonderzoeksyteem.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Bloedonderzoek {
    private Integer id;
    private String test_type;
    private Integer tube_count;
    private LocalDate test_date;
    private Integer doctor_id;
    private Integer patient_id;

    //Constructor voor het maken van een Bloedonderzoek-object met opgegeven gegevens
    public Bloedonderzoek(Integer id, String test_type, Integer tube_count, LocalDate test_date, Integer doctor_id, Integer patient_id) {
        this.id = id;
        this.test_type = test_type;
        this.tube_count = tube_count;
        this.test_date = test_date;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
    }

    //Constructor voor het maken van een Bloedonderzoek-object op basis van een ResultSet uit een databasequery
    public Bloedonderzoek(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.test_type = result.getString("test_type");
        this.tube_count = result.getInt("tube_count");
        this.test_date = result.getDate("test_date").toLocalDate();
        this.doctor_id = result.getInt("doctor_id");
        this.patient_id = result.getInt("patient_id");
    }

    //Getters en setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestType() {
        return test_type;
    }

    public void setTestType(String test_type) {
        this.test_type = test_type;
    }

    public Integer getTubeCount() {
        return tube_count;
    }

    public void setTubeCount(Integer tube_count) {
        this.tube_count = tube_count;
    }

    public LocalDate getTestDate() {
        return test_date;
    }

    public void setTestDate(LocalDate test_date) {
        this.test_date = test_date;
    }

    public Integer getDoctorId() {
        return doctor_id;
    }

    public void setDoctorId(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Integer getPatientId() {
        return patient_id;
    }

    public void setPatientId(Integer patient_id) {
        this.patient_id = patient_id;
    }
}
