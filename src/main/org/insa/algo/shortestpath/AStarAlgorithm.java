package org.insa.algo.shortestpath;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;
//Beaucoup trop lent! /!\ /!\ /!\
public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected Label[] Init(ShortestPathData data, BinaryHeap<Label> tas, int nbNodes) {
		LabelStar[] labelStars=new LabelStar[nbNodes];

		int i;
		for(i=0;i<nbNodes;i++) {
			labelStars[i]=new LabelStar(data.getGraph().get(i),data);
			
		}
		int idorigin=data.getOrigin().getId(); 
		labelStars[idorigin].setCost(0);
		tas.insert(labelStars[idorigin]); 
		notifyOriginProcessed(data.getOrigin());
		
		
		return labelStars;
	}



}
