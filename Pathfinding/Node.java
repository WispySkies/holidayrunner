package me.declan.holiday.Pathfinding;

public class Node {

	final int posX, posY;
	int g;
	Node parent;
	
	public Node(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.g = 0;
	}
	
	public float heuristic(int x, int y) {
		return Math.abs(x-posX)+Math.abs(y-posY);
	}
	
	public boolean equals(Node node) {
		return posX == node.posX && posY == node.posY;
	}
}
