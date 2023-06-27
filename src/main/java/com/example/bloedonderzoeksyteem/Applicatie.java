package com.example.bloedonderzoeksyteem;

import com.example.bloedonderzoeksyteem.schermen.Patiëntscherm;
import com.example.bloedonderzoeksyteem.schermen.Startscherm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 800);
        stage.setResizable(false);
        stage.setTitle("");
        stage.setScene(scene);

        // Create and set the top bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        Text titleText = new Text(TITLE);
        titleText.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-fill: WHITE");
        topBar.getChildren().add(titleText);
        root.setTop(topBar);

        startscherm = new Startscherm(this);
        root.setCenter(startscherm.createStartscherm());

        // Create and set the bottom bar
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        bottomBar.setStyle("-fx-alignment: center");
        bottomText = new Text(AUTHOR);
        bottomText.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-fill: WHITE");
        bottomBar.getChildren().add(bottomText);
        root.setBottom(bottomBar);

        stage.show();
    }

    public void switchToPatiëntscherm() {
        patiëntscherm = new Patiëntscherm();
        root.setCenter(patiëntscherm.createPatiëntscherm());
    }

    public static void main(String[] args) {
        launch();
    }
}