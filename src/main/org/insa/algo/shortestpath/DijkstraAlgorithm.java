package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Label;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	
	protected ShortestPathSolution createSolution(Label[] labels, ShortestPathData data, int iter) {
		ShortestPathSolution solution = null;
		// Destination has no predecessor, the solution is infeasible...
		if (labels[data.getDestination().getId()].getPere()==null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		else {

			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());

			System.out.println("nb d'iterations de l'algorithme:"+iter);

			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = labels[data.getDestination().getId()].getPere();
			while (arc != null) {
				arcs.add(arc);
				arc = labels[arc.getOrigin().getId()].getPere();
			}
			System.out.println("nb arcs plus court chemin : "+arcs.size());
			// Reverse the path...
			Collections.reverse(arcs);
			System.out.println("1er arc plus court chemin : "+arcs.get(0).getOrigin().getId()+ " to "+arcs.get(0).getDestination().getId());
			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));

		}
		return solution;
	}
	
	
	protected Label[] Init(ShortestPathData data, BinaryHeap<Label> tas, int nbNodes) {
		Label[] labels=new Label[nbNodes];

		int i;
		for(i=0;i<nbNodes;i++) {
			labels[i]=new Label(data.getGraph().get(i));
		}

		int idorigin=data.getOrigin().getId(); 
		labels[idorigin].setCost(0); 
		tas.insert(labels[idorigin]); 
		notifyOriginProcessed(data.getOrigin());
		//System.out.println("taille tas début : "+tas.size());
		return labels;
	}
	
	protected void findPath(Label courant, Arc currentSuc,BinaryHeap<Label> tas, Label[] labels) {
		//System.out.println("arc = "+ currentSuc);
		int idcurrentSuc=currentSuc.getDestination().getId();
		if(!labels[idcurrentSuc].isMarque()) {
			notifyNodeReached(currentSuc.getDestination());
			if (labels[idcurrentSuc].getTotalCost()>(courant.getTotalCost()+currentSuc.getLength())) {
				labels[idcurrentSuc].setCost(courant.getTotalCost()+currentSuc.getLength());
				tas.insert(labels[idcurrentSuc]);
				//System.out.println("taille tas insertion : "+tas.size());
				labels[idcurrentSuc].setPere(currentSuc);
			}
		}
	}
	
	@Override
	protected ShortestPathSolution doRun() {
		
		ShortestPathData data = getInputData();
		BinaryHeap<Label> tas=new BinaryHeap<Label>();
		int nbNodes=data.getGraph().size();
		Label[] labels = Init(data, tas, nbNodes);

		boolean tousMarque = false;
		Label courant;
		List<Arc> succesors = new ArrayList<Arc>();
		int iter=0;
		while(!tousMarque && !tas.isEmpty()) {
			iter++;
			tousMarque=true;
			int l=0;
			while(l<nbNodes && tousMarque) {
				tousMarque=labels[l].isMarque();
				l++;
			}
			if (!tousMarque) {

				courant=tas.deleteMin();
				courant.setMarque(true);
				if (courant.getSommetCourant()==data.getDestination()) {
					tousMarque=true;
				} else {
					notifyNodeMarked(courant.getSommetCourant());
					System.out.println(courant.getCost());
					//System.out.println("taille tas apres nouveau node : "+tas.size());
					succesors=courant.getSommetCourant().getSuccessors();
					//System.out.println("taille successeurs" + succesors.size());
					for (Arc currentSuc : succesors) {
						findPath(courant, currentSuc, tas, labels);
					}
				}
			}
		}

		
		return createSolution(labels, data, iter);
	}

}
