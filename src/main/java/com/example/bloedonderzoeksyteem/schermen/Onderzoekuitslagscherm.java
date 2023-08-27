package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Onderzoekuitslagscherm {
    private Database db;
    private Applicatie applicatie;
    private DateTimeFormatter formatter;

    public Onderzoekuitslagscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public BorderPane createOnderzoekgegevensscherm(Bloedonderzoek bloedonderzoek, Onderzoekuitslag onderzoekuitslag) {
        BorderPane borderPane = new BorderPane();
//        borderPane.setPadding(new Insets(10));

        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HBox topbar = createTopbar();
        GridPane onderzoekBox = createOnderzoeksBox(bloedonderzoek);

        VBox contentBox = new VBox(topbar, createDivider(),onderzoekBox);
        borderPane.setCenter(contentBox);

        return borderPane;
    }
    private VBox createDivider() {
        Line divider = new Line();
        divider.setStroke(Color.BLACK);
        divider.setStrokeWidth(2);
        divider.setStartX(0); // Verander de Y-coördinaat van het beginpunt van de lijn
        divider.setEndX(780);  // Verander de Y-coördinaat van het eindpunt van de lijn
        VBox dividerContainer = new VBox(divider);
        dividerContainer.setPadding(new Insets(0, 10, 0, 10)); // Voeg padding toe aan de boven- en onderkant

        return dividerContainer;
    }

    private HBox createTopbar(){
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));

        Label titleLabel = new Label("Uitslag toevoegen");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button backButton = new Button("Terug");
        backButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox.setHgrow(backButton, Priority.ALWAYS);
        StackPane leftBox = new StackPane(titleLabel);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        topBar.getChildren().addAll(leftBox, backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicatie.switchToOnderzoeklijst();
            }
        });

        return topBar;
    }
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

        String buttonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button enterUitslagButton = new Button("Uitslag invoeren");
        enterUitslagButton.setStyle(buttonStyle);

        enterUitslagButton.setOnAction(e -> {
            openToevoegenPopup(bloedonderzoek);
        });

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
        leftGrid.add(enterUitslagButton,3,4);

        return leftGrid;
    }
    public void openToevoegenPopup(Bloedonderzoek bloedonderzoek) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Uitslag Toevoegen");
        popupStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label idLabel = new Label("Test ID:");
        Label idValue = new Label(bloedonderzoek.getId().toString());
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idValue, 1, 0);

        Label UitslagLabel = new Label("Uitslag:");
        TextField uitslagTextField = new TextField();
        gridPane.add(UitslagLabel, 0, 1);
        gridPane.add(uitslagTextField, 1, 1);

        Label datumLabel = new Label("Uitslag datum:");
        DatePicker datumPicker = new DatePicker();
        gridPane.add(datumLabel, 0, 2);
        gridPane.add(datumPicker, 1, 2);

        Label laborantLabel = new Label("Laborant:");
        ComboBox<Laborant> laborantComboBox = new ComboBox<>();
        try {
            ResultSet laborantResultSet = db.getAllLaborants();
            List<Laborant> laborantsList = new ArrayList<>();
            while (laborantResultSet.next()) {
                int id = laborantResultSet.getInt("id");
                String firstName = laborantResultSet.getString("firstName");
                String lastName = laborantResultSet.getString("lastName");
                Laborant laborant = new Laborant(id, firstName, lastName);
                laborantsList.add(laborant);
            }
            laborantComboBox.setItems(FXCollections.observableArrayList(laborantsList));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        gridPane.add(laborantLabel, 0, 3);
        gridPane.add(laborantComboBox, 1, 3);

        Button saveButton = new Button("Opslaan");
        saveButton.setOnAction(event -> {
            String result = uitslagTextField.getText();
            LocalDate resultDatum = datumPicker.getValue();
            Laborant selectedLaborant = laborantComboBox.getValue();

            if (result.isEmpty() || resultDatum == null || selectedLaborant == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Waarschuwing");
                alert.setHeaderText("Controleer alle velden");
                alert.setContentText("Vul alle velden in voordat u de uitslag toevoegt.");
                alert.showAndWait();
                return;
            }

            Integer selectedLaborantId = selectedLaborant.getId();

            try {
                db.addResult(bloedonderzoek.getId(), result, Date.valueOf(resultDatum), selectedLaborantId);

                // Call switchToOnderzoeklijst() method here
                applicatie.switchToOnderzoeklijst();

                popupStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fout");
                alert.setHeaderText("Fout bij toevoegen van uitslag");
                alert.setContentText("Er is een fout opgetreden bij het toevoegen van de uitslag. Probeer het opnieuw.");
                alert.showAndWait();
            }
        });

        gridPane.add(saveButton, 1, 4);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
