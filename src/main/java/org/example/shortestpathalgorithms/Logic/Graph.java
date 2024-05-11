package org.example.shortestpathalgorithms.Logic;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Graph implements GraphFunctionality {
    private HashMap<Integer, ArrayList<int[]>> graph;
    private int numberOfNodes;
    private int DijkstraSpace = 0;
    private int BellmanFordSpace = 0;
    private int FloydWarshallSpace = 0;
    private int specificNode;
    private long time;

    public Graph(String filePath) throws FileNotFoundException {
        this.graph = new HashMap<>();
        this.numberOfNodes = 0;
        readFile(filePath);
    }

    public Graph(File file) {
        this.graph = new HashMap<>();
        this.numberOfNodes = 0;
        try {
            Scanner scanner = new Scanner(file);
            this.numberOfNodes = scanner.nextInt();
            int edges = scanner.nextInt();
            for (int i = 0; i < this.numberOfNodes; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < edges; i++) {
                scanner.nextLine();
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                graph.get(source).add(new int[]{destination, weight});
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    @Override
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }

    @Override
    public String getShortestPath(int destination, int[] parents, int start) {
        StringBuilder path = new StringBuilder();
        path.insert(0, destination);
        destination = parents[destination];
        if (destination == -2) {
            return "There is no path";
        }
        while (parents[destination] != -1 && destination != start) {
            path.insert(0, destination + " -> ");
            destination = parents[destination];
            if (destination == -2) {
                return "There is no path";
            }
        }
        path.insert(0, destination + " -> ");
        return path.toString().trim();
    }

    @Override
    public void Dijkstra(int source, int[] costs, int[] parents) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.numberOfNodes; i++) {
            costs[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < this.numberOfNodes; i++) {
            parents[i] = -2;
        }
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        HashSet<Integer> visited = new HashSet<>();
        minHeap.add(new int[]{source, 0});
        costs[source] = 0;
        parents[source] = -1;

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int node = current[0];
            int weight = current[1];

            if (weight > costs[node]) {
                continue;
            }

            if (node == this.specificNode) {
                long end = System.currentTimeMillis();
                setTime(end - start);
            }

            for (int[] edges : graph.get(node)) {
                int neighbour = edges[0];
                int newWeight = weight + edges[1];
                if (newWeight < costs[neighbour] && !visited.contains(neighbour)) {
                    costs[neighbour] = newWeight;
                    parents[neighbour] = node;
                    minHeap.add(new int[]{neighbour, newWeight});
                }
            }
            visited.add(node);
        }
        this.DijkstraSpace = costs.length * 4;
    }

    @Override
    public boolean BellmanFord(int source, int[] costs, int[] parents) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.numberOfNodes; i++) {
            costs[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < this.numberOfNodes; i++) {
            parents[i] = -2;
        }
        costs[source] = 0;
        parents[source] = -1;

        for (int i = 0; i < this.numberOfNodes - 1; i++) {
            for (int node : graph.keySet()) {
                for (int[] edges : graph.get(node)) {
                    int neighbour = edges[0];
                    int weight = edges[1];
                    if (costs[node] != Integer.MAX_VALUE && costs[node] + weight < costs[neighbour]) {
                        costs[neighbour] = costs[node] + weight;
                        parents[neighbour] = node;
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        setTime(end - start);
        this.BellmanFordSpace = costs.length * 3;

        for (int node : graph.keySet()) {
            for (int[] edges : graph.get(node)) {
                int neighbour = edges[0];
                int weight = edges[1];
                if (costs[node] != Integer.MAX_VALUE && costs[node] + weight < costs[neighbour]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean FloydWarshall(int[][] costs, int[][] predecessors) {
        long start = System.currentTimeMillis();
        int n = costs.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costs[i][j] = Integer.MAX_VALUE;
                predecessors[i][j] = -2;
            }
        }
        for (int i = 0; i < n; i++) {
            costs[i][i] = 0;
            predecessors[i][i] = -1;
        }

        for (int node : graph.keySet()) {
            for (int[] edges : graph.get(node)) {
                int neighbour = edges[0];
                int weight = edges[1];
                costs[node][neighbour] = weight;
                costs[node][node] = 0;
                predecessors[node][neighbour] = node;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (costs[i][k] == Integer.MAX_VALUE || costs[k][j] == Integer.MAX_VALUE) continue;

                    int sum = costs[i][k] + costs[k][j];
                    if (sum < costs[i][j]) {
                        costs[i][j] = sum;
                        predecessors[i][j] = predecessors[k][j];
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        setTime(end - start);

        for (int i = 0; i < n; i++) {
            if (costs[i][i] < 0) {
                return false;
            }
        }
        this.FloydWarshallSpace = (costs.length * costs.length) * 2;
        return true;
    }

    private void readFile(String filePath) throws FileNotFoundException {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            this.numberOfNodes = scanner.nextInt();
            int edges = scanner.nextInt();
            for (int i = 0; i < this.numberOfNodes; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < edges; i++) {
                scanner.nextLine();
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                graph.get(source).add(new int[]{destination, weight});
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            throw new FileNotFoundException();
        }
    }

    public int getBellmanFordSpace() {
        return BellmanFordSpace;
    }

    public int getFloydWarshallSpace() {
        return FloydWarshallSpace;
    }

    public int getDijkstraSpace() {
        return DijkstraSpace;
    }

    public long getTime() {
        return this.time;
    }

    public void setSpecificNode(int specificNode) {
        this.specificNode = specificNode;
    }

    private void setTime(long time) {
        this.time = time;
    }
}
