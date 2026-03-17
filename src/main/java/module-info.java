module com.example.slafamilyfeud2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;


    opens com.example.slafamilyfeud2026 to javafx.fxml;
    exports com.example.slafamilyfeud2026;
}