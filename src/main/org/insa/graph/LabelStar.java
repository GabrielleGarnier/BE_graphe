package org.insa.graph;

public class LabelStar extends Label implements Comparable<Label> {

	private Node destination;
	private double costDestination;
	public LabelStar(Node node, Node destination) {
		super(node);
		this.destination= destination;
		this.costDestination=Point.distance(this.getSommetCourant().getPoint(),this.destination.getPoint());
	}

	public float getTotalCost() {
		return (float) (this.getCost()+this.costDestination);
	}
}

//JeanneBertrand
//Magnolia_31