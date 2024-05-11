package org.example.shortestpathalgorithms.Logic;

public interface GraphFunctionality {
    public int getNumberOfNodes();

    public String getShortestPath(int destination, int[] parents, int start);

    public void Dijkstra(int source, int[] costs, int[] parents);

    public boolean BellmanFord(int source, int[] costs, int[] parents);

    public boolean FloydWarshall(int[][] costs, int[][] predecessors);
}
