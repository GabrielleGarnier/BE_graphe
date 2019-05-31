package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

public class Test  {
	String graphPath = "/C:/Users/Jeanne/Desktop/insa/3A/graphes/BE_graphe/";
	String[] graphNames = { "france" };
	public void Testlol() throws Exception {
		List<Graph> graphs = new ArrayList<>();
		GraphReader reader;
		for(String path : graphNames) {
			reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(graphPath + path + ".mapgr"))));
			graphs.add(reader.read());
		}
		System.out.println(graphs.get(0).size());
	}
	public void main (String args[]) {
		try {
			Testlol(); 
		} catch (Exception e) {}
	}
}
