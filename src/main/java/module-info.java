module com.example.bloedonderzoeksyteem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.bloedonderzoeksyteem to javafx.fxml;
    opens com.example.bloedonderzoeksyteem.models to javafx.base;

    exports com.example.bloedonderzoeksyteem;
}
