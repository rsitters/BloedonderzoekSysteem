package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Patiëntgegevensscherm {

    private Database db;
    private Applicatie applicatie;

    public Patiëntgegevensscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
    }

    public BorderPane createPatiëntgegevensscherm(Patiënt patiënt) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setSpacing(10);

        Label titleLabel = new Label("Patiëntgegevens");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button backButton = new Button("Terug");
        backButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox.setHgrow(backButton, Priority.ALWAYS);
        StackPane leftBox = new StackPane(titleLabel);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        topBar.getChildren().addAll(leftBox, backButton);
        borderPane.setTop(topBar);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicatie.switchToPatiëntscherm();
            }
        });


        GridPane gegevensGrid = new GridPane();
        gegevensGrid.setVgap(10);
        gegevensGrid.setHgap(10);
        gegevensGrid.setAlignment(Pos.CENTER_LEFT);

        Label titlePersonal = new Label("Persoonlijke informatie");
        titlePersonal.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label nameLabel = new Label("Naam: ");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label nameValueLabel = new Label(patiënt.getFirstName() + " " + patiënt.getLastName());

        Label birthDateLabel = new Label("Geboortedatum: ");
        birthDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthDate = patiënt.getBirthDate().format(formatter);
        Label birthDateValueLabel = new Label(formattedBirthDate);

        Label bsnLabel = new Label("BSN-nummer: ");
        bsnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label bsnValueLabel = new Label(patiënt.getBsnNumber());

        Label addressLabel = new Label("Adres: ");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label addressValueLabel = new Label(patiënt.getAddress());

        Label phoneNumberLabel = new Label("Telefoonnummer: ");
        phoneNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label phoneNumberValueLabel = new Label(patiënt.getPhoneNumber());

        Label emailLabel = new Label("E-mailadres: ");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label emailValueLabel = new Label(patiënt.getEmailAddress());

        gegevensGrid.add(titlePersonal, 0, 0, 2, 1);
        gegevensGrid.add(nameLabel, 0, 1);
        gegevensGrid.add(nameValueLabel, 1, 1);
        gegevensGrid.add(birthDateLabel, 0, 2);
        gegevensGrid.add(birthDateValueLabel, 1, 2);
        gegevensGrid.add(bsnLabel, 0, 3);
        gegevensGrid.add(bsnValueLabel, 1, 3);
        gegevensGrid.add(addressLabel, 0, 4);
        gegevensGrid.add(addressValueLabel, 1, 4);
        gegevensGrid.add(phoneNumberLabel, 0, 5);
        gegevensGrid.add(phoneNumberValueLabel, 1, 5);
        gegevensGrid.add(emailLabel, 0, 6);
        gegevensGrid.add(emailValueLabel, 1, 6);

        borderPane.setLeft(gegevensGrid);


        VBox onderzoekenBox = new VBox();
        // Voeg hier later de onderzoeken toe
        borderPane.setRight(onderzoekenBox);

        HBox knoppenBox = new HBox();
        knoppenBox.setSpacing(10);
        knoppenBox.setAlignment(Pos.CENTER_RIGHT);

        Button wijzigenButton = new Button("Wijzigen");
        wijzigenButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Button verwijderenButton = new Button("Verwijderen");
        verwijderenButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        verwijderenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    db.deletePatient(patiënt.getId());

                    applicatie.switchToPatiëntscherm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fout");
                    alert.setHeaderText("Fout bij verwijderen van patiënt");
                    alert.setContentText("Er is een fout opgetreden bij het verwijderen van de patiënt. Probeer het opnieuw.");
                    alert.showAndWait();
                }
            }
        });

        knoppenBox.getChildren().addAll(wijzigenButton, verwijderenButton);
        borderPane.setBottom(knoppenBox);

        return borderPane;
    }
}
