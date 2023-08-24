package com.example.bloedonderzoeksyteem.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Onderzoekuitslag {
    private Integer id;
    private Integer test_id;
    private Integer technician_id;
    private String result;
    private LocalDate result_date;

    public Onderzoekuitslag(Integer id, Integer test_id, Integer technician_id, String result, LocalDate result_date) {
        this.id = id;
        this.test_id = test_id;
        this.technician_id = technician_id;
        this.result = result;
        this.result_date = result_date;
    }

    public Onderzoekuitslag(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.test_id = result.getInt("test_id");
        this.technician_id = result.getInt("technician_id");
        this.result = result.getString("result");
        this.result_date = result.getDate("result_date").toLocalDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return test_id;
    }

    public void setTestId(Integer test_id) {
        this.test_id = test_id;
    }

    public Integer getTechnicianId() {
        return technician_id;
    }

    public void setTechnicianId(Integer technician_id) {
        this.technician_id = technician_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getResultDate() {
        return result_date;
    }

    public void setResultDate(LocalDate result_date) {
        this.result_date = result_date;
    }
}
