module org.example.javafxminefield {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafxminefield to javafx.fxml;
    exports org.example.javafxminefield;
}