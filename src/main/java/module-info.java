module org.example.shortestpathalgorithms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;

    opens org.example.shortestpathalgorithms to javafx.fxml;
    exports org.example.shortestpathalgorithms;
    exports org.example.shortestpathalgorithms.Controllers;
    opens org.example.shortestpathalgorithms.Controllers to javafx.fxml;
    exports org.example.shortestpathalgorithms.Singeltons;
    opens org.example.shortestpathalgorithms.Singeltons to javafx.fxml;
    exports org.example.shortestpathalgorithms.Logic;
    opens org.example.shortestpathalgorithms.Logic to javafx.fxml;
}