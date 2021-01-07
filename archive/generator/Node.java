package reso.examples.dv_routing.generator;

import java.util.ArrayList;
import java.util.List;

public class Node {

    String name;
    int x, y;
    List<Node> linkedTo = new ArrayList<>(); // list of nodes to which this one's linked to
    double probability = 0; // probability of attaching a new node to this one

    public Node(int x, int y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the node's x and y
     */
    public String display() {
        return (x + " " + y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public List<Node> getLinkedTo() {
        return linkedTo;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", probability=" + probability +
                '}';
    }
}
