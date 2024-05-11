package org.example.shortestpathalgorithms.Singeltons;

import org.example.shortestpathalgorithms.Logic.Graph;

public class GraphHolder {
    private static GraphHolder instance = new GraphHolder();
    private Graph graph;

    private GraphHolder() {
    }

    public static GraphHolder getInstance() {
        return instance;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
