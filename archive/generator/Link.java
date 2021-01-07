package reso.examples.dv_routing.generator;

public class Link {

    Node n1, n2;

    public Link(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    /**
     * links the two nodes n1 and n2
     */
    public void linkNodes() {
        if (!(n1.getLinkedTo().contains(n2))) {
            n1.getLinkedTo().add(n2);
            n2.getLinkedTo().add(n1);
        }
    }

    /**
     * verifies if nodes n1 and n2 are linked
     */
    public void isLinked() {
        if (n1.getLinkedTo().contains(n2))
            System.out.println(n1.getName() + " is connected to " + n2.getName());
        else
            System.out.println(n1.getName() + " is not connected to " + n2.getName());
    }

    /**
     * @return displays nodes' names if they are linked
     */
    public String display() {
        String output = "";
        if (n1.getLinkedTo().contains(n2)) {
            output = (n1.getName().substring(1) + " " + n2.getName().substring(1));
        }
        return output;
    }

    @Override
    public String toString() {
        return "Link{" +
                "n1=" + n1 +
                ", n2=" + n2 +
                '}';
    }
}
