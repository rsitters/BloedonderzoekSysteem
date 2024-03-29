package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.Applicatie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Startscherm {

    private Applicatie applicatie;

    public Startscherm(Applicatie applicatie) {
        this.applicatie = applicatie;
    }

    //Methode die startscherm maakt en terug geeft als BorderPane
    public BorderPane createStartscherm() {
        BorderPane borderPane = new BorderPane();

        Button patiëntButton = new Button("Patiënten");
        patiëntButton.setStyle("-fx-font-size: 20px; -fx-background-color: #3253d8; -fx-text-fill: white;");
        patiëntButton.setPrefSize(200, 100);

        Button bloodResearchButton = new Button("Bloedonderzoeken");
        bloodResearchButton.setStyle("-fx-font-size: 20px; -fx-background-color: #3253d8; -fx-text-fill: white;");
        bloodResearchButton.setPrefSize(200, 100);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(patiëntButton, bloodResearchButton);
        buttonBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(buttonBox);

        //Actie op te schakelen naar patiëntenlijst bij klikken op knop
        patiëntButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicatie.switchToPatiëntlijst();
            }
        });

        //Actie om te schakelen naar bloedonderzoekenlijst bij klikken op knop
        bloodResearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicatie.switchToOnderzoeklijst();
            }
        });

        return borderPane;
    }
}