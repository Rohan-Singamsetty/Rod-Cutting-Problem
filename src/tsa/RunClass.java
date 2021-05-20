package tsa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class RunClass {
	public static void main(String[] args) throws FileNotFoundException {

		int vertices = 13;
		GraphStructure graph = new GraphStructure(vertices);
		Route route = new Route();
		LocalSearch local=new LocalSearch();

		createEdges(graph);
		
		route.insertCity(1);
		route.insertCity(2);
		route.insertCity(4);
		route.insertCity(3);
		route.insertCity(6);
		route.insertCity(5);
		route.insertCity(7);
		route.insertCity(8);
		route.insertCity(10);
		route.insertCity(9);
		route.insertCity(1);

		



		
		System.out.println(route.getRouteString());
		System.out.println(route.getThisCost(graph));
		
		Route bestRoute=local.getBestNeighbour(graph,local.Opt(graph, route));
		System.out.println(bestRoute.getRouteString());
		System.out.println(bestRoute.getThisCost(graph));
	}

	public static void createEdges(GraphStructure graph) throws FileNotFoundException {
		nodeReader(graph);
		graph.nodesToEdges(graph.getNode());

	}

	private static void nodeReader(GraphStructure graph) throws FileNotFoundException {

		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("C:/Users/rohan/Documents/Ci/ulysses16.csv"));
			br.readLine();
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] read = line.split(splitBy); 
				double[] node = Arrays.stream(read).mapToDouble(Double::parseDouble).toArray();
				graph.createNode((int)node[0], (int)node[1], (int)node[2]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
