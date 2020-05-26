package com.ZombieGame.main;


import java.util.ArrayList;

class Node {
    public boolean isObstacle;
    public boolean isVisited = false;
    public float gCost;
    public float hCost;
    public float fCost;
    int x;
    int y;
    Node parent;
    ArrayList<Node> neighbours;

    public Node(int x, int y, Node parent, boolean isObstacle) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.isObstacle = isObstacle;
    }
}

public class AStarPathFinding {

}
