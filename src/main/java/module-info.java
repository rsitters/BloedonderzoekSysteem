module com.example.bloedonderzoeksyteem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bloedonderzoeksyteem to javafx.fxml;
    exports com.example.bloedonderzoeksyteem;
}