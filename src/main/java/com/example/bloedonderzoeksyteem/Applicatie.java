package com.example.bloedonderzoeksyteem;

import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Patiënt;
import com.example.bloedonderzoeksyteem.schermen.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Applicatie extends Application {

    private static final String TITLE = "Bloedonderzoek";
    private static final String AUTHOR = "\u00A9robinsitters";
    private static final String BACKGROUND_COLOR = "#3253d8";

    private BorderPane root;
    private Text bottomText;
    private Startscherm startscherm;
    private Patiëntenlijst patiëntenlijst;
    private Onderzoekenlijst onderzoekenlijst;
    private Patiëntscherm patiëntscherm;
    private Onderzoekscherm onderzoekscherm;

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setTitle("");
        stage.setScene(scene);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setSpacing(10);
        Text titleText = new Text(TITLE);
        Button returnButton = new Button("HOME");
        titleText.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-fill: WHITE");
        returnButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: BLACK;  -fx-font-weight: bold; -fx-padding: 8px 16px;");
        HBox.setHgrow(returnButton, Priority.ALWAYS);
        StackPane leftBox = new StackPane(titleText);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        topBar.getChildren().addAll(leftBox, returnButton);
        root.setTop(topBar);

        startscherm = new Startscherm(this);
        root.setCenter(startscherm.createStartscherm());

        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        bottomBar.setStyle("-fx-alignment: center");
        bottomText = new Text(AUTHOR);
        bottomText.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-fill: WHITE");
        bottomBar.getChildren().add(bottomText);
        root.setBottom(bottomBar);

        returnButton.setOnAction(event -> switchToStartscherm());

        stage.show();
    }

    public void switchToPatiëntlijst() {
        patiëntenlijst = new Patiëntenlijst();
        root.setCenter(patiëntenlijst.createPatiëntlijst());

        TableView<Patiënt> patiëntTableView = patiëntenlijst.getPatiëntTableView();
        patiëntTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Patiënt selectedPatiënt = patiëntTableView.getSelectionModel().getSelectedItem();
                if (selectedPatiënt != null) {
                    openPatiëntPagina(selectedPatiënt);
                }
            }
        });
    }

    public void switchToOnderzoeklijst() {
        onderzoekenlijst = new Onderzoekenlijst();
        root.setCenter(onderzoekenlijst.createBloedonderzoekscherm());
        TableView<Bloedonderzoek> bloedonderzoekTableView = onderzoekenlijst.getBloedonderzoekTableView();
        bloedonderzoekTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Bloedonderzoek selectedBloedonderzoek = bloedonderzoekTableView.getSelectionModel().getSelectedItem();
                if (selectedBloedonderzoek != null) {
                    openBloedonderzoekPagina(selectedBloedonderzoek);
                }
            }
        });

    }

    public void openPatiëntPagina(Patiënt patiënt) {
        patiëntscherm = new Patiëntscherm(this);
        root.setCenter(patiëntscherm.createPatiëntgegevensscherm(patiënt));
    }

    public void openBloedonderzoekPagina(Bloedonderzoek bloedonderzoek){
        onderzoekscherm = new Onderzoekscherm(this);
        root.setCenter(onderzoekscherm.createOnderzoekgegevensscherm(bloedonderzoek));
    }

    public void switchToStartscherm() {
        root.setCenter(startscherm.createStartscherm());
    }

    public static void main(String[] args) {
        launch();
    }
}

