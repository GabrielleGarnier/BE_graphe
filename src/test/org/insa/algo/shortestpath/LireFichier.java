package org.insa.algo.shortestpath;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LireFichier {

	private ArrayList<Integer> origins = new ArrayList<Integer>(); 
	private ArrayList<Integer> destinations = new ArrayList<Integer>(); 
	
	public LireFichier (ArrayList<Integer> origins, ArrayList<Integer> destinations) {
		this.origins=origins; 
		this.destinations=destinations; 
	}
	
	public void Lecture(String fichier) {
		try {
			Scanner lecteur = new Scanner(new File("/C:/Users/Jeanne/Desktop/insa/3A/graphes/BE_graphe/"+fichier));
			while (lecteur.hasNextInt()) {
				this.origins.add(lecteur.nextInt()); 
				this.destinations.add(lecteur.nextInt()); 
			}
			lecteur.close(); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
