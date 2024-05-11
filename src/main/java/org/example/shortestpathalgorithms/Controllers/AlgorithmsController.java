package org.example.shortestpathalgorithms.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.shortestpathalgorithms.Singeltons.GraphHolder;
import org.example.shortestpathalgorithms.Main;
import org.example.shortestpathalgorithms.Singeltons.ParentCostHolders;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlgorithmsController implements Initializable {
    private GraphHolder graphHolder = GraphHolder.getInstance();
    private ParentCostHolders parentCostHolders = ParentCostHolders.getInstance();
    private int graphSize = graphHolder.getGraph().getNumberOfNodes();
    private int startNode;
    private String methodName;
    private SpinnerValueFactory<Integer> startFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, graphSize - 1, 1);
    private SpinnerValueFactory<Integer> destinationFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, graphSize - 1, 1);
    @FXML
    private Label OogwayLabel;

    @FXML
    private ComboBox<String> chosenMethod;

    @FXML
    private Label destinationLabel;

    @FXML
    private Spinner<Integer> destinationNodeSpinner;

    @FXML
    private TextArea outPutField;

    @FXML
    private Label startLabel;

    @FXML
    private Spinner<Integer> startNodeSpinner;

    @FXML
    private Label methodsLabel;

    @FXML
    private Button solveButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    void backToChoices(MouseEvent event) throws IOException {
        switchWindow("Designs/menu-1.fxml", event);
    }

    @FXML
    void reset(MouseEvent event) throws IOException {
        switchWindow("Designs/answer.fxml", event);
    }

    @FXML
    void solve(MouseEvent event) {
        int mode = parentCostHolders.getMode();
        if (!(destinationNodeSpinner.getValue() != null
                && destinationNodeSpinner.getValue() >= 0
                && destinationNodeSpinner.getValue() < graphHolder.getGraph().getNumberOfNodes())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Start Node");
            alert.setContentText("Please enter a valid destination node");
            alert.showAndWait();
            return;
        }
        int destinationNode = destinationNodeSpinner.getValue();
        if (mode == 0) {
            outPutField.visibleProperty().setValue(true);
            if (methodName.equals("Dijkstra") || methodName.equals("Bellman-Ford")) {
                int[] parents = parentCostHolders.getParents1D();
                int[] distances = parentCostHolders.getCosts1D();
                int cost = distances[destinationNode];
                if (startNode == destinationNode) {
                    outPutField.setText("Shortest Path from " + startNode + " to " + destinationNode + " is " + cost + "\n");
                    outPutField.appendText("Path: " + startNode + "\n");
                    return;
                }
                if (cost == Integer.MAX_VALUE) {
                    outPutField.setText("Shortest Path from " + startNode + " to " + destinationNode + " is infinity\n");
                } else {
                    outPutField.setText("Shortest Path cost from " + startNode + " to " + destinationNode + " is: " + cost + "\n");
                }
                outPutField.appendText("Path: ");
                String path = graphHolder.getGraph().getShortestPath(destinationNode, parents, startNode);
                outPutField.appendText(path + "\n");
            } else {
                int[][] parents = parentCostHolders.getParents2D();
                int[][] distances = parentCostHolders.getCosts2D();
                int cost = distances[startNode][destinationNode];
                if (startNode == destinationNode) {
                    outPutField.setText("Shortest Path from " + startNode + " to " + destinationNode + " is " + cost + "\n");
                    outPutField.appendText("Path: " + startNode + "\n");
                    return;
                }
                if (cost == Integer.MAX_VALUE) {
                    outPutField.setText("Shortest Path from " + startNode + " to " + destinationNode + " is infinity\n");
                } else {
                    outPutField.setText("Shortest Path cost from " + startNode + " to " + destinationNode + " is: " + cost + "\n");
                }
                outPutField.appendText("Path: ");
                String path = graphHolder.getGraph().getShortestPath(destinationNode, parents[startNode], startNode);
                outPutField.appendText(path + "\n");
            }
        } else if (mode == 1) {
            if (!(startNodeSpinner.getValue() != null
                    && startNodeSpinner.getValue() >= 0
                    && startNodeSpinner.getValue() < graphHolder.getGraph().getNumberOfNodes())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Start Node");
                alert.setContentText("Please enter a valid start node");
                alert.showAndWait();
                return;
            }
            startNode = startNodeSpinner.getValue();
            outPutField.visibleProperty().setValue(true);
            int[][] parents = parentCostHolders.getParents2D();
            int[][] distances = parentCostHolders.getCosts2D();
            int cost = distances[startNode][destinationNode];
            if (startNode == destinationNode) {
                outPutField.setText("Shortest Path from " + startNode + " to " + destinationNode + " is " + cost + "\n");
                outPutField.appendText("Path: " + startNode + "\n");
                return;
            }
            if (cost == Integer.MAX_VALUE) {
                outPutField.setText("Shortest Path cost from " + startNode + " to " + destinationNode + " is infinity\n");
            } else {
                outPutField.setText("Shortest Path cost from " + startNode + " to " + destinationNode + " is: " + cost + "\n");
            }
            outPutField.appendText("Path: ");
            String path = graphHolder.getGraph().getShortestPath(destinationNode, parents[startNode], startNode);
            outPutField.appendText(path + "\n");
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

    @FXML
    void readStartNode(MouseEvent event) {
        methodName = chosenMethod.getValue();
        if (startNodeSpinner.getValue() != null
                && startNodeSpinner.getValue() >= 0
                && startNodeSpinner.getValue() < graphHolder.getGraph().getNumberOfNodes()
                && methodName != null) {
            startNode = startNodeSpinner.getValue();
            startNodeSpinner.setDisable(true);
            chosenMethod.setDisable(true);
            submitButton.visibleProperty().setValue(false);
            methodsLabel.visibleProperty().setValue(true);
            chosenMethod.visibleProperty().setValue(true);
            destinationLabel.visibleProperty().setValue(true);
            destinationNodeSpinner.visibleProperty().setValue(true);
            solveButton.visibleProperty().setValue(true);
            resetButton.visibleProperty().setValue(true);
            if (methodName.equals("Dijkstra")) {
                startNodeDijkstra();
                OogwayLabel.setText("Your Path Might Have Negative Cycle Dragon Warrior\n");
            } else if (methodName.equals("Bellman-Ford")) {
                if (!startNodeBellmanFord()) {
                    OogwayLabel.setText("Your Path Has Negative Cycle Dragon Warrior\n");
                } else {
                    OogwayLabel.setText("Your Path Has No Negative Cycle Dragon Warrior\n");
                }
            } else {
                if (FloydWarshall()) {
                    OogwayLabel.setText("Your Path Has No Negative Cycle Dragon Warrior\n");
                } else {
                    OogwayLabel.setText("Your Path Has Negative Cycle Dragon Warrior\n");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Start Node");
            alert.setContentText("Please enter a valid start node");
            alert.showAndWait();
        }
    }

    private void startNodeDijkstra() {
        int[] parents = new int[graphSize];
        int[] distances = new int[graphSize];
        graphHolder.getGraph().Dijkstra(startNode, distances, parents);
        parentCostHolders.setParents1D(parents);
        parentCostHolders.setCosts1D(distances);
    }

    private boolean startNodeBellmanFord() {
        int[] parents = new int[graphSize];
        int[] distances = new int[graphSize];
        boolean flag = graphHolder.getGraph().BellmanFord(startNode, distances, parents);
        parentCostHolders.setParents1D(parents);
        parentCostHolders.setCosts1D(distances);
        return flag;
    }

    private boolean allPathsBellmanFord() {
        int[][] parents = new int[graphSize][graphSize];
        int[][] distances = new int[graphSize][graphSize];
        boolean flag = true;
        for (int i = 0; i < graphSize; i++) {
            parents[i] = new int[graphSize];
            distances[i] = new int[graphSize];
            flag = flag && graphHolder.getGraph().BellmanFord(i, distances[i], parents[i]);
        }
        parentCostHolders.setParents2D(parents);
        parentCostHolders.setCosts2D(distances);
        return flag;
    }

    private void allPathsDijkstra() {
        int[][] parents = new int[graphSize][graphSize];
        int[][] distances = new int[graphSize][graphSize];
        for (int i = 0; i < graphSize; i++) {
            distances[i] = new int[graphSize];
            parents[i] = new int[graphSize];
            graphHolder.getGraph().Dijkstra(i, distances[i], parents[i]);
        }
        parentCostHolders.setParents2D(parents);
        parentCostHolders.setCosts2D(distances);
    }

    private boolean FloydWarshall() {
        int[][] parents = new int[graphSize][graphSize];
        int[][] distances = new int[graphSize][graphSize];
        for (int i = 0; i < graphSize; i++) {
            distances[i] = new int[graphSize];
            parents[i] = new int[graphSize];
        }
        boolean flag = graphHolder.getGraph().FloydWarshall(distances, parents);
        parentCostHolders.setParents2D(parents);
        parentCostHolders.setCosts2D(distances);
        return flag;
    }

    private boolean checkForNegativeCycleBellmanFord() {
        boolean flag;
        for (int i = 0; i < graphSize; i++) {
            flag = graphHolder.getGraph().BellmanFord(i, new int[graphSize], new int[graphSize]);
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int mode = parentCostHolders.getMode();
        startNodeSpinner.setValueFactory(startFactory);
        destinationNodeSpinner.setValueFactory(destinationFactory);
        outPutField.visibleProperty().setValue(false);
        if (mode == 0) {
            destinationLabel.visibleProperty().setValue(false);
            destinationNodeSpinner.visibleProperty().setValue(false);
            solveButton.visibleProperty().setValue(false);
            chosenMethod.getItems().add("Dijkstra");
            chosenMethod.getItems().add("Bellman-Ford");
            chosenMethod.getItems().add("Floyd-Warshall");
        } else if (mode == 1) {
            startNodeSpinner.visibleProperty().setValue(false);
            startLabel.visibleProperty().setValue(false);
            solveButton.visibleProperty().setValue(false);
            destinationLabel.visibleProperty().setValue(false);
            destinationNodeSpinner.visibleProperty().setValue(false);
            submitButton.visibleProperty().setValue(false);
            OogwayLabel.setText("You must choose a technique, and be patient with master Bellman-Ford");
            chosenMethod.getItems().add("Dijkstra");
            chosenMethod.getItems().add("Bellman-Ford");
            chosenMethod.getItems().add("Floyd-Warshall");
            chosenMethod.setOnAction(e -> {
                methodName = chosenMethod.getValue();
                if (methodName != null) {
                    solveButton.visibleProperty().setValue(true);
                    destinationLabel.visibleProperty().setValue(true);
                    destinationNodeSpinner.visibleProperty().setValue(true);
                    startNodeSpinner.visibleProperty().setValue(true);
                    startLabel.visibleProperty().setValue(true);
                    chosenMethod.setDisable(true);
                    resetButton.visibleProperty().setValue(true);
                    if (methodName.equals("Dijkstra")) {
                        allPathsDijkstra();
                        OogwayLabel.setText("Your Path Might Have Negative Cycle Dragon Warrior\n");
                    } else if (methodName.equals("Bellman-Ford")) {
                        if (!allPathsBellmanFord()) {
                            OogwayLabel.setText("Your Path Has Negative Cycle Dragon Warrior\n");
                        } else {
                            OogwayLabel.setText("Your Path Has No Negative Cycle Dragon Warrior\n");
                        }
                    } else {
                        if (!FloydWarshall()) {
                            OogwayLabel.setText("Your Path Has Negative Cycle Dragon Warrior\n");
                        } else {
                            OogwayLabel.setText("Your Path Has No Negative Cycle Dragon Warrior\n");
                        }
                    }
                }
            });
        } else {
            submitButton.visibleProperty().setValue(false);
            startLabel.visibleProperty().setValue(false);
            OogwayLabel.visibleProperty().setValue(true);
            startNodeSpinner.visibleProperty().setValue(false);
            destinationLabel.visibleProperty().setValue(false);
            destinationNodeSpinner.visibleProperty().setValue(false);
            solveButton.visibleProperty().setValue(false);
            chosenMethod.getItems().add("Bellman-Ford");
            chosenMethod.getItems().add("Floyd-Warshall");
            OogwayLabel.setText("You must choose a technique, and be patient with master Bellman-Ford");
            chosenMethod.setOnAction(e -> {
                methodName = chosenMethod.getValue();
                if (methodName != null) {
                    outPutField.visibleProperty().setValue(true);
                    if (methodName.equals("Bellman-Ford")) {
                        if (checkForNegativeCycleBellmanFord()) {
                            outPutField.setText("Your Path Has Negative Cycle Dragon Warrior\n Master:Bellman-Ford");
                        } else {
                            outPutField.setText("Your Path Has No Negative Cycle Dragon Warrior\n Master:Bellman-Ford");
                        }
                    } else {
                        if (!FloydWarshall()) {
                            outPutField.setText("Your Path Has Negative Cycle Dragon Warrior\n Master:Floyd-Warshall");
                        } else {
                            outPutField.setText("Your Path Has No Negative Cycle Dragon Warrior\n Master:Floyd-Warshall");
                        }
                    }
                }
            });
        }
    }
}
