package com.example.bloedonderzoeksyteem;

import com.example.bloedonderzoeksyteem.models.Patiënt;
import com.example.bloedonderzoeksyteem.schermen.Patiëntgegevensscherm;
import com.example.bloedonderzoeksyteem.schermen.Patiëntscherm;
import com.example.bloedonderzoeksyteem.schermen.Startscherm;
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
    private Patiëntscherm patiëntscherm;
    private Patiëntgegevensscherm patiëntgegevensscherm;

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

    public void switchToPatiëntscherm() {
        patiëntscherm = new Patiëntscherm();
        root.setCenter(patiëntscherm.createPatiëntscherm());

        TableView<Patiënt> patiëntTableView = patiëntscherm.getPatiëntTableView();
        patiëntTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Patiënt selectedPatiënt = patiëntTableView.getSelectionModel().getSelectedItem();
                if (selectedPatiënt != null) {
                    openPatiëntPagina(selectedPatiënt);
                }
            }
        });
    }

    public void openPatiëntPagina(Patiënt patiënt) {
        patiëntgegevensscherm = new Patiëntgegevensscherm(this);
        root.setCenter(patiëntgegevensscherm.createPatiëntgegevensscherm(patiënt));
    }

    public void switchToStartscherm() {
        root.setCenter(startscherm.createStartscherm());
    }

    public static void main(String[] args) {
        launch();
    }
}

