/*******************************************************************************
 * Copyright (c) 2011 Bruno Quoitin.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Bruno Quoitin - initial API and implementation
 ******************************************************************************/

package reso.examples.dv_routing;

import reso.common.Network;
import reso.common.Node;
import reso.ip.*;
import reso.scheduler.AbstractScheduler;
import reso.scheduler.Scheduler;
import reso.utilities.FIBDumper;
import reso.utilities.NetworkBuilder;

public class Problem {

    private static IPAddress getRouterID(IPLayer ip) {
        IPAddress routerID = null;
        for (IPInterfaceAdapter iface : ip.getInterfaces()) {
            IPAddress addr = iface.getAddress();
            if (routerID == null)
                routerID = addr;
            else if (routerID.compareTo(addr) < 0)
                routerID = addr;
        }
        return routerID;
    }

    /**
     * Configure the routing protocol on every router.
     * If routerDst is not null, configure a single router as destination.
     *
     * @param network
     * @param routerDst
     * @throws Exception
     */
    private static void setupRoutingProtocol(Network network, String routerDst) throws Exception {
        for (Node n : network.getNodes()) {
            if (!(n instanceof IPRouter))
                continue;
            IPRouter router = (IPRouter) n;
            boolean advertise = (routerDst == null) || (n.name.equals(routerDst));
            router.addApplication(new DVRoutingProtocol(router, advertise));
            router.start();
        }
    }

    public static void main(String[] args) {
        try {
            // change filename string according to project structure (where topology.txt is located)
            String filename = "project/src/main/java/reso/data/problem-graph.txt";
            AbstractScheduler scheduler = new Scheduler();
            Network network = NetworkBuilder.loadTopology(filename, scheduler);
            setupRoutingProtocol(network, "A");

            // Run simulation -- first convergence
            scheduler.run();

            // Display forwarding table for each node
            FIBDumper.dumpForAllRouters(network);

            // Change topology/nodes properties here ..

            /* setting down the link between node A and node Y */
            IPRouter routerY = (IPRouter) network.getNodeByName("Y");
            routerY.getIPLayer().getInterfaceByName("eth2").down();
            setupRoutingProtocol(network, "A");
//            scheduler.run();

            // Run simulation for 0.1 sec
            scheduler.runUntil(1);

            // Display again forwarding table for each node
            FIBDumper.dumpForAllRouters(network);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /* Display number of messages exchanged during convergence */
        System.out.println("Number of messages exchanged: " +DVRoutingProtocol.NUMBER_MESSAGE);
    }

}
