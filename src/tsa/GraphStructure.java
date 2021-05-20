package tsa;

import java.util.ArrayList;
import java.util.LinkedList;

public class GraphStructure {

		int vertices;
		LinkedList<Edge>[] adjacencylist;
		ArrayList<Node> nodeList = new ArrayList<Node>();
		
		public LinkedList<Edge>[] getGraph(){
			return adjacencylist;
		}
		
		public ArrayList<Node> getNode(){
			return nodeList;
		}

		public GraphStructure(int vertices) {
			this.vertices = vertices;
			adjacencylist = new LinkedList[vertices];
			// initialize adjacency lists for all the vertices
			for (int i = 0; i < vertices; i++) {
				adjacencylist[i] = new LinkedList<>();
			}
		}
		
		public void createNode(int id, int x, int y) {
			Node node = new Node(id, x, y);
			nodeList.add(node);
		}
		
		public void nodesToEdges(ArrayList<Node> nodeList) {
			for(int i=0;i<(nodeList.size()-1);i++) {
				for(int j=1;j<nodeList.size();j++) {
					addEdge(nodeList.get(i),nodeList.get(j));
				}
			}	
			for(int i=0;i<(nodeList.size()-2);i++) {
				addEdge(nodeList.get(nodeList.size()-1),nodeList.get(i));
			}
		}
		
		public void addEdge(int source, int destination, int weight) {
			Edge edge = new Edge(source, destination, weight);
			adjacencylist[source].addFirst(edge); // for directed graph
		}
		
		public void addEdge(Node node1, Node node2) {
			int weight=distance(node1.x, node1.y, node2.x, node2.y);
			addEdge(node1.id,node2.id,weight);
		}
		
		public int distance(int x1, int y1, int x2, int y2) {
	        double l = Math.sqrt((x1*x2)+(y1*y2));
	        int distance = (int)Math.round(l);
	        return distance;
		}
		
		
		public int getWeight(int source,int destination) {
				LinkedList<Edge> list = adjacencylist[source];
				for (int j = 0; j < list.size(); j++) {
					if(list.get(j).destination==destination) {
						return list.get(j).weight;
					}
				}
				return 0;

		}
		

		public void printGraph() {
			for (int i = 0; i < vertices; i++) {
				LinkedList<Edge> list = adjacencylist[i];
				for (int j = 0; j < list.size(); j++) {
					System.out.println("[vertex-" + i + "," + list.get(j).destination + ","
							+ list.get(j).weight +"]");
				}
			}
		}
	}


