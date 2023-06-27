package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        patiëntTableView = createPatiëntTableView();
        borderPane.setCenter(patiëntTableView);

        return borderPane;
    }

    public TableView<Patiënt> getPatiëntTableView() {
        return patiëntTableView;
    }

    public TableView<Patiënt> createPatiëntTableView() {
        TableView<Patiënt> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Patiënt, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Patiënt, String> firstNameColumn = new TableColumn<>("Voornaam");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Patiënt, String> lastNameColumn = new TableColumn<>("Achternaam");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Patiënt, LocalDate> birthDateColumn = new TableColumn<>("Geboortedatum");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        birthDateColumn.setCellFactory(column -> new TableCell<Patiënt, LocalDate>() {
            private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        tableView.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, birthDateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            ResultSet rs = db.getPatientData();
            while (rs.next()) {
                Patiënt patiënt = new Patiënt(rs);
                tableView.getItems().add(patiënt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableView;
    }
}
