package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Patiëntscherm {

    private Database db;
    private TableView<Patiënt> patiëntTableView;

    public BorderPane createPatiëntscherm() {
        BorderPane borderPane = new BorderPane();

        // Connect to DB
        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        patiëntTableView = new TableView<Patiënt>();
        patiëntTableView.setEditable(true);

        TableColumn<Patiënt, String> id_column = new TableColumn<Patiënt, String>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("id"));

        TableColumn<Patiënt, String> first_name_column = new TableColumn<Patiënt, String>("Voornaam");
        first_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("firstName"));

        TableColumn<Patiënt, String> last_name_column = new TableColumn<Patiënt, String>("Achternaam");
        last_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("lastName"));

        TableColumn<Patiënt, String> birthdate_name_column = new TableColumn<Patiënt, String>("Geboortedatum");
        birthdate_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("birthDate"));

//        TableColumn<Patiënt, String> bsn_name_column = new TableColumn<Patiënt, String>("BSN");
//        bsn_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("bsnNumber"));
//
//        TableColumn<Patiënt, String> address_name_column = new TableColumn<Patiënt, String>("Adres");
//        address_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("address"));
//
//        TableColumn<Patiënt, String> phone_name_column = new TableColumn<Patiënt, String>("Telefoonnummer");
//        phone_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("phoneNumber"));
//
//        TableColumn<Patiënt, String> email_name_column = new TableColumn<Patiënt, String>("Emailadres");
//        email_name_column.setCellValueFactory(new PropertyValueFactory<Patiënt, String>("emailAddress"));

        patiëntTableView.getColumns().addAll(id_column,first_name_column, last_name_column, birthdate_name_column);
        patiëntTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try{
            ResultSet rs = db.getPatientData();
            while (rs.next()){
                // Create new object for each donor
                Patiënt patiënt = new Patiënt(rs);
                // Insert into table
                patiëntTableView.getItems().add(patiënt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(patiëntTableView);
        return borderPane;
    }
}
