package reso.examples.dv_routing.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Generator {

    int numNodes, density;

    public Generator(int numNodes, int density) {
        this.numNodes = numNodes;
        this.density = density;
    }

    /**
     * generates the nodes and links between these nodes
     * using variables numNodes and density
     */
    public void generate() {
        // output info about nodes and density
        System.out.println("\nNodes: " + numNodes + ", density: " + density);

        // create a list of nodes and assign to each a (unique), random position
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            Random rand = new Random();
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            Node node = new Node(x, y, "R" + String.valueOf(i + 1));
            nodes.add(node);
        }

        /* create a list of links between nodes
        fist: randomly connect 2 nodes (m0 for Barabási-Albert algorithm)
        second: connect remaining nodes */
        List<Link> links = new ArrayList<>();
        Node node1 = nodes.get(0);
        Node node2 = nodes.get(1);
        Link linkR1toR2 = new Link(node1, node2);
        linkR1toR2.linkNodes();
        links.add(linkR1toR2);
        node1.setProbability(1);
        node2.setProbability(1);

        // applying preferential attachment (second)
        int nodeDegreeSum = 0;
        for (Node n : nodes)
            nodeDegreeSum = n.getLinkedTo().size();
        int graphDensity = (nodeDegreeSum / links.size());
        if (numNodes > 2) {
            for (int i = 0; i < numNodes - 2; i++) {
                if (graphDensity <= density) {
                    // getting the best node for the new link
                    Node bestNode = chooseOnWeight(nodes);

                    Node newNode = nodes.get(i + 2);
                    Link newLink = new Link(bestNode, newNode); // create a link between the best node and the next+2 node
                    newLink.linkNodes();
                    links.add(newLink);
                    bestNode.setProbability(bestNode.getProbability() + 1);
                    newNode.setProbability(newNode.getProbability() + 1);
                }
            }
            System.out.println("Total number of links: " + links.size());
            String output = displayGraph(nodes, links, false);
            System.out.println(output);

            // creating a file and storing the output within
            try {
                FileWriter myWriter = new FileWriter("graph.txt");
                myWriter.write(output);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * @param nodes nodes generated by generate() function
     * @return probability (weighted) of returning the node with the best probability
     */
    public Node chooseOnWeight(List<Node> nodes) {
        double completeWeight = 0.0;
        for (Node n : nodes)
            completeWeight += n.getProbability();
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (Node n : nodes) {
            countWeight += n.getProbability();
            if (countWeight >= r)
                return n;
        }
        throw new RuntimeException("runtime exception");
    }

    /**
     * @param nodes   the nodes' generated by generate() function
     * @param links   the links' generated by generate() function
     * @param verbose displays more or less text
     * @return a string displaying the output
     */
    public String displayGraph(List<Node> nodes, List<Link> links, boolean verbose) {
        String output = "";
        if (verbose) {
            output += ("\nNodes(" + nodes.size() + "): ");
            for (Node n : nodes) {
                output += (n.toString()) + "\n";
            }
            output += ("\nLinks(" + links.size() + "): ");
            for (Link l : links) {
                output += (l.toString()) + "\n";
            }
        } else {
            for (Node n : nodes) {
                output += n.display() + "\n";
            }
            output += "\n";
            for (Link l : links) {
                output += l.display() + "\n";
            }
        }
        return output;
    }
}
