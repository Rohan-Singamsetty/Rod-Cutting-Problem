package tsa;

public class Node {
	int id;
	int x;
	int y;

	public Node(int id, int x, int y) {
		this.id = id;
		this.x = y;
		this.y = y;
	}

	public int getId() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setId(int id) {
		this.id=id;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
}
