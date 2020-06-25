package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	SerieADAO dao;
	List<Season> stagioni;
	Graph<Team, DefaultWeightedEdge> grafo;
	List<Team> squadrePerStagione;

	public Model() {
		dao = new SerieADAO();
	}

	public List<Season> listSeasons() {
		stagioni = dao.listSeasons();
		return stagioni;
	}

	public void creaGrafo(Season stagione) {

		grafo = new SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//Aggiungiamo i vertici
		squadrePerStagione = dao.listTeams(stagione);
		Graphs.addAllVertices(grafo, squadrePerStagione);

		//Aggiungiamo gli archi
		for(Adiacenza a : dao.getAdiacenze(stagione)) {
			if(a.getT1() == null && a.getT2() == null) {
				return;
			}
			if(grafo.getEdge(a.getT1(), a.getT2()) == null) {
				if(a.getRis().equals("H")) {
					Graphs.addEdge(grafo, a.getT1(), a.getT2(), 1);
				}
				if (a.getRis().equals("A")) {
					Graphs.addEdge(grafo, a.getT1(), a.getT2(), -1);
				}
				if(a.getRis().equals("D")) {
					Graphs.addEdge(grafo, a.getT1(), a.getT2(), 0);
				}
			}
		}


		System.out.println("Grafo creato!\n# vertici: "+ this.grafo.vertexSet().size()+"\n# archi: "+ this.grafo.edgeSet().size());
	}

	public List<Team> getTeams(){
		return squadrePerStagione;
	}

	//CLASSIFICA FINALE DEL CAMPIONATO 
	public List<TeamPunteggio> calcolaClassifica(){
		List<TeamPunteggio> classifica = new ArrayList<TeamPunteggio>();
		int somma = 0 ;

		for(Team t : grafo.vertexSet()) {
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(t)) {
				somma = somma + (int) grafo.getEdgeWeight(e);
			}

			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(t)) {
				somma = somma - (int) grafo.getEdgeWeight(e);
			}

			TeamPunteggio tp = new TeamPunteggio(t, somma);
			classifica.add(tp);
		}
		Collections.sort(classifica);
		return classifica;
	}
}
