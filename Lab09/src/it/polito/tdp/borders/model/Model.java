package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;




public class Model {
	
	private Graph<Country, DefaultEdge> graph;
	private List<Country> paesi;
	private List<Border> confini;
	Set<Country> visitati;
	public Model() {
	
	}

	/*
	 * controllo l'intervallo dell'anno
	 */
	public boolean isAnnoValid(int anno) {
		if(anno>= 1816 && anno<=2016) {
			return true;
		}
		return false;
	}
	
	/*
	 * crea grafo
	 */

	public void creaGrafo(int anno) {
		// prendo 
		BordersDAO dao = new BordersDAO();
		this.paesi = dao.getCountryByYear(anno);
		
		// Crea il grafo (sempre nuovo)
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		// Aggiungi i vertici
//		for(ArtObject ao : this.artObjects) {
//			this.graph.addVertex(ao);
//		}  oppure usando la libreria ->
		Graphs.addAllVertices(this.graph, this.paesi);
		
		
		// Aggiungi gli archi (con il loro peso)
		addEdges(anno);
		System.out.format("Grafo creato: %d vertici, %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());
		
	}

	private void addEdges(int anno) {
		BordersDAO dao = new BordersDAO();
		confini = dao.getCountryPairs(anno);
		for(Border b: confini) {
			this.graph.addEdge(b.getC1(), b.getC2());
		}
		
	}
	
	public String getCountriesAndBorders() {
		String risultato = "";
		for(Country c: this.paesi) {
			risultato += c.getNome() +" "+ this.graph.degreeOf(c)+"\n";
		}
		return risultato;
	}
	
	public int getComponentiConnesse() {
		
		ConnectivityInspector<Country,DefaultEdge> ci = new ConnectivityInspector<Country,DefaultEdge>(this.graph);
		return ci.connectedSets().size();
		
	
	}

	public List<Country> getCountryList() {
		BordersDAO dao = new BordersDAO();
		
		return dao.loadAllCountries();
	}
	
	public Set<Country> trovaVicini(Country partenza) {
		visitati = new HashSet<>();
		this.ricorri(partenza);
		System.out.println(visitati);
		System.out.println(visitati.size());
		return visitati;
		
	}
	
	private void ricorri(Country partenza) {
		for(Country c : this.graph.vertexSet()) {
			if(this.graph.containsEdge(partenza, c) && !visitati.contains(c) && !c.equals(partenza)) {
					visitati.add(c);
					ricorri(c);
			}
		}
		
	}
	
//	public Set<Country> trovaVicini(Country partenza) {
//		// visita il grafo
//		Set<Country> visitati = new HashSet<>();
//		DepthFirstIterator<Country, DefaultEdge> dfv = new DepthFirstIterator<>(this.graph, partenza);
//		while(dfv.hasNext()) {
//			visitati.add(dfv.next());
//		}
//		System.out.println(visitati);
//		System.out.println(visitati.size());
//		return visitati;
//		
//	}

}
