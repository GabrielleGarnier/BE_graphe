package org.insa.algo.shortestpath;

import java.io.FileWriter;
import java.util.ArrayList;

public class EcrireFichier {
	public EcrireFichier(String fichier, ArrayList<Resultat> resultats) {
		String retour ="\n";
		String espace = ","; 
		 String firstLine ="Size, Origine, Destination, Temps Dijkstra (en ms), Nb Sommets, Temps A* (en ms), Nb Sommets \n"; 
		 try {
		 FileWriter file = new FileWriter("src\\test\\"+fichier);
		 file.append(firstLine);
		 	for (Resultat line : resultats) {
		 		file.append(retour);
		 		file.append(String.valueOf(line.getSize()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getOrigin()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getDestination()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getTempsDijkstra()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getNbSommetsDijkstra()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getTempsAStar()));
		 		file.append(espace);
		 		file.append(String.valueOf(line.getNbSommetsAStar()));
		 	}
	 		file.flush();
	 		file.close();
	 		} 
		 catch (Exception e){
			 System.out.println(e.getMessage());
		 }
	}
	
}
