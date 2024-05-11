package org.example.shortestpathalgorithms.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.shortestpathalgorithms.Logic.Graph;
import org.example.shortestpathalgorithms.Singeltons.GraphHolder;
import org.example.shortestpathalgorithms.Main;

import java.io.File;
import java.io.IOException;

public class StartController {

    @FXML
    void readFile(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Omar\\Downloads"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                GraphHolder.getInstance().setGraph(new Graph(file));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid File");
                alert.setContentText("Please Choose a Valid File");
                alert.showAndWait();
                return;
            }
            switchWindow("Designs/menu-1.fxml", event);
        }
    }

    @FXML
    void switchWindow(String fxml, MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Shortest Path Algorithms");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
