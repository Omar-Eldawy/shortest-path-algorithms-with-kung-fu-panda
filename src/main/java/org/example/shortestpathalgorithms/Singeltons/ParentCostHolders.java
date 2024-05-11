package org.example.shortestpathalgorithms.Singeltons;

public class ParentCostHolders {
    private static ParentCostHolders instance = new ParentCostHolders();
    private int[][] parents2D;
    private int[][] costs2D;
    private int[] parents1D;
    private int[] costs1D;
    private int mode;

    private ParentCostHolders() {
    }

    public static ParentCostHolders getInstance() {
        return instance;
    }

    public int[][] getParents2D() {
        return parents2D;
    }

    public void setParents2D(int[][] parents) {
        this.parents2D = parents;
    }

    public int[][] getCosts2D() {
        return costs2D;
    }

    public void setCosts2D(int[][] costs) {
        this.costs2D = costs;
    }

    public int[] getParents1D() {
        return parents1D;
    }

    public void setParents1D(int[] parents) {
        this.parents1D = parents;
    }

    public int[] getCosts1D() {
        return costs1D;
    }

    public void setCosts1D(int[] costs) {
        this.costs1D = costs;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
