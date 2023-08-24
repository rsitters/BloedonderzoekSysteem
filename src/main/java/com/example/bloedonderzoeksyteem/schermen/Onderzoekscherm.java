package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Onderzoekuitslag;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class Onderzoekscherm {
    private Database db;
    private Applicatie applicatie;
    private DateTimeFormatter formatter;

    public Onderzoekscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public BorderPane createOnderzoekgegevensscherm(Bloedonderzoek bloedonderzoek, Onderzoekuitslag onderzoekuitslag) {
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

        GridPane mainGrid = new GridPane();
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);
        mainGrid.setAlignment(Pos.CENTER_LEFT);

        Label titleOnderzoek = new Label("Onderzoeksinformatie");
        titleOnderzoek.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label testIdLabel = new Label("Test ID: ");
        testIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label testIdValue = new Label(bloedonderzoek.getId().toString());

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

        Label titleUitslag = new Label("Uitslag");
        titleUitslag.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        mainGrid.add(titleOnderzoek, 0, 0, 3, 1);
        mainGrid.add(testIdLabel,0,1);
        mainGrid.add(testIdValue,1,1);
        mainGrid.add(testTypeLabel, 0, 2);
        mainGrid.add(testTypeValueLabel, 1, 2);
        mainGrid.add(tubeCountLabel, 0, 3);
        mainGrid.add(tubeCountValueLabel, 1, 3);
        mainGrid.add(testDateLabel, 0, 4);
        mainGrid.add(testDateValueLabel, 1, 4);
        mainGrid.add(doctorLabel, 0, 5);
        mainGrid.add(doctorValueLabel, 1, 5);
        mainGrid.add(patientLabel, 0, 6);
        mainGrid.add(patientValueLabel, 1, 6);
        mainGrid.add(titleUitslag, 0, 8, 3, 1);

        if (onderzoekuitslag != null) {
            Label uitslagIdLabel = new Label("Uitslag ID: ");
            uitslagIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            Label uitslagIdValue = new Label(onderzoekuitslag.getId().toString());

            Label resultLabel = new Label("Resultaat: ");
            resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            Label resultValue = new Label(onderzoekuitslag.getResult());

            Label resultDateLabel = new Label("Resultaatdatum: ");
            resultDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            String formattedResultDate = onderzoekuitslag.getResultDate().format(formatter);
            Label resultDateValue = new Label(formattedResultDate);

            mainGrid.add(uitslagIdLabel, 0, 9);
            mainGrid.add(uitslagIdValue, 1, 9);
            mainGrid.add(resultLabel, 0, 10);
            mainGrid.add(resultValue, 1, 10);
            mainGrid.add(resultDateLabel, 0, 11);
            mainGrid.add(resultDateValue, 1, 11);
        } else {
            Label geenUitslagLabel = new Label("Geen uitslag bekend");
            mainGrid.add(geenUitslagLabel, 0, 9);
        }

        borderPane.setCenter(mainGrid);

        return borderPane;
    }
}
