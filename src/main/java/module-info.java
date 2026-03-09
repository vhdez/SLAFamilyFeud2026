module com.example.slafamilyfeud2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.slafamilyfeud2026 to javafx.fxml;
    exports com.example.slafamilyfeud2026;
}