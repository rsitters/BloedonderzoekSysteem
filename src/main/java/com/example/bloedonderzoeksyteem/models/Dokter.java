package com.example.bloedonderzoeksyteem.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Dokter {
    private Integer id;
    private String first_name;
    private String last_name;

    //Constructor voor het maken van een Dokter-object met opgegeven gegevens
    public Dokter(Integer id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    //Constructor voor het maken van een Dokter-object op basis van een ResultSet uit een databasequery
    public Dokter(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.first_name = result.getString("firstname");
        this.last_name = result.getString("lastname");
    }

    //Getters en setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }
}
