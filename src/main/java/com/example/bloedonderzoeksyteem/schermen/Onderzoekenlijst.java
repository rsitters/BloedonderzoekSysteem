package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Onderzoekenlijst {
    private Database db;
    private TableView<Bloedonderzoek> bloedonderzoekTableView;

    //Methode die Onderzoeklijstscherm creërt en terug geeft als BorderPane
    public BorderPane createBloedonderzoekscherm() {
        BorderPane borderPane = new BorderPane();

        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Label titleLabel = new Label("Bloedonderzoeken");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-padding: 10px;");

        bloedonderzoekTableView = createBloedonderzoekTableView();

        borderPane.setTop(titleLabel);
        borderPane.setCenter(bloedonderzoekTableView);

        return borderPane;
    }

    public TableView<Bloedonderzoek> getBloedonderzoekTableView() {
        return bloedonderzoekTableView;
    }

    //Creërt een tabel met onderzoeken zonder uitslag
    public TableView<Bloedonderzoek> createBloedonderzoekTableView() {
        TableView<Bloedonderzoek> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Bloedonderzoek, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bloedonderzoek, String> testTypeColumn = new TableColumn<>("Type");
        testTypeColumn.setCellValueFactory(new PropertyValueFactory<>("testType"));

        TableColumn<Bloedonderzoek, Integer> tubeCountColumn = new TableColumn<>("Aantal buisjes");
        tubeCountColumn.setCellValueFactory(new PropertyValueFactory<>("tubeCount"));

        TableColumn<Bloedonderzoek, LocalDate> testDateColumn = new TableColumn<>("Testdatum");
        testDateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        testDateColumn.setCellFactory(column -> new TableCell<Bloedonderzoek, LocalDate>() {
            private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            //Update de datum naar juiste format
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

        tableView.getColumns().addAll(idColumn, testTypeColumn, tubeCountColumn, testDateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            ResultSet rs = db.getAllBloodTests();
            while (rs.next()) {
                Bloedonderzoek bloedonderzoek = new Bloedonderzoek(rs);

                //Checkt of er al een uitslag bekend is en vult tabel met onderzoeken zonder uitslag
                if (!db.hasBloodTestResult(bloedonderzoek.getId())) {
                    tableView.getItems().add(bloedonderzoek);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableView;
    }
}
