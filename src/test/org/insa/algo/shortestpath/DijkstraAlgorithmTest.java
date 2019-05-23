package org.insa.algo.shortestpath;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {

	List<ArcInspector> filters = ArcInspectorFactory.getAllFilters();

	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, a2i, b2a, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2a, e2g, g2h, h2i;


    // Some paths...
    private static Path PathOne,PathTwo;

    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[10];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 10, speed20, null);
        a2i= Node.linkNodes(nodes[0], nodes[8], 140, speed10, null);
        b2a = Node.linkNodes(nodes[1], nodes[0], 10, speed10, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2a = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);
        e2g= Node.linkNodes(nodes[4], nodes[6], 5, speed10, null);
        g2h= Node.linkNodes(nodes[6], nodes[7], 5, speed10, null);
        h2i= Node.linkNodes(nodes[7], nodes[8], 5, speed10, null);
        

        graph = new Graph("ID", "", Arrays.asList(nodes), null);


  
        PathOne = new Path(graph, Arrays.asList(new Arc[] {b2c}));
        PathTwo = new Path(graph, Arrays.asList(new Arc[] {a2e,e2g,g2h,h2i}));

    }

	
	ShortestPathData dataZero =new ShortestPathData(graph, nodes[0], nodes[0],filters.get(0));
	ShortestPathData dataImpossible =new ShortestPathData(graph, nodes[0], nodes[5],filters.get(0));
	ShortestPathData dataOne = new ShortestPathData(graph, nodes[1], nodes[2], filters.get(0));
	ShortestPathData dataTwo = new ShortestPathData(graph, nodes[0], nodes[8], filters.get(0));
	@Test
	
	
	public void test() {
		DijkstraAlgorithm dij = new DijkstraAlgorithm(dataImpossible);
		ShortestPathSolution sol=dij.doRun(); 
		assertEquals( sol.getStatus(), Status.INFEASIBLE);
		
		dij = new DijkstraAlgorithm(dataZero);
		sol=dij.doRun(); 
		assertEquals( sol.getStatus(), Status.INFEASIBLE);
		
		dij = new DijkstraAlgorithm(dataOne);
		sol=dij.doRun(); 
		assertEquals( sol.getPath(),PathOne);
		
		dij = new DijkstraAlgorithm(dataTwo);
		sol=dij.doRun(); 
		assertEquals( sol.getPath(),PathTwo);
		

		
	}

}
