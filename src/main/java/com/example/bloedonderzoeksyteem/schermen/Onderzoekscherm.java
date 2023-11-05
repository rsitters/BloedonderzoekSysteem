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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

    //Methode die Onderzoekgegevensscherm creërt en terug geeft als BorderPane
    public BorderPane createOnderzoekgegevensscherm(Bloedonderzoek bloedonderzoek, Onderzoekuitslag onderzoekuitslag) {
        BorderPane borderPane = new BorderPane();

        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HBox topbar = createTopbar();
        GridPane onderzoekBox = createOnderzoeksBox(bloedonderzoek);
        GridPane uitslagBox = createUitslagbox(onderzoekuitslag);

        VBox contentBox = new VBox(topbar, createDivider(), onderzoekBox, createDivider(), uitslagBox);
        borderPane.setCenter(contentBox);

        return borderPane;
    }

    //Methode die scheidingslijn maakt
    private VBox createDivider() {
        Line divider = new Line();
        divider.setStroke(Color.BLACK);
        divider.setStrokeWidth(2);
        divider.setStartX(0);
        divider.setEndX(780);
        VBox dividerContainer = new VBox(divider);
        dividerContainer.setPadding(new Insets(0, 10, 0, 10));

        return dividerContainer;
    }

    //Methode die bovenste balk maakt met titel en terugknop
    private HBox createTopbar(){
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));

        Label titleLabel = new Label("Onderzoekgegevens");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button backButton = new Button("Terug");
        backButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox.setHgrow(backButton, Priority.ALWAYS);
        StackPane leftBox = new StackPane(titleLabel);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        topBar.getChildren().addAll(leftBox, backButton);

        //Zorgt ervoor dat terug genavigeerd wordt naar pagina specifieke patiënt
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicatie.openPatiëntPagina();
            }
        });

        return topBar;
    }

    //Methode die een grid met onderzoekgegevens maakt
    private GridPane createOnderzoeksBox(Bloedonderzoek bloedonderzoek){
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(10));
        leftGrid.setVgap(10);
        leftGrid.setHgap(10);
        leftGrid.setAlignment(Pos.CENTER_LEFT);

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

        leftGrid.add(titleOnderzoek, 0, 0, 6, 1);
        leftGrid.add(testIdLabel,0,1);
        leftGrid.add(testIdValue,1,1);
        leftGrid.add(testTypeLabel, 0, 2);
        leftGrid.add(testTypeValueLabel, 1, 2);
        leftGrid.add(tubeCountLabel, 0, 3);
        leftGrid.add(tubeCountValueLabel, 1, 3);
        leftGrid.add(testDateLabel, 2, 1);
        leftGrid.add(testDateValueLabel, 3, 1);
        leftGrid.add(doctorLabel, 2, 2);
        leftGrid.add(doctorValueLabel, 3, 2);
        leftGrid.add(patientLabel, 2, 3);
        leftGrid.add(patientValueLabel, 3, 3);

        return leftGrid;
    }

    //Methode die een grid met uitslaggegevens maakt
    private GridPane createUitslagbox(Onderzoekuitslag onderzoekuitslag){
        GridPane rightGrid = new GridPane();
        rightGrid.setPadding(new Insets(10));
        rightGrid.setVgap(10);
        rightGrid.setHgap(10);
        rightGrid.setAlignment(Pos.CENTER_LEFT);

        Label titleUitslag = new Label("Uitslag");
        titleUitslag.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightGrid.add(titleUitslag, 0, 0, 3, 1);

        //Checkt of uitslag al bekend is en vult op basis daarvan de grid
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

            Label laborantLabel = new Label("Laborant: ");
            laborantLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            Label laborantValueLabel = new Label(db.getLaborantName(onderzoekuitslag.getTechnicianId()));

            rightGrid.add(uitslagIdLabel, 0, 1);
            rightGrid.add(uitslagIdValue, 1, 1);
            rightGrid.add(resultLabel, 0, 2);
            rightGrid.add(resultValue, 1, 2);
            rightGrid.add(laborantLabel,2,1);
            rightGrid.add(laborantValueLabel,3,1);
            rightGrid.add(resultDateLabel, 2, 2);
            rightGrid.add(resultDateValue, 3, 2);
        } else {
            Label geenUitslagLabel = new Label("Geen uitslag bekend");
            rightGrid.add(geenUitslagLabel, 0, 1);
        }
        return rightGrid;
    }
}
