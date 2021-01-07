package reso.examples.dv_routing.generator;

import java.util.*;

public class Generator {

    int numNodes, density;

    public Generator(int numNodes, int density) {
        this.numNodes = numNodes;
        this.density = density;
    }

    public void generate() {
        // output info about nodes and density
        System.out.println("\nNodes: " + numNodes + ", density: " + density + "\n");

        // create a list of nodes and assign to each a (unique), random position
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            Random rand = new Random();
            int x = rand.nextInt(100); //todo(maybe): allow for bound personalization
            int y = rand.nextInt(100); //todo(maybe): allow for bound personalization
            Node node = new Node(x, y, "R" + String.valueOf(i + 1)); //todo: verify that the tuple (x,y) is unique across the list
            nodes.add(node);
        }

        /* create a list of links between nodes
            fist: randomly connect 2 nodes (m0 for Barabási-Albert algorithm)
            second: connect remaining nodes
         */
        List<Link> links = new ArrayList<>();
        Link linkR1toR2 = new Link(nodes.get(0), nodes.get(1));
        linkR1toR2.linkNodes();
        linkR1toR2.isLinked();
        links.add(linkR1toR2);

        // applying preferential attachment (second)
        int totalLinks = 0; // total number of links

        for (Node n : nodes) {
            totalLinks += n.getLinkedTo().size();
        }
        totalLinks /= 2; // divide by 2 since the connection is stated twice; by each node

        if (numNodes > 2) {
            System.out.println("Total number of links: " + totalLinks);
            // set the probabilities of attachment of each node
            for (int i = 0; i < nodes.size(); i++) {
                int linksOfNode = nodes.get(i).getLinkedTo().size(); // a node's number of links
                System.out.println("linksofnode:" + linksOfNode);
                nodes.get(i).setProbability(linksOfNode / totalLinks); // set a node's probability of attaching to a new one
            }
            for (Node n : nodes) {
                System.out.println(n.getProbability());
            }

            // link nodes 3+ to the node with highest probability
            double maxProbability = 0;
            Node bestNode = null;
            for (Node n : nodes) {
                if (n.getProbability() > maxProbability) {
                    maxProbability = n.getProbability();
                    bestNode = n;
                }
            }
            System.out.println("maxProbability: " + maxProbability + " best node: ");
            bestNode.display();
        }

        displayGraph(nodes, links, false);
    }

    public void displayGraph(List<Node> nodes, List<Link> links, boolean verbose) {
        if (verbose) {
            // display nodes
            System.out.println("\nNodes(" + nodes.size() + "): ");
            for (Node n : nodes) {
                System.out.println(n.toString());
            }

            //display links
            System.out.println("\nLinks(" + links.size() + "): ");
            for (Link l : links) {
                System.out.println(l.toString());
            }

        } else {
            System.out.println();
            for (Node n : nodes) {
                n.display();
            }
            System.out.println();
            for (Link l : links) {
                l.display();
            }
        }
    }

    public void generateFile() {

    }
}
