module org.example.school_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires static lombok;

    opens org.example.school_management to javafx.fxml;
    exports org.example.school_management;
    opens org.example.school_management.controllers to javafx.fxml;
    exports org.example.school_management.controllers to javafx.fxml;
    opens org.example.school_management.controllers.Dashboards to javafx.fxml;
    opens org.example.school_management.controllers.Etudiant to javafx.fxml;
    exports org.example.school_management.controllers.Home to javafx.fxml;
    exports org.example.school_management.controllers.Inscription to javafx.fxml;
    exports org.example.school_management.controllers.Module to javafx.fxml;
    exports org.example.school_management.controllers.Prof to javafx.fxml;
}