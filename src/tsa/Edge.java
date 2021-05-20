package tsa;

public class Edge {
	int source;
	int destination;
	int weight;

	public Edge(int source, int destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	public int getSource() {
		return source;
	}
	public int getDestination() {
		return destination;
	}
	public int getWeight() {
		return weight;
	}
	public void setSource(int source) {
		this.source=source;
	}
	public void setX(int destination) {
		this.destination=destination;
	}
	public void setY(int weight) {
		this.weight=weight;
	}
}
