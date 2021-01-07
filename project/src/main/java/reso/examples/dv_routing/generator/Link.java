package reso.examples.dv_routing.generator;

public class Link {

    Node n1, n2;
    boolean areConnected;

    public Link(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public void linkNodes() {
        if (!(n1.getLinkedTo().contains(n2))) {
            n1.getLinkedTo().add(n2);
            n2.getLinkedTo().add(n1);
            areConnected = true;
        }
    }

    public void isLinked() {
        if (n1.getLinkedTo().contains(n2))
            System.out.println(n1.getName() + " is connected to " + n2.getName());
        else
            System.out.println(n1.getName() + " is not connected to " + n2.getName());
    }

    public void display() {
        if (n1.getLinkedTo().contains(n2)) {
            System.out.println(n1.getName().substring(1) + " " + n2.getName().substring(1));
        }
    }

    @Override
    public String toString() {
        return "Link{" +
                "n1=" + n1 +
                ", n2=" + n2 +
                ", areConnected=" + areConnected +
                '}';
    }
}
