package reso.examples.dv_routing.generator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Generator generator = inputValues();
        generator.generate();
    }

    /**
     *
     * @return a new Generator object with the number of nodes and density input by the user
     */
    public static Generator inputValues(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of nodes: ");
        int numNodes = sc.nextInt();
        System.out.print("Density: ");
        int density = sc.nextInt();
        return new Generator(numNodes, density);
    }
}
