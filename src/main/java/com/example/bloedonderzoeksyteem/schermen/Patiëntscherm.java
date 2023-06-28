package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
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

        Label titleLabel = new Label("Patiëntenlijst");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-padding: 10px;");

        patiëntTableView = createPatiëntTableView();

        Button addButton = new Button("Patiënt Toevoegen");
        addButton.setOnAction(e -> {
            // Open pop-upvenster om patiëntgegevens in te vullen
            createAddPatientPopup();
        });

        // Stijl voor de addButton
        addButton.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-text-fill: black; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-font-weight: bold;");

        borderPane.setTop(titleLabel);
        borderPane.setCenter(patiëntTableView);
        borderPane.setBottom(addButton);
        BorderPane.setAlignment(addButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(addButton, new Insets(10));

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

    private void createAddPatientPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Patiënt Toevoegen");
        popupStage.setResizable(false); // Voorkomt dat de pop-up schaalbaar is

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Stijl voor labels
        String labelStyle = "-fx-font-weight: bold;";

        // Voornaam
        Label firstNameLabel = new Label("Voornaam:");
        firstNameLabel.setStyle(labelStyle);
        TextField firstNameField = new TextField();
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);

        // Achternaam
        Label lastNameLabel = new Label("Achternaam:");
        lastNameLabel.setStyle(labelStyle);
        TextField lastNameField = new TextField();
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);

        // Geboortedatum
        Label birthDateLabel = new Label("Geboortedatum:");
        birthDateLabel.setStyle(labelStyle);
        DatePicker birthDatePicker = new DatePicker();
        gridPane.add(birthDateLabel, 0, 2);
        gridPane.add(birthDatePicker, 1, 2);

        // BSN
        Label bsnLabel = new Label("BSN:");
        bsnLabel.setStyle(labelStyle);
        TextField bsnField = new TextField();
        gridPane.add(bsnLabel, 0, 3);
        gridPane.add(bsnField, 1, 3);

        // Adres
        Label addressLabel = new Label("Adres:");
        addressLabel.setStyle(labelStyle);
        TextField addressField = new TextField();
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);

        // E-mail
        Label emailLabel = new Label("E-mail:");
        emailLabel.setStyle(labelStyle);
        TextField emailField = new TextField();
        gridPane.add(emailLabel, 0, 5);
        gridPane.add(emailField, 1, 5);

        // Telefoonnummer
        Label phoneLabel = new Label("Telefoonnummer:");
        phoneLabel.setStyle(labelStyle);
        TextField phoneField = new TextField();
        gridPane.add(phoneLabel, 0, 6);
        gridPane.add(phoneField, 1, 6);

        // Opslaan knop stijl
        String saveButtonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle(saveButtonStyle);
        saveButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String bsn = bsnField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            try {
                // Roep de addPatient() methode van de Database-klasse aan om de patiënt toe te voegen aan de database
                db.addPatient(firstName, lastName, Date.valueOf(birthDate), bsn, address, email, phone);

                // Vernieuw de tabelweergave om de nieuwe patiënt weer te geven
                patiëntTableView.getItems().clear();
                ResultSet rs = db.getPatientData();
                while (rs.next()) {
                    Patiënt patiënt = new Patiënt(rs);
                    patiëntTableView.getItems().add(patiënt);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Toon een foutmelding aan de gebruiker als er een fout optreedt tijdens het toevoegen van de patiënt
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fout");
                alert.setHeaderText("Fout bij toevoegen van patiënt");
                alert.setContentText("Er is een fout opgetreden bij het toevoegen van de patiënt. Probeer het opnieuw.");
                alert.showAndWait();
            }

            // Sluit de pop-up
            popupStage.close();
        });
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        gridPane.add(saveButton, 1, 7);

        // Padding voor alle elementen
        gridPane.setPadding(new Insets(10));

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

}
