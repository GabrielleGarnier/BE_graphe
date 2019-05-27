package org.insa.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

	/**
	 * Create a new path that goes through the given list of nodes (in order),
	 * choosing the fastest route if multiple are available.
	 * 
	 * @param graph Graph containing the nodes in the list.
	 * @param nodes List of nodes to build the path.
	 * 
	 * @return A path that goes through the given list of nodes.
	 * 
	 * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
	 *         consecutive nodes in the list are not connected in the graph.
	 * 
	 */
	public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
			throws IllegalArgumentException {
		Path result;
		Iterator<Node> iter=nodes.iterator();
		if(nodes.size()==0) {
			result = new Path(graph);
		} else if (nodes.size()==1) {
			result = new Path(graph, iter.next());
		} else {
			List<Arc> arcs = new ArrayList<Arc>(); 
			List<Arc> aTester = new ArrayList<Arc>();

			Arc arc;  
			double vitesse=Double.MAX_VALUE;
			Node current = iter.next();
			while (iter.hasNext()) { 
				arc=null;
				vitesse=Double.MAX_VALUE;
				Node suivant = iter.next();
				aTester=current.getSuccessors();
				for (Arc courant : aTester) {
					if (courant.getDestination().equals(suivant)) {
						if (vitesse>courant.getMinimumTravelTime()) {
							arc=courant; 
							vitesse=courant.getMinimumTravelTime(); 
						}
					}
				}
				if (arc==null) {
					throw new IllegalArgumentException("pas bien"); 
				}
				arcs.add(arc); 
				current=suivant;
			} 
			result = new Path(graph, arcs);
		}
		return result;
	}

	/**
	 * Create a new path that goes through the given list of nodes (in order),
	 * choosing the shortest route if multiple are available.
	 * 
	 * @param graph Graph containing the nodes in the list.
	 * @param nodes List of nodes to build the path.
	 * 
	 * @return A path that goes through the given list of nodes.
	 * 
	 * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
	 *         consecutive nodes in the list are not connected in the graph.

	 */
	public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
			throws IllegalArgumentException {

		Path result;
		Iterator<Node> iter=nodes.iterator();
		if(nodes.size()==0) {
			result = new Path(graph);
		} else if (nodes.size()==1) {
			result = new Path(graph, iter.next());
		} else {
			List<Arc> arcs = new ArrayList<Arc>(); 
			List<Arc> aTester = new ArrayList<Arc>(); 
			Arc arc;  
			float taille=Float.MAX_VALUE;
			Node current = iter.next();
			while (iter.hasNext()) { 
				arc=null;
				taille=Float.MAX_VALUE;
				Node suivant = iter.next();
				aTester=current.getSuccessors();
				for (Arc courant : aTester) {
					if (courant.getDestination().equals(suivant)) {
						if (taille>courant.getLength()) {
							arc=courant; 
							taille=courant.getLength(); 
						}
					}
				}
				if (arc==null) {
					throw new IllegalArgumentException("pas bien"); 
				}
				arcs.add(arc); 
				current=suivant;
			}
			result = new Path(graph,arcs);
		}
		return result;
	}

	/**
	 * Concatenate the given paths.
	 * 
	 * @param paths Array of paths to concatenate.
	 * 
	 * @return Concatenated path.
	 * 
	 * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
	 *         map do not match, or the end of a path is not the beginning of the
	 *         next).
	 */
	public static Path concatenate(Path... paths) throws IllegalArgumentException {
		if (paths.length == 0) {
			throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
		}
		final String mapId = paths[0].getGraph().getMapId();
		for (int i = 1; i < paths.length; ++i) {
			if (!paths[i].getGraph().getMapId().equals(mapId)) {
				throw new IllegalArgumentException(
						"Cannot concatenate paths from different graphs.");
			}
		}
		ArrayList<Arc> arcs = new ArrayList<>();
		for (Path path: paths) {
			arcs.addAll(path.getArcs());
		}
		Path path = new Path(paths[0].getGraph(), arcs);
		if (!path.isValid()) {
			throw new IllegalArgumentException(
					"Cannot concatenate paths that do not form a single path.");
		}
		return path;
	}

	// Graph containing this path.
	private final Graph graph;

	// Origin of the path
	private final Node origin;

	// List of arcs in this path.
	private final List<Arc> arcs;

	/**
	 * Create an empty path corresponding to the given graph.
	 * 
	 * @param graph Graph containing the path.
	 */
	public Path(Graph graph) {
		this.graph = graph;
		this.origin = null;
		this.arcs = new ArrayList<>();
	}

	/**
	 * Create a new path containing a single node.
	 * 
	 * @param graph Graph containing the path.
	 * @param node Single node of the path.
	 */
	public Path(Graph graph, Node node) {
		this.graph = graph;
		this.origin = node;
		this.arcs = new ArrayList<>();
	}

	/**
	 * Create a new path with the given list of arcs.
	 * 
	 * @param graph Graph containing the path.
	 * @param arcs Arcs to construct the path.
	 */
	public Path(Graph graph, List<Arc> arcs) {
		this.graph = graph;
		this.arcs = arcs;
		this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
	}

	/**
	 * @return Graph containing the path.
	 */
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * @return First node of the path.
	 */
	public Node getOrigin() {
		return this.origin;
	}

	/**
	 * @return Last node of the path.
	 */
	public Node getDestination() {
		return this.arcs.get(this.arcs.size() - 1).getDestination();
	}

	/**
	 * @return List of arcs in the path.
	 */
	public List<Arc> getArcs() {
		return Collections.unmodifiableList(this.arcs);
	}

	/**
	 * Check if this path is empty (it does not contain any node).
	 * 
	 * @return true if this path is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.origin == null;
	}

	/**
	 * Get the number of <b>nodes</b> in this path.
	 * 
	 * @return Number of nodes in this path.
	 */
	public int size() {
		return isEmpty() ? 0 : 1 + this.arcs.size();
	}

	/**
	 * Check if this path is valid.
	 * 
	 * A path is valid if any of the following is true:
	 * <ul>
	 * <li>it is empty;</li>
	 * <li>it contains a single node (without arcs);</li>
	 * <li>the first arc has for origin the origin of the path and, for two
	 * consecutive arcs, the destination of the first one is the origin of the
	 * second one.</li>
	 * </ul>
	 * 
	 * @return true if the path is valid, false otherwise.
	 * 
	 *  Need to be implemented.
	 */
	public boolean isValid() {
		boolean valid = false;
		Node noot;
		valid = isEmpty();
		if (!valid) {
			valid = (size()==1);
		}
		if (!valid) {
			valid = true;
			noot = getOrigin();
			Iterator<Arc> iter = this.arcs.iterator();
			while(valid==true & iter.hasNext()) {
				Arc current = iter.next();
				if (noot!=current.getOrigin()) {valid=false;}
				noot=current.getDestination();
			}
			if (noot!=this.getDestination()) {valid = false;}
		}
		return valid;
	}

	/**
	 * Compute the length of this path (in meters).
	 * 
	 * @return Total length of the path (in meters).
	 * 
	 */
	public float getLength() {
		float length=0 ;
		Iterator<Arc> iter =this.arcs.iterator(); 
		while (iter.hasNext()) {
			Arc current = iter.next(); 
			length=length+current.getLength(); 
		}
		return length;
	}

	/**
	 * Compute the time required to travel this path if moving at the given speed.
	 * 
	 * @param speed Speed to compute the travel time.
	 * 
	 * @return Time (in seconds) required to travel this path at the given speed (in
	 *         kilometers-per-hour).
	 * 
	 *  Need to be implemented.
	 */
	public double getTravelTime(double speed) {
		return this.getLength() * 3600.0 / (speed * 1000.0);
	}

	/**
	 * Compute the time to travel this path if moving at the maximum allowed speed
	 * on every arc.
	 * 
	 * @return Minimum travel time to travel this path (in seconds).
	 *
	 */
	public double getMinimumTravelTime() {
		float travelTime=0 ;
		Iterator<Arc> iter =this.arcs.iterator(); 
		while (iter.hasNext()) {
			Arc current = iter.next(); 
			travelTime=travelTime+(float)current.getMinimumTravelTime(); 
		}
		return travelTime;
	}


	public boolean equals( Object o) {
		boolean eq = true;
		if(o instanceof Path) {
			Path p = (Path) o;
			if (!this.getOrigin().equals(p.getOrigin())) {
				eq=false;
			}
			if (!this.getDestination().equals(p.getDestination())) {
				eq=false;
			}
			if (!(this.getLength()==p.getLength())) {
				eq=false;
			}
		}
		return eq;
	}

}
