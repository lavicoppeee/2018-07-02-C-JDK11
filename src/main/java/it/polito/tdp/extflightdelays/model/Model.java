package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	ExtFlightDelaysDAO dao;
	private Graph<Airport,DefaultWeightedEdge> graph;
	Map<Integer,Airport> idMap;
	
	
	public Model() {
		dao= new ExtFlightDelaysDAO();
	}
	
	public void creaGrafo(int x) {
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap=new HashMap<>();
		
		dao.getVeritici(x,idMap);
		Graphs.addAllVertices(graph, idMap.values());
		
		for(Arco a:dao.getArchi(idMap)) {
			if(!this.graph.containsEdge(a.getA1(), a.getA2())) {
				Graphs.addEdgeWithVertices(graph, a.getA1(), a.getA2(),a.getPeso());
			}
		}
	}
	
	public int nVertici() {
		return this.graph.vertexSet().size();
	}

	public int nArchi() {
		return this.graph.edgeSet().size();
	}
	
	public Collection<Airport> Airport(){
		return idMap.values();
	}
	
	
	public List<Arco> getConnessi(Airport a){
		List<Arco> result=new ArrayList<>();
		
		List<Airport> vicini=Graphs.neighborListOf(graph, a);
		for(Airport v:vicini) {
			Double peso=this.graph.getEdgeWeight(this.graph.getEdge(a, v));
			result.add(new Arco(a,v,peso));
		}
		Collections.sort(result);
		return result;
	}
}
