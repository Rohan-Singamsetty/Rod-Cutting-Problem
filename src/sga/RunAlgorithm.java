package sga;

import tsa.Route;
import tsa.RunClass;

import java.io.FileNotFoundException;
import tsa.GraphStructure;

class RunAlgorithm {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		int vertices = 20;
		GraphStructure graph = new GraphStructure(vertices);
		Route route = new Route();

		route.insertCity(1);route.insertCity(3);route.insertCity(4);route.insertCity(6);route.insertCity(11);
		route.insertCity(2);route.insertCity(5);route.insertCity(12);route.insertCity(8);route.insertCity(7);
		route.insertCity(1);route.insertCity(3);route.insertCity(4);route.insertCity(6);route.insertCity(11);
		route.insertCity(2);route.insertCity(5);route.insertCity(12);route.insertCity(8);route.insertCity(7);
		
		try {
			RunClass.createEdges(graph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
		ga.runAlgorithm(20,route,graph);
	}

}