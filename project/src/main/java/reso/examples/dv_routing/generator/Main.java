package reso.examples.dv_routing.generator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Generator generator = inputValues();
        generator.generate();

    }

    public static Generator inputValues(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of nodes: "); //todo: enforce minimum = 2 (mO>=2)
        int numNodes = sc.nextInt();
        System.out.print("Density: ");
        int density = sc.nextInt(); //todo: check if density>1 && density>=numNodes
        return new Generator(numNodes, density);
    }

}
