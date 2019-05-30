package org.insa.algo.shortestpath;

public class Resultat {
private int origin,destination, nbSommetsDijkstra, nbSommetsAStar;
private float size; 
private long tempsDijkstra, tempsAStar;

public Resultat(int origin, int destination, float size, long tempsDij, int nbDij, long tempsAst, int nbAst) {
	this.origin=origin; 
	this.destination=destination; 
	this.size=size;
	this.tempsDijkstra=tempsDij; 
	this.tempsAStar=tempsAst; 
	this.nbSommetsDijkstra=nbDij; 
	this.nbSommetsAStar=nbAst; 
}

public float getSize() {
	return size;
}

public void setSize(float size) {
	this.size = size;
}

public int getOrigin() {
	return origin;
}

public void setOrigin(int origin) {
	this.origin = origin;
}

public long getTempsDijkstra() {
	return tempsDijkstra;
}

public void setTempsDijkstra(long tempsDijkstra) {
	this.tempsDijkstra = tempsDijkstra;
}

public long getTempsAStar() {
	return tempsAStar;
}

public void setTempsAStar(long tempsAStar) {
	this.tempsAStar = tempsAStar;
}

public int getDestination() {
	return destination;
}

public void setDestination(int destination) {
	this.destination = destination;
}

public int getNbSommetsDijkstra() {
	return nbSommetsDijkstra;
}

public void setNbSommetsDijkstra(int nbSommetsDijkstra) {
	this.nbSommetsDijkstra = nbSommetsDijkstra;
}


public int getNbSommetsAStar() {
	return nbSommetsAStar;
}

public void setNbSommetsAStar(int nbSommetAStar) {
	this.nbSommetsAStar = nbSommetAStar;
}

}
