package com.example.bloedonderzoeksyteem.schermen;

import com.example.bloedonderzoeksyteem.models.Patiënt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Patiëntgegevensscherm {

    public VBox createPatiëntgegevensscherm(Patiënt patiënt) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        // Titel
        Label titleLabel = new Label("Persoonlijke gegevens");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Voeg de benodigde informatie van de patiënt toe aan het scherm
        Label nameLabel = new Label("Naam: " + patiënt.getFirstName() + " " + patiënt.getLastName());
        Label birthDateLabel = new Label("Geboortedatum: " + patiënt.getBirthDate());
        Label bsnLabel = new Label("BSN-nummer: " + patiënt.getBsnNumber());
        Label addressLabel = new Label("Adres: " + patiënt.getAddress());
        Label phoneNumberLabel = new Label("Telefoonnummer: " + patiënt.getPhoneNumber());
        Label emailLabel = new Label("E-mailadres: " + patiënt.getEmailAddress());

        vBox.getChildren().addAll(titleLabel, nameLabel, birthDateLabel, bsnLabel, addressLabel, phoneNumberLabel, emailLabel);

        return vBox;
    }
}
