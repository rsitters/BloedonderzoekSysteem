package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Database;
import com.example.bloedonderzoeksyteem.Applicatie;
import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Dokter;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Patiëntscherm {
    private Database db;
    private Applicatie applicatie;
    private Label nameValueLabel;
    private Label birthDateValueLabel;
    private Label bsnValueLabel;
    private Label addressValueLabel;
    private Label phoneNumberValueLabel;
    private Label emailValueLabel;
    private TableView<Bloedonderzoek> bloedonderzoekTableView;
    private VBox onderzoekenBox;
    private DateTimeFormatter formatter;

    public Patiëntscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public BorderPane createPatiëntgegevensscherm(Patiënt patiënt) {
        BorderPane borderPane = new BorderPane();

        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HBox topbar = createTopbar();
        GridPane gegevensBox = createGegevensBox(patiënt);
        HBox knoppenBox = createKnoppenBox(patiënt);
        VBox onderzoekenBox = createOnderzoekenBox(patiënt);

        VBox contentBox = new VBox(topbar, createDivider(), gegevensBox,  knoppenBox, createDivider(), onderzoekenBox);
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

        Label titleLabel = new Label("Patiëntgegevens");
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
                applicatie.switchToPatiëntlijst();
            }
        });

        return topBar;
    }
    private GridPane createGegevensBox(Patiënt patiënt) {
        GridPane gegevensGrid = new GridPane();
        gegevensGrid.setPadding(new Insets(10));
        gegevensGrid.setVgap(10);
        gegevensGrid.setHgap(10);
        gegevensGrid.setAlignment(Pos.CENTER_LEFT);

        Label titlePersonal = new Label("Persoonlijke informatie");
        titlePersonal.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label nameLabel = new Label("Naam: ");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.nameValueLabel = new Label(patiënt.getFirstName() + " " + patiënt.getLastName());

        Label birthDateLabel = new Label("Geboortedatum: ");
        birthDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        String formattedBirthDate = patiënt.getBirthDate().format(formatter);
        this.birthDateValueLabel = new Label(formattedBirthDate);

        Label bsnLabel = new Label("BSN-nummer: ");
        bsnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.bsnValueLabel = new Label(patiënt.getBsnNumber());

        Label addressLabel = new Label("Adres: ");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.addressValueLabel = new Label(patiënt.getAddress());

        Label phoneNumberLabel = new Label("Telefoonnummer: ");
        phoneNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.phoneNumberValueLabel = new Label(patiënt.getPhoneNumber());

        Label emailLabel = new Label("E-mailadres: ");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.emailValueLabel = new Label(patiënt.getEmailAddress());

        gegevensGrid.add(titlePersonal, 0, 0, 4, 1);
        gegevensGrid.add(nameLabel, 0, 1);
        gegevensGrid.add(nameValueLabel, 1, 1);
        gegevensGrid.add(birthDateLabel, 0, 2);
        gegevensGrid.add(birthDateValueLabel, 1, 2);
        gegevensGrid.add(bsnLabel, 0, 3);
        gegevensGrid.add(bsnValueLabel, 1, 3);
        gegevensGrid.add(addressLabel, 2, 1);
        gegevensGrid.add(addressValueLabel, 3, 1);
        gegevensGrid.add(phoneNumberLabel, 2, 2);
        gegevensGrid.add(phoneNumberValueLabel, 3, 2);
        gegevensGrid.add(emailLabel, 2, 3);
        gegevensGrid.add(emailValueLabel, 3, 3);

        return gegevensGrid;
    }

    private HBox createKnoppenBox(Patiënt patiënt) {
        HBox knoppenBox = new HBox();
        knoppenBox.setPadding(new Insets(0,10,10,10));
        knoppenBox.setSpacing(10);
        knoppenBox.setAlignment(Pos.CENTER_RIGHT);

        Button wijzigenButton = new Button("Wijzigen");
        wijzigenButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Button verwijderenButton = new Button("Verwijderen");
        verwijderenButton.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        wijzigenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWijzigingsPopup(patiënt);
            }
        });

        verwijderenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    db.deletePatient(patiënt.getId());

                    applicatie.switchToPatiëntlijst();
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

        return knoppenBox;
    }

    private VBox createOnderzoekenBox(Patiënt patiënt) {
        this.onderzoekenBox = new VBox();
        onderzoekenBox.setPadding(new Insets(10));
        onderzoekenBox.setSpacing(10);
        onderzoekenBox.setAlignment(Pos.CENTER_LEFT);

        Label titleResearch = new Label("Onderzoeken");
        titleResearch.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        bloedonderzoekTableView = createOnderzoekTableView(patiënt.getId());

        String addButtonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button addResearchButton = new Button("Onderzoek toevoegen");
        addResearchButton.setStyle(addButtonStyle);

        addResearchButton.setOnAction(e -> {
            openToevoegenPopup(patiënt.getId());
        });

        HBox buttonContainer = new HBox(addResearchButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        onderzoekenBox.getChildren().addAll(titleResearch, bloedonderzoekTableView, buttonContainer);
        return onderzoekenBox;
    }

    private void updateOnderzoekenBox(int patientId) {
        ObservableList<Bloedonderzoek> newItems = FXCollections.observableArrayList();

        try {
            ResultSet rs = db.getBloodTestsForPatient(patientId);
            while (rs.next()) {
                Bloedonderzoek bloedonderzoek = new Bloedonderzoek(rs);
                newItems.add(bloedonderzoek);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        bloedonderzoekTableView.getItems().setAll(newItems);
    }

    public TableView<Bloedonderzoek> createOnderzoekTableView(int patientId) {
        TableView<Bloedonderzoek> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Bloedonderzoek, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bloedonderzoek, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("testType"));

        TableColumn<Bloedonderzoek, LocalDate> dateColumn = new TableColumn<>("Datum");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        dateColumn.setCellFactory(column -> new TableCell<Bloedonderzoek, LocalDate>() {
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

        TableColumn<Bloedonderzoek, Integer> tubeColumn = new TableColumn<>("Aantal buisjes");
        tubeColumn.setCellValueFactory(new PropertyValueFactory<>("tubeCount"));

        TableColumn<Bloedonderzoek, String> doctorColumn = new TableColumn<>("Dokter");
        doctorColumn.setCellValueFactory(cellData -> {
            Integer doctorId = cellData.getValue().getDoctorId();
            String doctorName = db.getDoctorName(doctorId);
            return new SimpleStringProperty(doctorName);
        });


        tableView.getColumns().addAll(idColumn, typeColumn, dateColumn, tubeColumn, doctorColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            ResultSet rs = db.getBloodTestsForPatient(patientId);
            while (rs.next()) {
                Bloedonderzoek bloedonderzoek = new Bloedonderzoek(rs);
                tableView.getItems().add(bloedonderzoek);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableView;
    }
    public TableView<Bloedonderzoek> getBloedonderzoekTableView() {
        return bloedonderzoekTableView;
    }

    public void openWijzigingsPopup(Patiënt patiënt) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Wijzig patiëntgegevens");
        popupStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        String labelStyle = "-fx-font-weight: bold;";

        Label firstNameLabel = new Label("Voornaam:");
        firstNameLabel.setStyle(labelStyle);
        TextField firstNameField = new TextField(patiënt.getFirstName());
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);

        Label lastNameLabel = new Label("Achternaam:");
        lastNameLabel.setStyle(labelStyle);
        TextField lastNameField = new TextField(patiënt.getLastName());
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);

        Label birthDateLabel = new Label("Geboortedatum:");
        birthDateLabel.setStyle(labelStyle);
        DatePicker birthDatePicker = new DatePicker(patiënt.getBirthDate());
        birthDatePicker.setEditable(false);
        gridPane.add(birthDateLabel, 0, 2);
        gridPane.add(birthDatePicker, 1, 2);

        Label bsnLabel = new Label("BSN:");
        bsnLabel.setStyle(labelStyle);
        TextField bsnField = new TextField(patiënt.getBsnNumber());
        gridPane.add(bsnLabel, 0, 3);
        gridPane.add(bsnField, 1, 3);

        Label addressLabel = new Label("Adres:");
        addressLabel.setStyle(labelStyle);
        TextField addressField = new TextField(patiënt.getAddress());
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);

        Label emailLabel = new Label("E-mail:");
        emailLabel.setStyle(labelStyle);
        TextField emailField = new TextField(patiënt.getEmailAddress());
        gridPane.add(emailLabel, 0, 5);
        gridPane.add(emailField, 1, 5);

        Label phoneLabel = new Label("Telefoonnummer:");
        phoneLabel.setStyle(labelStyle);
        TextField phoneField = new TextField(patiënt.getPhoneNumber());
        gridPane.add(phoneLabel, 0, 6);
        gridPane.add(phoneField, 1, 6);

        String saveButtonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle(saveButtonStyle);
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        gridPane.add(saveButton, 1, 7);

        String cancelButtonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle(cancelButtonStyle);
        gridPane.add(cancelButton, 0, 7);

        saveButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String bsn = bsnField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || birthDate == null ||
                    bsn.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Waarschuwing");
                alert.setHeaderText("Controleer alle velden");
                alert.setContentText("Vul alle velden in voordat u de patiënt wijzigt.");
                alert.showAndWait();
                return;
            }

            patiënt.setFirstName(firstName);
            patiënt.setLastName(lastName);
            patiënt.setBirthDate(birthDate);
            patiënt.setBsnNumber(bsn);
            patiënt.setAddress(address);
            patiënt.setPhoneNumber(phone);
            patiënt.setEmailAddress(email);

            try {
                db.updatePatient(patiënt.getId(), firstName, lastName, Date.valueOf(birthDate), bsn, address, email, phone);
                updateUIWithPatientData(patiënt);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fout");
                alert.setHeaderText("Fout bij het wijzigen van patiëntgegevens");
                alert.setContentText("Er is een fout opgetreden bij het wijzigen van de patiëntgegevens. Probeer het opnieuw.");
                alert.showAndWait();
            }

            popupStage.close();
        });

        cancelButton.setOnAction(e -> {
            popupStage.close();
        });

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    private void updateUIWithPatientData(Patiënt patiënt) {
        this.nameValueLabel.setText(patiënt.getFirstName() + " " + patiënt.getLastName());
        this.birthDateValueLabel.setText(patiënt.getBirthDate().format(formatter));
        this.bsnValueLabel.setText(patiënt.getBsnNumber());
        this.addressValueLabel.setText(patiënt.getAddress());
        this.phoneNumberValueLabel.setText(patiënt.getPhoneNumber());
        this.emailValueLabel.setText(patiënt.getEmailAddress());
    }
    public void openToevoegenPopup(int patientId) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Onderzoek Toevoegen");
        popupStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        String labelStyle = "-fx-font-weight: bold;";

        Label typeLabel = new Label("Type test:");
        typeLabel.setStyle(labelStyle);
        TextField typeTextField = new TextField();
        gridPane.add(typeLabel, 0, 0);
        gridPane.add(typeTextField, 1, 0);

        Label buisjesLabel = new Label("Aantal buisjes:");
        buisjesLabel.setStyle(labelStyle);
        TextField buisjesTextField = new TextField();
        gridPane.add(buisjesLabel, 0, 1);
        gridPane.add(buisjesTextField, 1, 1);

        Label datumLabel = new Label("Testdatum:");
        datumLabel.setStyle(labelStyle);
        DatePicker datumPicker = new DatePicker();
        gridPane.add(datumLabel, 0, 2);
        gridPane.add(datumPicker, 1, 2);

        Label doctorLabel = new Label("Dokter:");
        doctorLabel.setStyle(labelStyle);
        ComboBox<Dokter> doctorComboBox = new ComboBox<>();
        try {
            ResultSet doctorsResultSet = db.getAllDoctors();
            List<Dokter> doctorsList = new ArrayList<>();
            while (doctorsResultSet.next()) {
                int id = doctorsResultSet.getInt("id");
                String firstName = doctorsResultSet.getString("firstName");
                String lastName = doctorsResultSet.getString("lastName");
                Dokter dokter = new Dokter(id, firstName, lastName);
                doctorsList.add(dokter);
            }
            doctorComboBox.setItems(FXCollections.observableArrayList(doctorsList));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        gridPane.add(doctorLabel, 0, 3);
        gridPane.add(doctorComboBox, 1, 3);

        Button saveButton = new Button("Opslaan");
        saveButton.setOnAction(event -> {
            String selectedType = typeTextField.getText();
            String buisjesText = buisjesTextField.getText();
            LocalDate testDatum = datumPicker.getValue();
            Dokter selectedDoctor = doctorComboBox.getValue();

            if (selectedType.isEmpty() || buisjesText.isEmpty() || testDatum == null || selectedDoctor == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Waarschuwing");
                alert.setHeaderText("Controleer alle velden");
                alert.setContentText("Vul alle velden in voordat u het onderzoek toevoegt.");
                alert.showAndWait();
                return;
            }

            Integer buisjes = Integer.parseInt(buisjesText);
            Integer selectedDoctorId = selectedDoctor.getId();

            try {
                db.addResearch(patientId, selectedType, buisjes, Date.valueOf(testDatum), selectedDoctorId);

                updateOnderzoekenBox(patientId);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fout");
                alert.setHeaderText("Fout bij toevoegen van onderzoek");
                alert.setContentText("Er is een fout opgetreden bij het toevoegen van het onderzoek. Probeer het opnieuw.");
                alert.showAndWait();
            }

            popupStage.close();
        });
        String saveButtonStyle = "-fx-font-weight: bold; -fx-border-color: black; -fx-background-color: white; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        saveButton.setStyle(saveButtonStyle);
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        gridPane.add(saveButton, 1, 4);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}

