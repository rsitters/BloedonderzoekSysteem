package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

public class Onderzoekscherm {
    private Database db;
    private Applicatie applicatie;
    private DateTimeFormatter formatter;

    public Onderzoekscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public BorderPane createOnderzoekgegevensscherm(Bloedonderzoek bloedonderzoek) {
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

        Label titleLabel = new Label("Onderzoekgegevens");
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
                applicatie.switchToOnderzoeklijst();
            }
        });

        GridPane onderzoekGrid = new GridPane();
        onderzoekGrid.setVgap(10);
        onderzoekGrid.setHgap(10);
        onderzoekGrid.setAlignment(Pos.CENTER_LEFT);

        Label titleOnderzoek = new Label("Onderzoeksinformatie");
        titleOnderzoek.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label testTypeLabel = new Label("Test Type: ");
        testTypeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label testTypeValueLabel = new Label(bloedonderzoek.getTestType());

        Label tubeCountLabel = new Label("Aantal Buisjes: ");
        tubeCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label tubeCountValueLabel = new Label(bloedonderzoek.getTubeCount().toString());

        Label testDateLabel = new Label("Testdatum: ");
        testDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        String formattedTestDate = bloedonderzoek.getTestDate().format(formatter);
        Label testDateValueLabel = new Label(formattedTestDate);

        Label doctorLabel = new Label("Behandelend arts: ");
        doctorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label doctorValueLabel = new Label(db.getDoctorName(bloedonderzoek.getDoctorId()));

        Label patientLabel = new Label("Patiënt: ");
        patientLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label patientValueLabel = new Label(db.getPatientName(bloedonderzoek.getPatientId()));

        onderzoekGrid.add(titleOnderzoek, 0, 0, 3, 1);
        onderzoekGrid.add(testTypeLabel, 0, 1);
        onderzoekGrid.add(testTypeValueLabel, 1, 1);
        onderzoekGrid.add(tubeCountLabel, 0, 2);
        onderzoekGrid.add(tubeCountValueLabel, 1, 2);
        onderzoekGrid.add(testDateLabel, 0, 3);
        onderzoekGrid.add(testDateValueLabel, 1, 3);
        onderzoekGrid.add(doctorLabel, 0, 4);
        onderzoekGrid.add(doctorValueLabel, 1, 4);
        onderzoekGrid.add(patientLabel, 0, 5);
        onderzoekGrid.add(patientValueLabel, 1, 5);

        borderPane.setLeft(onderzoekGrid);

        return borderPane;
    }
}
