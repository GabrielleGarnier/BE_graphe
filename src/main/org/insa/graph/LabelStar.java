package org.insa.graph;

import org.insa.algo.AbstractInputData;
import org.insa.algo.shortestpath.ShortestPathData;

public class LabelStar extends Label implements Comparable<Label> {

	private double costDestination;
	
	public LabelStar(Node node, ShortestPathData data) {
		super(node);
		
		Double dist = Point.distance(this.getSommetCourant().getPoint(),data.getDestination().getPoint());
		
		if(data.getMode()==AbstractInputData.Mode.TIME ) {
			int maxSpeed = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed());
			this.costDestination = dist/((double) maxSpeed/3.6);
		} else {
		this.costDestination= dist;
		}
	}

	public double getTotalCost() {
		return  (this.getCost() + this.costDestination);
	}
}

