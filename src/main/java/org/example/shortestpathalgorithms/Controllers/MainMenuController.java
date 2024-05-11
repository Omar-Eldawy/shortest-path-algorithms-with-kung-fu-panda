package org.example.shortestpathalgorithms.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.shortestpathalgorithms.Main;
import org.example.shortestpathalgorithms.Singeltons.ParentCostHolders;

import java.io.IOException;

public class MainMenuController {

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

    @FXML
    void backToStartWindow(MouseEvent event) throws IOException {
        switchWindow("Designs/start-window.fxml", event);
    }

    @FXML
    void checkNegativeCycles(MouseEvent event) throws IOException {
        ParentCostHolders.getInstance().setMode(2);
        switchWindow("Designs/answer.fxml", event);
    }

    @FXML
    void findAllPaths(MouseEvent event) throws IOException {
        ParentCostHolders.getInstance().setMode(1);
        switchWindow("Designs/answer.fxml", event);
    }

    @FXML
    void getSourceNode(MouseEvent event) throws IOException {
        ParentCostHolders.getInstance().setMode(0);
        switchWindow("Designs/answer.fxml", event);
    }
}
