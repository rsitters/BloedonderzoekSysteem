package com.example.bloedonderzoeksyteem;

import com.example.bloedonderzoeksyteem.models.Bloedonderzoek;
import com.example.bloedonderzoeksyteem.models.Onderzoekuitslag;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class Applicatie extends Application {

    private static final String TITLE = "Bloedonderzoek";
    private static final String AUTHOR = "\u00A9robinsitters";
    private static final String BACKGROUND_COLOR = "#3253d8";

    private BorderPane root;
    private Text bottomText;
    private Startscherm startscherm;
    private Database db;
    private Patiëntenlijst patiëntenlijst;
    private Onderzoekenlijst onderzoekenlijst;
    private Patiëntscherm patiëntscherm;
    private Onderzoekscherm onderzoekscherm;
    private Onderzoekuitslagscherm onderzoekuitslagscherm;
    private Patiënt currentPatiënt;

    @Override
    public void start(Stage stage) throws IOException {
        //Configureert gebruikersinterface bij opstarten
        setupUI(stage);
        //Configureert de databaseverbinding bij opstarten
        setupDatabase();
        //Toont hoofdscherm van de applicatie bij opstarten
        stage.show();
    }

    //Configuratie van de gebruikersinterface
    private void setupUI(Stage stage) {
        root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.setScene(scene);

        //Configureert de bovenste rand
        setupTopBar();
        //Configureert de onderste rand
        setupBottomBar();

        startscherm = new Startscherm(this);
        //Configureert startscherm als centrale deel applicatie
        root.setCenter(startscherm.createStartscherm());
    }

    //Configureert de bovenste rand
    private void setupTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setSpacing(10);

        Text titleText = new Text(TITLE);
        titleText.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-fill: WHITE");

        Button returnButton = new Button("HOME");
        returnButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: BLACK; -fx-font-weight: bold; -fx-padding: 8px 16px;");
        HBox.setHgrow(returnButton, Priority.ALWAYS);

        StackPane leftBox = new StackPane(titleText);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        topBar.getChildren().addAll(leftBox, returnButton);
        root.setTop(topBar);

        //Zorgt ervoor dat HOME knop functioneel is en terug navigeert naar startscherm
        returnButton.setOnAction(event -> switchToStartscherm());
    }

    //Configureert de onderste rand
    private void setupBottomBar() {
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setBackground(new Background(new BackgroundFill(Color.web(BACKGROUND_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));
        bottomBar.setStyle("-fx-alignment: center");

        bottomText = new Text(AUTHOR);
        bottomText.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-fill: WHITE");

        bottomBar.getChildren().add(bottomText);
        root.setBottom(bottomBar);
    }

    //Configureert de databaseverbinding
    private void setupDatabase() {
        try {
            db = new Database();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Schakelt naar het scherm met lijst van patiënten
    public void switchToPatiëntlijst() {
        patiëntenlijst = new Patiëntenlijst();
        root.setCenter(patiëntenlijst.createPatiëntlijst());

        TableView<Patiënt> patiëntTableView = patiëntenlijst.getPatiëntTableView();
        //Zorgt ervoor dat pagina specifieke patiënt opent bij dubbelklikken
        patiëntTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Patiënt selectedPatiënt = patiëntTableView.getSelectionModel().getSelectedItem();
                if (selectedPatiënt != null) {
                    openPatiëntPagina(selectedPatiënt);
                }
            }
        });
    }

    //Schakelt naar scherm met lijst bloedonderzoeken
    public void switchToOnderzoeklijst() {
        onderzoekenlijst = new Onderzoekenlijst();
        root.setCenter(onderzoekenlijst.createBloedonderzoekscherm());
        TableView<Bloedonderzoek> bloedonderzoekTableView = onderzoekenlijst.getBloedonderzoekTableView();

        //Zorgt ervoor dat pagina specifiek onderzoek opent bij dubbelklikken
        bloedonderzoekTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Bloedonderzoek selectedBloedonderzoek = bloedonderzoekTableView.getSelectionModel().getSelectedItem();
                if (selectedBloedonderzoek != null) {
                    try {
                        ResultSet onderzoekuitslagResultSet = db.getBloodTestResult(selectedBloedonderzoek.getId());

                        Onderzoekuitslag onderzoekuitslag = null;

                        if (onderzoekuitslagResultSet.next()) {
                            onderzoekuitslag = new Onderzoekuitslag(
                                    onderzoekuitslagResultSet.getInt("id"),
                                    onderzoekuitslagResultSet.getInt("test_id"),
                                    onderzoekuitslagResultSet.getInt("technician_id"),
                                    onderzoekuitslagResultSet.getString("result"),
                                    onderzoekuitslagResultSet.getDate("result_date").toLocalDate()
                            );
                        }

                        openBloedonderzoekuitslagPagina(selectedBloedonderzoek, onderzoekuitslag);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //Schakelt naar scherm specifiek bloedonderzoek
    public void openBloedonderzoekPagina(Bloedonderzoek bloedonderzoek, Onderzoekuitslag onderzoekuitslag) {
        onderzoekscherm = new Onderzoekscherm(this);
        root.setCenter(onderzoekscherm.createOnderzoekgegevensscherm(bloedonderzoek, onderzoekuitslag));
    }

    //Schakelt naar scherm specifiek onderzoek met uitslag
    public void openBloedonderzoekuitslagPagina(Bloedonderzoek bloedonderzoek, Onderzoekuitslag onderzoekuitslag) {
        onderzoekuitslagscherm = new Onderzoekuitslagscherm(this);
        root.setCenter(onderzoekuitslagscherm.createOnderzoekgegevensscherm(bloedonderzoek, onderzoekuitslag));
    }

    //Schakelt naar scherm specifieke patiënt
    public void openPatiëntPagina(Patiënt patiënt) {
        currentPatiënt = patiënt;
        patiëntscherm = new Patiëntscherm(this);

        root.setCenter(patiëntscherm.createPatiëntgegevensscherm(patiënt));

        TableView<Bloedonderzoek> bloedonderzoekTableView = patiëntscherm.getBloedonderzoekTableView();

        //Zorgt ervoor dat scherm specifiek onderzoek opent bij dubbelklikken
        bloedonderzoekTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Bloedonderzoek selectedBloedonderzoek = bloedonderzoekTableView.getSelectionModel().getSelectedItem();
                if (selectedBloedonderzoek != null) {
                    try {
                        ResultSet onderzoekuitslagResultSet = db.getBloodTestResult(selectedBloedonderzoek.getId());

                        Onderzoekuitslag onderzoekuitslag = null;

                        if (onderzoekuitslagResultSet.next()) {
                            onderzoekuitslag = new Onderzoekuitslag(
                                    onderzoekuitslagResultSet.getInt("id"),
                                    onderzoekuitslagResultSet.getInt("test_id"),
                                    onderzoekuitslagResultSet.getInt("technician_id"),
                                    onderzoekuitslagResultSet.getString("result"),
                                    onderzoekuitslagResultSet.getDate("result_date").toLocalDate()
                            );
                        }

                        openBloedonderzoekPagina(selectedBloedonderzoek, onderzoekuitslag);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //Schakelt naar scherm van een specifieke patiënt
    public void openPatiëntPagina() {
        openPatiëntPagina(currentPatiënt);
    }
    //Schakelt terug naar startscherm
    public void switchToStartscherm() {
        root.setCenter(startscherm.createStartscherm());
    }

    public static void main(String[] args) {
        launch();
    }
}
