package com.example.bloedonderzoeksyteem.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Patiënt {
    private Integer id;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private String bsn_number;
    private String address;
    private String phone_number;
    private String email_address;

    public Patiënt(Integer id, String first_name, String last_name, LocalDate birth_date, String bsn_number, String address, String phone_number, String email_address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.bsn_number = bsn_number;
        this.address = address;
        this.phone_number = phone_number;
        this.email_address = email_address;
    }

    public Patiënt(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.first_name = result.getString("firstname");
        this.last_name = result.getString("lastname");
        this.birth_date = LocalDate.parse(result.getString("birthdate"));
        this.bsn_number = result.getString("bsn");
        this.address = result.getString("address");
        this.phone_number = result.getString("phone");
        this.email_address = result.getString("email");
    }

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

    public LocalDate getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getBsnNumber() {
        return bsn_number;
    }

    public void setBsnNumber(String bsn_number) {
        this.bsn_number = bsn_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmailAddress() {
        return email_address;
    }

    public void setEmailAddress(String email_address) {
        this.email_address = email_address;
    }
}
