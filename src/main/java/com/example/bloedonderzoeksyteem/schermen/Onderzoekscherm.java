package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Dokter;
import com.example.bloedonderzoeksyteem.models.Laborant;
import com.example.bloedonderzoeksyteem.models.Onderzoekuitslag;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

        GridPane leftGrid = new GridPane();
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

        leftGrid.add(titleOnderzoek, 0, 0, 3, 1);
        leftGrid.add(testIdLabel,0,1);
        leftGrid.add(testIdValue,1,1);
        leftGrid.add(testTypeLabel, 0, 2);
        leftGrid.add(testTypeValueLabel, 1, 2);
        leftGrid.add(tubeCountLabel, 0, 3);
        leftGrid.add(tubeCountValueLabel, 1, 3);
        leftGrid.add(testDateLabel, 0, 4);
        leftGrid.add(testDateValueLabel, 1, 4);
        leftGrid.add(doctorLabel, 0, 5);
        leftGrid.add(doctorValueLabel, 1, 5);
        leftGrid.add(patientLabel, 0, 6);
        leftGrid.add(patientValueLabel, 1, 6);

        GridPane rightGrid = new GridPane();
        rightGrid.setVgap(10);
        rightGrid.setHgap(10);
        rightGrid.setAlignment(Pos.CENTER_LEFT);

        Label titleUitslag = new Label("Uitslag");
        titleUitslag.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightGrid.add(titleUitslag, 0, 0, 3, 1);

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

            rightGrid.add(uitslagIdLabel, 0, 1);
            rightGrid.add(uitslagIdValue, 1, 1);
            rightGrid.add(resultLabel, 0, 2);
            rightGrid.add(resultValue, 1, 2);
            rightGrid.add(resultDateLabel, 0, 3);
            rightGrid.add(resultDateValue, 1, 3);
        } else {
            Label geenUitslagLabel = new Label("Geen uitslag bekend");
            String buttonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
            Button enterUitslagButton = new Button("Uitslag invoeren");
            enterUitslagButton.setStyle(buttonStyle);
            rightGrid.add(geenUitslagLabel, 0, 1);
            rightGrid.add(enterUitslagButton,1,2);

            enterUitslagButton.setOnAction(e -> {
                openToevoegenPopup(bloedonderzoek);
            });
        }

        borderPane.setLeft(leftGrid);
        borderPane.setRight(rightGrid);

        return borderPane;
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
            LocalDate testDatum = datumPicker.getValue();
            Laborant selectedLaborant = laborantComboBox.getValue(); // Change the data type to Laborant

            if (selectedLaborant != null) {
                Integer selectedLaborantId = selectedLaborant.getId(); // Get the ID of the selected laborant

                try {
                    // First, add the result to the database
                    db.addResult(bloedonderzoek.getId(), result, Date.valueOf(testDatum), selectedLaborantId);

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Show an error message if something goes wrong
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fout");
                    alert.setHeaderText("Fout bij toevoegen van uitslag");
                    alert.setContentText("Er is een fout opgetreden bij het toevoegen van de uitslag. Probeer het opnieuw.");
                    alert.showAndWait();
                }

                popupStage.close();
            } else {
                // Handle case where no laborant is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Waarschuwing");
                alert.setHeaderText("Geen laborant geselecteerd");
                alert.setContentText("Selecteer een laborant voordat u het onderzoek opslaat.");
                alert.showAndWait();
            }
        });

        gridPane.add(saveButton, 1, 4);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
