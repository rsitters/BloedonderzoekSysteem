module com.example.bloedonderzoeksyteem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bloedonderzoeksyteem to javafx.fxml;
    exports com.example.bloedonderzoeksyteem;
}